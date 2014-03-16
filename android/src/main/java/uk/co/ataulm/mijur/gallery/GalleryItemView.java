package uk.co.ataulm.mijur.gallery;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import uk.co.ataulm.mijur.R;
import uk.co.ataulm.mijur.Matisse;
import uk.co.ataulm.mijur.model.GalleryItem;

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
        String thumbnailSuffix = getResources().getString(R.string.thumbnail_suffix);
        String imageUrl = GalleryItem.getThumbnailImageUrlFor(item, thumbnailSuffix);
        Matisse.load(imageUrl, this);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onGalleryItemClicked(position, item);
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (heightRatio > 0.0) {
            // set the IMAGE_ID PAGE_VIEWS SIZE_BYTES
            int width = MeasureSpec.getSize(widthMeasureSpec);
            int height = (int) (width * heightRatio);
            setMeasuredDimension(width, height);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

}
