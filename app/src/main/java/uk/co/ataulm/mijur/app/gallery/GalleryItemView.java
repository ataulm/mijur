package uk.co.ataulm.mijur.app.gallery;

import android.content.Context;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;

import com.novoda.notils.logger.Novogger;

import java.util.Random;

import uk.co.ataulm.mijur.app.Matisse;
import uk.co.ataulm.mijur.core.model.GalleryItem;

public class GalleryItemView extends ImageView {

    private static final Random RANDOM = new Random();
    private static final SparseArray<Double> POSITION_HEIGHT_RATIOS = new SparseArray<Double>();

    private double heightRatio;

    public GalleryItemView(Context context) {
        super(context);
    }

    public GalleryItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    void updateWith(final int position, final GalleryItem item, final GalleryAdapter.GalleryItemListener listener) {
        applyTemporaryDifferentiation(position);

        setHeightRatio(position);
        Matisse.load(GalleryItem.getImageUrlFor(item), this);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onGalleryItemClicked(item);
            }
        });
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
        setMinimumHeight((int) (150 + 100 * RANDOM.nextDouble()));
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

    private void setHeightRatio(int position) {
        double ratio = POSITION_HEIGHT_RATIOS.get(position, 0.0);

        if (ratio == 0) {
            ratio = getRandomHeightRatio();
            POSITION_HEIGHT_RATIOS.append(position, ratio);
            Novogger.d("getPositionRatio:" + position + " ratio:" + ratio);
        }

        if (ratio != heightRatio) {
            heightRatio = ratio;
            requestLayout();
        }
    }

    private double getRandomHeightRatio() {
        // height will be 1.0 - 1.5 the width
        return (RANDOM.nextDouble() / 2.0) + 1.0;
    }

}
