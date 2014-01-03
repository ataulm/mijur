package uk.co.ataulm.mijur.gallery;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.novoda.notils.caster.Views;

import uk.co.ataulm.mijur.R;
import uk.co.ataulm.mijur.Matisse;
import uk.co.ataulm.mijur.model.GalleryItem;

public class GalleryItemHeaderView extends RelativeLayout {

    private ImageView imageView;
    private TextView caption;

    public GalleryItemHeaderView(Context context) {
        super(context);
    }

    public GalleryItemHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        imageView = Views.findById(this, R.id.image);
        caption = Views.findById(this, R.id.caption);
    }

    void updateWith(final GalleryItem item, final GalleryAdapter.GalleryItemListener listener) {
        Matisse.load(GalleryItem.getImageUrlFor(item), imageView);
        caption.setText(item.title);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onGalleryItemClicked(item);
            }
        });
    }

}
