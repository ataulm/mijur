package uk.co.ataulm.mijur.app.gallery;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import uk.co.ataulm.mijur.core.model.GalleryElement;

public class GalleryItemView extends ImageView {

    public GalleryItemView(Context context) {
        super(context);
    }

    public GalleryItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GalleryItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    void updateWith(int position, final GalleryElement item, final GalleryAdapter.GalleryItemListener listener) {
        // TODO: get the image using imageloader.load(url, this) instead of this background thing

        if (position % 2 == 0) {
            setBackgroundColor(getResources().getColor(android.R.color.holo_orange_dark));
        } else {
            setBackgroundColor(getResources().getColor(android.R.color.holo_orange_light));
        }

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onGalleryItemClicked(item.id);
            }
        });
    }

}
