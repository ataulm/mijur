package com.ataulm.mijur.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.ataulm.mijur.R;
import com.ataulm.mijur.feed.GalleryItem;
import com.novoda.notils.caster.Views;
import com.squareup.picasso.Picasso;

public class GalleryItemView extends FrameLayout {

    private ImageView imageView;

    public GalleryItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GalleryItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        layoutInflater.inflate(R.layout.merge_gallery_item, this, true);
        imageView = Views.findById(this, R.id.gallery_image);
    }

    public void update(GalleryItem item) {
        Picasso.with(getContext()).load(item.getThumbnailUrl()).into(imageView);
    }

}
