/*
 * Copyright (c) 2013 Etsy
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.etsy.android.grid;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;

/**
 * A staggered grid view which supports multiple columns with rows of varying sizes.
 * <p/>
 * Builds multiple columns on top of {@link ExtendableListView}
 * <p/>
 * Partly inspired by - https://github.com/huewu/PinterestLikeAdapterView
 */
public class StaggeredGridView extends ExtendableListView {

    private static final String TAG = StaggeredGridView.class.getSimpleName();
    private static final boolean DEBUG = false;
    private static final int DEFAUlT_COLUMNS_PORTRAIT = 2;
    private static final int DEFAULT_COLUMNS_LANDSCAPE = 3;

    private int columnCount;
    private int itemMargin;
    private int columnWidth;
    private boolean needSync;
    private int columnCountPortrait = DEFAUlT_COLUMNS_PORTRAIT;
    private int columnCountLandscape = DEFAULT_COLUMNS_LANDSCAPE;

    /**
     * A key-value collection where the key is the position and the
     * {@link GridItemRecord} with some info about that position
     * so we can maintain it's position - and reorg on orientation change.
     */
    private SparseArray<GridItemRecord> positionData;
    private int gridPaddingLeft;
    private int gridPaddingRight;
    private int gridPaddingTop;
    private int gridPaddingBottom;

    /**
     * Our grid item state record with {@link Parcelable} implementation
     * so we can persist them across the SGV lifecycle.
     */
    static class GridItemRecord implements Parcelable {
        int column;
        double heightRatio;
        boolean isHeaderFooter;

        GridItemRecord() {
        }

        /**
         * Constructor called from {@link #CREATOR}
         */
        private GridItemRecord(Parcel in) {
            column = in.readInt();
            heightRatio = in.readDouble();
            isHeaderFooter = in.readByte() == 1;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            out.writeInt(column);
            out.writeDouble(heightRatio);
            out.writeByte((byte) (isHeaderFooter ? 1 : 0));
        }

        @Override
        public String toString() {
            return "GridItemRecord.ListSavedState{"
                    + Integer.toHexString(System.identityHashCode(this))
                    + " column:" + column
                    + " heightRatio:" + heightRatio
                    + " isHeaderFooter:" + isHeaderFooter
                    + "}";
        }

        public static final Parcelable.Creator<GridItemRecord> CREATOR
                = new Parcelable.Creator<GridItemRecord>() {
            public GridItemRecord createFromParcel(Parcel in) {
                return new GridItemRecord(in);
            }

            public GridItemRecord[] newArray(int size) {
                return new GridItemRecord[size];
            }
        };
    }

    /**
     * The location of the top of each top item added in each column.
     */
    private int[] columnTops;

    /**
     * The location of the bottom of each bottom item added in each column.
     */
    private int[] columnBottoms;

    /**
     * The left location to put items for each column
     */
    private int[] columnLefts;

    /**
     * Tells us the distance we've offset from the top.
     * Can be slightly off on orientation change - TESTING
     */
    private int distanceToTop;

    public StaggeredGridView(final Context context) {
        this(context, null);
    }

    public StaggeredGridView(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StaggeredGridView(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);

        if (attrs != null) {
            // get the number of columns in portrait and landscape
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.StaggeredGridView, defStyle, 0);

            columnCountPortrait = typedArray.getInteger(
                    R.styleable.StaggeredGridView_column_count_portrait,
                    DEFAUlT_COLUMNS_PORTRAIT);
            columnCountLandscape = typedArray.getInteger(
                    R.styleable.StaggeredGridView_column_count_landscape,
                    DEFAULT_COLUMNS_LANDSCAPE);

            itemMargin = typedArray.getDimensionPixelSize(
                    R.styleable.StaggeredGridView_item_margin, 0);
            gridPaddingLeft = typedArray.getDimensionPixelSize(
                    R.styleable.StaggeredGridView_grid_paddingLeft, 0);
            gridPaddingRight = typedArray.getDimensionPixelSize(
                    R.styleable.StaggeredGridView_grid_paddingRight, 0);
            gridPaddingTop = typedArray.getDimensionPixelSize(
                    R.styleable.StaggeredGridView_grid_paddingTop, 0);
            gridPaddingBottom = typedArray.getDimensionPixelSize(
                    R.styleable.StaggeredGridView_grid_paddingBottom, 0);

            typedArray.recycle();
        }

