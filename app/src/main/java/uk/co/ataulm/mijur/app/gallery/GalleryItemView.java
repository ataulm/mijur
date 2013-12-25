package uk.co.ataulm.mijur.app.gallery;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import uk.co.ataulm.mijur.R;
import uk.co.ataulm.mijur.app.Matisse;
import uk.co.ataulm.mijur.core.model.GalleryItem;

public class GalleryItemView extends ImageView {

    private double heightRatio;

    public GalleryItemView(Context context) {
        super(context);
    }

    public GalleryItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    void setHeightRatio(double heightRatio) {
        this.heightRatio = heightRatio;
        requestLayout();
    }

    void updateWith(final int position, final GalleryItem item, final GalleryAdapter.GalleryItemListener listener) {
        applyTemporaryDifferentiation(position);

        String thumbnailSuffix = getResources().getString(R.string.thumbnail_suffix);
        String imageUrl = GalleryItem.getThumbnailImageUrlFor(item, thumbnailSuffix);
        Matisse.load(imageUrl, this);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onGalleryItemClicked(item);
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (heightRatio > 0.0) {
            // set the image views size
            int width = MeasureSpec.getSize(widthMeasureSpec);
            int height = (int) (width * heightRatio);
            setMeasuredDimension(width, height);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    /**
     * This is just temporary to make it look interesting during development!
     * @param position
     */
    private void applyTemporaryDifferentiation(int position) {
        if (position % 2 == 0) {
            setBackgroundColor(getResources().getColor(android.R.color.holo_orange_dark));
        } else {
            setBackgroundColor(getResources().getColor(android.R.color.holo_orange_light));
        }
    }

}
