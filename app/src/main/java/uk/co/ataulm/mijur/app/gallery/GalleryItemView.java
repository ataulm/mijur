package uk.co.ataulm.mijur.app.gallery;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.novoda.notils.logger.Novogger;

import java.util.Random;

import uk.co.ataulm.mijur.core.model.GalleryElement;

public class GalleryItemView extends ImageView {

    private static final Random random = new Random();

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
        Novogger.d(String.format("updateWith called. position: %d, itemid :%s", position, item.id));

        // TODO: remove when image is set correctly - this is for the dummy items to help show variation
        if (position % 2 == 0) {
            setBackgroundColor(getResources().getColor(android.R.color.holo_orange_dark));
        } else {
            setBackgroundColor(getResources().getColor(android.R.color.holo_orange_light));
        }
        setMinimumHeight((int) (150 + 100 * random.nextDouble()));

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onGalleryItemClicked(item.id);
            }
        });
    }

}