        columnCount = 0; // determined onMeasure
        // Creating these empty arrays to avoid saving null states
        columnTops = new int[0];
        columnBottoms = new int[0];
        columnLefts = new int[0];
        positionData = new SparseArray<GridItemRecord>();
    }

    // //////////////////////////////////////////////////////////////////////////////////////////
    // PROPERTIES
    //

    // Grid padding is applied to the list item rows but not the header and footer
    public int getRowPaddingLeft() {
        return getListPaddingLeft() + gridPaddingLeft;
    }

    public int getRowPaddingRight() {
        return getListPaddingRight() + gridPaddingRight;
    }

    public int getRowPaddingTop() {
        return getListPaddingTop() + gridPaddingTop;
    }

    public int getRowPaddingBottom() {
        return getListPaddingBottom() + gridPaddingBottom;
    }

    public void setGridPadding(int left, int top, int right, int bottom) {
        gridPaddingLeft = left;
        gridPaddingTop = top;
        gridPaddingRight = right;
        gridPaddingBottom = bottom;
    }

    // //////////////////////////////////////////////////////////////////////////////////////////
    // MEASUREMENT
    //

    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (columnCount <= 0) {
            boolean isLandscape = getMeasuredWidth() > getMeasuredHeight();
            columnCount = isLandscape ? columnCountLandscape : columnCountPortrait;
        }

        // our column width is the width of the listview
        // minus it's padding
        // minus the total items margin
        // divided by the number of columns
        columnWidth = calculateColumnWidth(getMeasuredWidth());

        if (columnTops == null || columnTops.length != columnCount) {
            columnTops = new int[columnCount];
        }
        if (columnBottoms == null || columnBottoms.length != columnCount) {
            columnBottoms = new int[columnCount];
        }
        if (columnLefts == null || columnLefts.length != columnCount) {
            columnLefts = new int[columnCount];
        }

        for (int i = 0; i < columnCount; i++) {
            columnLefts[i] = calculateColumnLeft(i);
        }
    }

    @Override
    protected void onMeasureChild(final View child, final LayoutParams layoutParams) {
        final int viewType = layoutParams.viewType;
        final int position = layoutParams.position;

        if (viewType == ITEM_VIEW_TYPE_HEADER_OR_FOOTER ||
                viewType == ITEM_VIEW_TYPE_IGNORE) {
            // for headers and weird ignored views
            super.onMeasureChild(child, layoutParams);
        } else {
            if (DEBUG) {
                Log.d(TAG, "onMeasureChild BEFORE position:" + position +
                        " h:" + getMeasuredHeight());
            }
            // measure it to the width of our column.
            int childWidthSpec = MeasureSpec.makeMeasureSpec(columnWidth, MeasureSpec.EXACTLY);
            int childHeightSpec;
            if (layoutParams.height > 0) {
                childHeightSpec = MeasureSpec.makeMeasureSpec(layoutParams.height, MeasureSpec.EXACTLY);
            } else {
                childHeightSpec = MeasureSpec.makeMeasureSpec(LayoutParams.WRAP_CONTENT, MeasureSpec.EXACTLY);
            }
            child.measure(childWidthSpec, childHeightSpec);
        }

        final int childHeight = getChildHeight(child);
        setPositionHeightRatio(position, childHeight);

        if (DEBUG) {
            Log.d(TAG, "onMeasureChild AFTER position:" + position +
                    " h:" + childHeight);
        }
    }

    public int getColumnWidth() {
        return columnWidth;
    }

    public void resetToTop() {
        if (columnCount > 0) {

            if (columnTops == null) {
                columnTops = new int[columnCount];
            } else {
                Arrays.fill(columnTops, 0);
            }
            if (columnBottoms == null) {
                columnBottoms = new int[columnCount];
            } else {
                Arrays.fill(columnBottoms, 0);
            }
            positionData.clear();
            needSync = false;
            distanceToTop = 0;
            setSelection(0);
        }
    }

    // //////////////////////////////////////////////////////////////////////////////////////////
    // POSITIONING
    //

    @Override
    protected void onChildCreated(final int position, final boolean flowDown) {
        super.onChildCreated(position, flowDown);
        if (!isHeaderOrFooter(position)) {
            // do we already have a column for this position?
            final int column = getChildColumn(position, flowDown);
            setPositionColumn(position, column);
            if (DEBUG) {
                Log.d(TAG, "onChildCreated position:" + position +
                        " is in column:" + column);
            }
        } else {
            setPositionIsHeaderFooter(position);
        }
    }

    @Override
    protected void layoutChildren() {
        preLayoutChildren();
        super.layoutChildren();
    }

    private void preLayoutChildren() {
        // on a major re-layout reset for our next layout pass
        if (!needSync) {
            Arrays.fill(columnBottoms, 0);
        } else {
            needSync = false;
        }
        // copy the tops into the bottom
        // since we're going to redo a layout pass that will draw down from
        // the top
        System.arraycopy(columnTops, 0, columnBottoms, 0, columnCount);
    }

    // NOTE : Views will either be layout out via onLayoutChild
    // OR
    // Views will be offset if they are active but offscreen so that we can recycle!
    // Both onLayoutChild() and onOffsetChild are called after we measure our view
    // see ExtensibleListView.setupChild();

    @Override
    protected void onLayoutChild(final View child,
                                 final int position,
                                 final boolean flowDown,
                                 final int childrenLeft, final int childTop,
                                 final int childRight, final int childBottom) {
        if (isHeaderOrFooter(position)) {
            layoutGridHeaderFooter(child, position, flowDown, childrenLeft, childTop, childRight, childBottom);
        } else {
            layoutGridChild(child, position, flowDown, childrenLeft, childRight);
        }
    }

    private void layoutGridHeaderFooter(final View child, final int position, final boolean flowDown, final int childrenLeft, final int childTop, final int childRight, final int childBottom) {
        // offset the top and bottom of all our columns
        // if it's the footer we want it below the lowest child bottom
        int gridChildTop;
        int gridChildBottom;

        if (flowDown) {
            gridChildTop = getLowestPositionedBottom();
            gridChildBottom = gridChildTop + getChildHeight(child);
        } else {
            gridChildBottom = getHighestPositionedTop();
            gridChildTop = gridChildBottom - getChildHeight(child);
        }

        for (int i = 0; i < columnCount; i++) {
            updateColumnTopIfNeeded(i, gridChildTop);
            updateColumnBottomIfNeeded(i, gridChildBottom);
        }

        super.onLayoutChild(child, position, flowDown,
                childrenLeft, gridChildTop, childRight, gridChildBottom);
    }

    private void layoutGridChild(final View child, final int position,
                                 final boolean flowDown,
                                 final int childrenLeft, final int childRight) {
        // stash the bottom and the top if it's higher positioned
        int column = getPositionColumn(position);

        int gridChildTop;
        int gridChildBottom;

        int childTopMargin = getChildTopMargin(position);
        int childBottomMargin = getChildBottomMargin();
        int verticalMargins = childTopMargin + childBottomMargin;

        if (flowDown) {
            gridChildTop = columnBottoms[column]; // the next items top is the last items bottom
            gridChildBottom = gridChildTop + (getChildHeight(child) + verticalMargins);
        } else {
            gridChildBottom = columnTops[column]; // the bottom of the next column up is our top
            gridChildTop = gridChildBottom - (getChildHeight(child) + verticalMargins);
        }

        if (DEBUG) {
            Log.d(TAG, "onLayoutChild position:" + position +
                    " column:" + column +
                    " gridChildTop:" + gridChildTop +
                    " gridChildBottom:" + gridChildBottom);
        }

        // we also know the column of this view so let's stash it in the
        // view's layout params
        GridLayoutParams layoutParams = (GridLayoutParams) child.getLayoutParams();
        layoutParams.column = column;

        updateColumnBottomIfNeeded(column, gridChildBottom);
        updateColumnTopIfNeeded(column, gridChildTop);

        // subtract the margins before layout
        gridChildTop += childTopMargin;
        gridChildBottom -= childBottomMargin;

        child.layout(childrenLeft, gridChildTop, childRight, gridChildBottom);
    }

    @Override
    protected void onOffsetChild(final View child, final int position,
                                 final boolean flowDown, final int childrenLeft, final int childTop) {
        // if the child is recycled and is just offset
        // we still want to add its deets into our store
        if (isHeaderOrFooter(position)) {

            offsetGridHeaderFooter(child, position, flowDown, childrenLeft, childTop);
        } else {
            offsetGridChild(child, position, flowDown, childrenLeft, childTop);
        }
    }

    private void offsetGridHeaderFooter(final View child, final int position, final boolean flowDown, final int childrenLeft, final int childTop) {
        // offset the top and bottom of all our columns
        // if it's the footer we want it below the lowest child bottom
        int gridChildTop;
        int gridChildBottom;

        if (flowDown) {
            gridChildTop = getLowestPositionedBottom();
            gridChildBottom = gridChildTop + getChildHeight(child);
        } else {
            gridChildBottom = getHighestPositionedTop();
            gridChildTop = gridChildBottom - getChildHeight(child);
        }

        for (int i = 0; i < columnCount; i++) {
            updateColumnTopIfNeeded(i, gridChildTop);
            updateColumnBottomIfNeeded(i, gridChildBottom);
        }

        super.onOffsetChild(child, position, flowDown, childrenLeft, gridChildTop);
    }

    private void offsetGridChild(final View child, final int position, final boolean flowDown, final int childrenLeft, final int childTop) {
        // stash the bottom and the top if it's higher positioned
        int column = getPositionColumn(position);

        int gridChildTop;
        int gridChildBottom;

        int childTopMargin = getChildTopMargin(position);
        int childBottomMargin = getChildBottomMargin();
        int verticalMargins = childTopMargin + childBottomMargin;

        if (flowDown) {
            gridChildTop = columnBottoms[column]; // the next items top is the last items bottom
            gridChildBottom = gridChildTop + (getChildHeight(child) + verticalMargins);
        } else {
            gridChildBottom = columnTops[column]; // the bottom of the next column up is our top
            gridChildTop = gridChildBottom - (getChildHeight(child) + verticalMargins);
        }

        if (DEBUG) {
            Log.d(TAG, "onOffsetChild position:" + position +
                    " column:" + column +
                    " childTop:" + childTop +
                    " gridChildTop:" + gridChildTop +
                    " gridChildBottom:" + gridChildBottom);
        }

        // we also know the column of this view so let's stash it in the
        // view's layout params
        GridLayoutParams layoutParams = (GridLayoutParams) child.getLayoutParams();
        layoutParams.column = column;

        updateColumnBottomIfNeeded(column, gridChildBottom);
        updateColumnTopIfNeeded(column, gridChildTop);

        super.onOffsetChild(child, position, flowDown, childrenLeft, gridChildTop + childTopMargin);
    }

    private int getChildHeight(final View child) {
        return child.getMeasuredHeight();
    }

    private int getChildTopMargin(final int position) {
        boolean isFirstRow = position < (getHeaderViewsCount() + columnCount);
        return isFirstRow ? itemMargin : 0;
    }

    private int getChildBottomMargin() {
        return itemMargin;
    }

    @Override
    protected LayoutParams generateChildLayoutParams(final View child) {
        GridLayoutParams layoutParams = null;

        final ViewGroup.LayoutParams childParams = child.getLayoutParams();
        if (childParams != null) {
            if (childParams instanceof GridLayoutParams) {
                layoutParams = (GridLayoutParams) childParams;
            } else {
                layoutParams = new GridLayoutParams(childParams);
            }
        }
        if (layoutParams == null) {
            layoutParams = new GridLayoutParams(
                    columnWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        return layoutParams;
    }

    private void updateColumnTopIfNeeded(int column, int childTop) {
        if (childTop < columnTops[column]) {
            columnTops[column] = childTop;
        }
    }

    private void updateColumnBottomIfNeeded(int column, int childBottom) {
        if (childBottom > columnBottoms[column]) {
            columnBottoms[column] = childBottom;
        }
    }

    @Override
    protected int getChildLeft(final int position) {
        if (isHeaderOrFooter(position)) {
            return super.getChildLeft(position);
        } else {
            final int column = getPositionColumn(position);
            return columnLefts[column];
        }
    }

    @Override
    protected int getChildTop(final int position) {
        if (isHeaderOrFooter(position)) {
            return super.getChildTop(position);
        } else {
            final int column = getPositionColumn(position);
            if (column == -1) {
                return getHighestPositionedBottom();
            }
            return columnBottoms[column];
        }
    }

    /**
     * Get the top for the next child down in our view
     * (maybe a column across) so we can fill down.
     */
    @Override
    protected int getNextChildDownsTop(final int position) {
        if (isHeaderOrFooter(position)) {
            return super.getNextChildDownsTop(position);
        } else {
            return getHighestPositionedBottom();
        }
    }

    @Override
    protected int getChildBottom(final int position) {
        if (isHeaderOrFooter(position)) {
            return super.getChildBottom(position);
        } else {
            final int column = getPositionColumn(position);
            if (column == -1) {
                return getLowestPositionedTop();
            }
            return columnTops[column];
        }
    }

    /**
     * Get the bottom for the next child up in our view
     * (maybe a column across) so we can fill up.
     */
    @Override
    protected int getNextChildUpsBottom(final int position) {
        if (isHeaderOrFooter(position)) {
            return super.getNextChildUpsBottom(position);
        } else {
            return getLowestPositionedTop();
        }
    }

    @Override
    protected int getLastChildBottom() {
        final int lastPosition = firstPosition + (getChildCount() - 1);
        if (isHeaderOrFooter(lastPosition)) {
            return super.getLastChildBottom();
        }
        return getHighestPositionedBottom();
    }

    @Override
    protected int getFirstChildTop() {
        if (isHeaderOrFooter(firstPosition)) {
            return super.getFirstChildTop();
        }
        return getLowestPositionedTop();
    }

    @Override
    protected int getHighestChildTop() {
        if (isHeaderOrFooter(firstPosition)) {
            return super.getHighestChildTop();
        }
        return getHighestPositionedTop();
    }

    @Override
    protected int getLowestChildBottom() {
        final int lastPosition = firstPosition + (getChildCount() - 1);
        if (isHeaderOrFooter(lastPosition)) {
            return super.getLowestChildBottom();
        }
        return getLowestPositionedBottom();
    }

    @Override
    protected void offsetChildrenTopAndBottom(final int offset) {
        super.offsetChildrenTopAndBottom(offset);
        offsetAllColumnsTopAndBottom(offset);
        offsetDistanceToTop(offset);
    }

    protected void offsetChildrenTopAndBottom(final int offset, final int column) {
        if (DEBUG) {
            Log.d(TAG, "offsetChildrenTopAndBottom: " + offset + " column:" + column);
        }
        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            final View v = getChildAt(i);
            if (v != null &&
                    v.getLayoutParams() != null &&
                    v.getLayoutParams() instanceof GridLayoutParams) {
                GridLayoutParams lp = (GridLayoutParams) v.getLayoutParams();
                if (lp.column == column) {
                    v.offsetTopAndBottom(offset);
                }
            }
        }
        offsetColumnTopAndBottom(offset, column);
    }

    private void offsetDistanceToTop(final int offset) {
        distanceToTop += offset;
        if (DEBUG) {
            Log.d(TAG, "offset distanceToTop:" + distanceToTop);
        }
    }

    public int getDistanceToTop() {
        return distanceToTop;
    }

    private void offsetAllColumnsTopAndBottom(final int offset) {
        if (offset != 0) {
            for (int i = 0; i < columnCount; i++) {
                offsetColumnTopAndBottom(offset, i);
            }
        }
    }

    private void offsetColumnTopAndBottom(final int offset, final int column) {
        if (offset != 0) {
            columnTops[column] += offset;
            columnBottoms[column] += offset;
        }
    }

    @Override
    protected void adjustViewsAfterFillGap(final boolean down) {
        super.adjustViewsAfterFillGap(down);
        // fix vertical gaps when hitting the top after a rotate
        // only when scrolling back up!
        if (!down) {
            alignTops();
        }
    }

    private void alignTops() {
        if (firstPosition == getHeaderViewsCount()) {
            // we're showing all the views before the header views
            int[] nonHeaderTops = getHighestNonHeaderTops();
            // we should now have our non header tops
            // align them
            boolean isAligned = true;
            int highestColumn = -1;
            int highestTop = Integer.MAX_VALUE;
            for (int i = 0; i < nonHeaderTops.length; i++) {
                // are they all aligned
                if (isAligned && i > 0 && nonHeaderTops[i] != highestTop) {
                    isAligned = false; // not all the tops are aligned
                }
                // what's the highest
                if (nonHeaderTops[i] < highestTop) {
                    highestTop = nonHeaderTops[i];
                    highestColumn = i;
                }
            }

            // skip the rest.
            if (isAligned) {
                return;
            }

            // we've got the highest column - lets align the others
            for (int i = 0; i < nonHeaderTops.length; i++) {
                if (i != highestColumn) {
                    // there's a gap in this column
                    int offset = highestTop - nonHeaderTops[i];
                    offsetChildrenTopAndBottom(offset, i);
                }
            }
            invalidate();
        }
    }

    private int[] getHighestNonHeaderTops() {
        int[] nonHeaderTops = new int[columnCount];
        int childCount = getChildCount();
        if (childCount > 0) {
            for (int i = 0; i < childCount; i++) {
                View child = getChildAt(i);
                if (child != null &&
                        child.getLayoutParams() != null &&
                        child.getLayoutParams() instanceof GridLayoutParams) {
                    // is this child's top the highest non
                    GridLayoutParams lp = (GridLayoutParams) child.getLayoutParams();
                    // is it a child that isn't a header
                    if (lp.viewType != ITEM_VIEW_TYPE_HEADER_OR_FOOTER &&
                            child.getTop() < nonHeaderTops[lp.column]) {
                        nonHeaderTops[lp.column] = child.getTop();
                    }
                }
            }
        }
        return nonHeaderTops;
    }

    @Override
    protected void onChildrenDetached(final int start, final int count) {
        super.onChildrenDetached(start, count);
        // go through our remaining views and sync the top and bottom stash.

        // Repair the top and bottom column boundaries from the views we still have
        Arrays.fill(columnTops, Integer.MAX_VALUE);
        Arrays.fill(columnBottoms, 0);

        for (int i = 0; i < getChildCount(); i++) {
            final View child = getChildAt(i);
            if (child != null) {
                final LayoutParams childParams = (LayoutParams) child.getLayoutParams();
                if (childParams.viewType != ITEM_VIEW_TYPE_HEADER_OR_FOOTER &&
                        childParams instanceof GridLayoutParams) {
                    GridLayoutParams layoutParams = (GridLayoutParams) childParams;
                    int column = layoutParams.column;
                    int position = layoutParams.position;
                    final int childTop = child.getTop();
                    if (childTop < columnTops[column]) {
                        columnTops[column] = childTop - getChildTopMargin(position);
                    }
                    final int childBottom = child.getBottom();
                    if (childBottom > columnBottoms[column]) {
                        columnBottoms[column] = childBottom + getChildBottomMargin();
                    }
                } else {
                    // the header and footer here
                    final int childTop = child.getTop();
                    final int childBottom = child.getBottom();

                    for (int col = 0; col < columnCount; col++) {
                        if (childTop < columnTops[col]) {
                            columnTops[col] = childTop;
                        }
                        if (childBottom > columnBottoms[col]) {
                            columnBottoms[col] = childBottom;
                        }
                    }

                }
            }
        }
    }

    @Override
    protected boolean hasSpaceUp() {
        int end = clipToPadding ? getRowPaddingTop() : 0;
        return getLowestPositionedTop() > end;
    }

    // //////////////////////////////////////////////////////////////////////////////////////////
    // SYNCING ACROSS ROTATION
    //

    @Override
    protected void onSizeChanged(final int w, final int h, final int oldw, final int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        boolean isLandscape = w > h;
        int newColumnCount = isLandscape ? columnCountLandscape : columnCountPortrait;
        if (columnCount != newColumnCount) {
            columnCount = newColumnCount;

            columnWidth = calculateColumnWidth(w);

            columnTops = new int[columnCount];
            columnBottoms = new int[columnCount];
            columnLefts = new int[columnCount];

            distanceToTop = 0;

            // rebuild the column lefts
            for (int i = 0; i < columnCount; i++) {
                columnLefts[i] = calculateColumnLeft(i);
            }

            // if we have data
            if (getCount() > 0 && positionData.size() > 0) {
                onColumnSync();
            }

            requestLayout();
        }
    }

    private int calculateColumnWidth(final int gridWidth) {
        final int listPadding = getRowPaddingLeft() + getRowPaddingRight();
        return (gridWidth - listPadding - itemMargin * (columnCount + 1)) / columnCount;
    }

    private int calculateColumnLeft(final int colIndex) {
        return getRowPaddingLeft() + itemMargin + ((itemMargin + columnWidth) * colIndex);
    }

    /**
     * Our columnTops and columnBottoms need to be re-built up to the
     * syncPosition - the following layout request will then
     * layout the that position and then fillUp and fillDown appropriately.
     */
    private void onColumnSync() {
        // re-calc tops for new column count!
        int syncPosition = Math.min(this.syncPosition, getCount() - 1);

        SparseArray<Double> positionHeightRatios = new SparseArray<Double>(syncPosition);
        for (int pos = 0; pos < syncPosition; pos++) {
            // check for weirdness
            final GridItemRecord rec = positionData.get(pos);
            if (rec == null) {
                break;
            }

            Log.d(TAG, "onColumnSync:" + pos + " ratio:" + rec.heightRatio);
            positionHeightRatios.append(pos, rec.heightRatio);
        }

        positionData.clear();

        // re-calc our relative position while at the same time
        // rebuilding our GridItemRecord collection

        if (DEBUG) {
            Log.d(TAG, "onColumnSync column width:" + columnWidth);
        }

        for (int pos = 0; pos < syncPosition; pos++) {
            final GridItemRecord rec = getOrCreateRecord(pos);
            final double heightRatio = positionHeightRatios.get(pos);
            final int height = (int) (columnWidth * heightRatio);
            rec.heightRatio = heightRatio;

            int top;
            int bottom;
            // check for headers
            if (isHeaderOrFooter(pos)) {
                // the next top is the bottom for that column
                top = getLowestPositionedBottom();
                bottom = top + height;

                for (int i = 0; i < columnCount; i++) {
                    columnTops[i] = top;
                    columnBottoms[i] = bottom;
                }
            } else {
                // what's the next column down ?
                final int column = getHighestPositionedBottomColumn();
                // the next top is the bottom for that column
                top = columnBottoms[column];
                bottom = top + height + getChildTopMargin(pos) + getChildBottomMargin();

                columnTops[column] = top;
                columnBottoms[column] = bottom;

                rec.column = column;
            }

            if (DEBUG) {
                Log.d(TAG, "onColumnSync position:" + pos +
                        " top:" + top +
                        " bottom:" + bottom +
                        " height:" + height +
                        " heightRatio:" + heightRatio);
            }
        }

        // our sync position will be displayed in this column
        final int syncColumn = getHighestPositionedBottomColumn();
        setPositionColumn(syncPosition, syncColumn);

        // we want to offset from height of the sync position
        // minus the offset
        int syncToBottom = columnBottoms[syncColumn];
        int offset = -syncToBottom + specificTop;
        // offset all columns by
        offsetAllColumnsTopAndBottom(offset);

        // sync the distance to top
        distanceToTop = -syncToBottom;

        // stash our bottoms in our tops - though these will be copied back to the bottoms
        System.arraycopy(columnBottoms, 0, columnTops, 0, columnCount);
    }

    // //////////////////////////////////////////////////////////////////////////////////////////
    // GridItemRecord UTILS
    //

    private void setPositionColumn(final int position, final int column) {
        GridItemRecord rec = getOrCreateRecord(position);
        rec.column = column;
    }

    private void setPositionHeightRatio(final int position, final int height) {
        GridItemRecord rec = getOrCreateRecord(position);
        rec.heightRatio = (double) height / (double) columnWidth;
        if (DEBUG) {
            Log.d(TAG, "position:" + position +
                    " width:" + columnWidth +
                    " height:" + height +
                    " heightRatio:" + rec.heightRatio);
        }
    }

    private void setPositionIsHeaderFooter(final int position) {
        GridItemRecord rec = getOrCreateRecord(position);
        rec.isHeaderFooter = true;
    }

    private GridItemRecord getOrCreateRecord(final int position) {
        GridItemRecord rec = positionData.get(position, null);
        if (rec == null) {
            rec = new GridItemRecord();
            positionData.append(position, rec);
        }
        return rec;
    }

    private int getPositionColumn(final int position) {
        GridItemRecord rec = positionData.get(position, null);
        return rec != null ? rec.column : -1;
    }

    // //////////////////////////////////////////////////////////////////////////////////////////
    // HELPERS
    //

    private boolean isHeaderOrFooter(final int position) {
        final int viewType = adapter.getItemViewType(position);
        return viewType == ITEM_VIEW_TYPE_HEADER_OR_FOOTER;
    }

    private int getChildColumn(final int position, final boolean flowDown) {

        // do we already have a column for this child position?
        int column = getPositionColumn(position);
        // we don't have the column or it no longer fits in our grid
        final int columnCount = this.columnCount;
        if (column < 0 || column >= columnCount) {
            // if we're going down -
            // get the highest positioned (lowest value)
            // column bottom
            if (flowDown) {
                column = getHighestPositionedBottomColumn();
            } else {
                column = getLowestPositionedTopColumn();

            }
        }
        return column;
    }

    // //////////////////////////////////////////////////////////////////////////////////////////
    // BOTTOM
    //

    private int getHighestPositionedBottom() {
        final int column = getHighestPositionedBottomColumn();
        return columnBottoms[column];
    }

    private int getHighestPositionedBottomColumn() {
        int columnFound = 0;
        int highestPositionedBottom = Integer.MAX_VALUE;
        // the highest positioned bottom is the one with the lowest value :D
        for (int i = 0; i < columnCount; i++) {
            int bottom = columnBottoms[i];
            if (bottom < highestPositionedBottom) {
                highestPositionedBottom = bottom;
                columnFound = i;
            }
        }
        return columnFound;
    }

    private int getLowestPositionedBottom() {
        final int column = getLowestPositionedBottomColumn();
        return columnBottoms[column];
    }

    private int getLowestPositionedBottomColumn() {
        int columnFound = 0;
        int lowestPositionedBottom = Integer.MIN_VALUE;
        // the lowest positioned bottom is the one with the highest value :D
        for (int i = 0; i < columnCount; i++) {
            int bottom = columnBottoms[i];
            if (bottom > lowestPositionedBottom) {
                lowestPositionedBottom = bottom;
                columnFound = i;
            }
        }
        return columnFound;
    }

    // //////////////////////////////////////////////////////////////////////////////////////////
    // TOP
    //

    private int getLowestPositionedTop() {
        final int column = getLowestPositionedTopColumn();
        return columnTops[column];
    }

    private int getLowestPositionedTopColumn() {
        int columnFound = 0;
        // we'll go backwards through since the right most
        // will likely be the lowest positioned Top
        int lowestPositionedTop = Integer.MIN_VALUE;
        // the lowest positioned top is the one with the highest value :D
        for (int i = 0; i < columnCount; i++) {
            int top = columnTops[i];
            if (top > lowestPositionedTop) {
                lowestPositionedTop = top;
                columnFound = i;
            }
        }
        return columnFound;
    }

    private int getHighestPositionedTop() {
        final int column = getHighestPositionedTopColumn();
        return columnTops[column];
    }

    private int getHighestPositionedTopColumn() {
        int columnFound = 0;
        int highestPositionedTop = Integer.MAX_VALUE;
        // the highest positioned top is the one with the lowest value :D
        for (int i = 0; i < columnCount; i++) {
            int top = columnTops[i];
            if (top < highestPositionedTop) {
                highestPositionedTop = top;
                columnFound = i;
            }
        }
        return columnFound;
    }

    // //////////////////////////////////////////////////////////////////////////////////////////
    // LAYOUT PARAMS
    //

    /**
     * Extended LayoutParams to column position and anything else we may been using for the grid
     */
    public static class GridLayoutParams extends LayoutParams {

        // The column the view is displayed in
        int column;

        public GridLayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            enforceStaggeredLayout();
        }

        public GridLayoutParams(int w, int h) {
            super(w, h);
            enforceStaggeredLayout();
        }

        public GridLayoutParams(int w, int h, int viewType) {
            super(w, h);
            enforceStaggeredLayout();
        }

        public GridLayoutParams(ViewGroup.LayoutParams source) {
            super(source);
            enforceStaggeredLayout();
        }

        /**
         * Here we're making sure that all grid view items
         * are width MATCH_PARENT and height WRAP_CONTENT.
         * That's what this grid is designed for
         */
        private void enforceStaggeredLayout() {
            if (width != MATCH_PARENT) {
                width = MATCH_PARENT;
            }
            if (height == MATCH_PARENT) {
                height = WRAP_CONTENT;
            }
        }
    }

    // //////////////////////////////////////////////////////////////////////////////////////////
    // SAVED STATE

    @Override
    public Parcelable onSaveInstanceState() {
        ListSavedState listState = (ListSavedState) super.onSaveInstanceState();
        GridListSavedState gridState = new GridListSavedState(listState.getSuperState());

        // from the list state
        gridState.selectedId = listState.selectedId;
        gridState.firstId = listState.firstId;
        gridState.viewTop = listState.viewTop;
        gridState.position = listState.position;
        gridState.height = listState.height;

        // our state
        boolean haveChildren = getChildCount() > 0 && getCount() > 0;

        if (haveChildren && firstPosition > 0) {
            gridState.columnCount = columnCount;
            gridState.columnTops = columnTops;
            gridState.positionData = positionData;
        } else {
            gridState.columnCount = columnCount >= 0 ? columnCount : 0;
            gridState.columnTops = new int[gridState.columnCount];
            gridState.positionData = new SparseArray<Object>();
        }

        return gridState;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        GridListSavedState ss = (GridListSavedState) state;
        columnCount = ss.columnCount;
        columnTops = ss.columnTops;
        positionData = ss.positionData;
        needSync = true;
        super.onRestoreInstanceState(ss);
    }

    public static class GridListSavedState extends ListSavedState {
        int columnCount;
        int[] columnTops;
        SparseArray positionData;

        public GridListSavedState(Parcelable superState) {
            super(superState);
        }

        /**
         * Constructor called from {@link #CREATOR}
         */
        public GridListSavedState(Parcel in) {
            super(in);
            columnCount = in.readInt();
            columnTops = new int[columnCount >= 0 ? columnCount : 0];
            in.readIntArray(columnTops);
            positionData = in.readSparseArray(GridItemRecord.class.getClassLoader());
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(columnCount);
            out.writeIntArray(columnTops);
            out.writeSparseArray(positionData);
        }

        @Override
        public String toString() {
            return "StaggeredGridView.GridListSavedState{"
                    + Integer.toHexString(System.identityHashCode(this)) + "}";
        }

        public static final Creator<GridListSavedState> CREATOR = new Creator<GridListSavedState>() {
            public GridListSavedState createFromParcel(Parcel in) {
                return new GridListSavedState(in);
            }

            public GridListSavedState[] newArray(int size) {
                return new GridListSavedState[size];
            }
        };
    }
}
