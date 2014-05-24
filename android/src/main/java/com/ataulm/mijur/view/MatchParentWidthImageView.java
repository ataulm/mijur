package com.ataulm.mijur.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class MatchParentWidthImageView extends ImageView {

    public MatchParentWidthImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MatchParentWidthImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Drawable drawable = getDrawable();
        if (drawable == null) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        } else {
            int desiredHeight = calculateDesiredHeight(drawable, widthMeasureSpec);
            int desiredHeightMeasureSpec = MeasureSpec.makeMeasureSpec(desiredHeight, MeasureSpec.EXACTLY);
            super.onMeasure(widthMeasureSpec, desiredHeightMeasureSpec);
        }
    }

    private int calculateDesiredHeight(Drawable drawable, int widthMeasureSpec) {
        float width = drawable.getIntrinsicWidth();
        float height = drawable.getIntrinsicHeight();

        float aspectRatio = width / height;
        int availableWidth = MeasureSpec.getSize(widthMeasureSpec);

        return (int) (availableWidth / aspectRatio + 0.5f);
    }

}
