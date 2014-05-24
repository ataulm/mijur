package com.ataulm.mijur.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.ataulm.mijur.R;
import com.ataulm.mijur.data.GalleryItem;
import com.novoda.notils.caster.Views;
import com.squareup.picasso.Picasso;

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
        setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                listener.onClick(item);
            }

        });

        Picasso.with(getContext()).load(item.getThumbnailUrl()).into(imageView);
    }

    public interface OnClickListener {

        void onClick(GalleryItem item);

    }

}
