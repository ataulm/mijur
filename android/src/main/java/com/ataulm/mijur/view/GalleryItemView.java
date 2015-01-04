package com.ataulm.mijur.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.ataulm.mijur.ImageLoader;
import com.ataulm.mijur.R;
import com.ataulm.mijur.data.GalleryItem;
import com.novoda.notils.caster.Views;

public class GalleryItemView extends FrameLayout {

    private MatchParentWidthImageView imageView;

    public GalleryItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GalleryItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onFinishInflate() {
        View.inflate(getContext(), R.layout.merge_gallery_item, this);
        imageView = Views.findById(this, R.id.gallery_image);
    }

    public void update(final GalleryItem item, final OnClickListener listener) {
        ImageLoader.load(getContext(), item.getPreviewImageUrl(), imageView);

        if (listener == null) {
            return;
        }

        setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                listener.onClick(item);
            }

        });
    }

    public interface OnClickListener {

        void onClick(GalleryItem item);

    }

}
