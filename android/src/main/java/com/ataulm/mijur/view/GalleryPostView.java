package com.ataulm.mijur.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ataulm.mijur.R;
import com.ataulm.mijur.data.GalleryItem;
import com.novoda.notils.caster.Views;
import com.squareup.picasso.Picasso;

public class GalleryPostView extends ScrollView {

    private TextView captionView;
    private ImageView imageView;

    public GalleryPostView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GalleryPostView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onFinishInflate() {
        setFillViewport(true);

        View.inflate(getContext(), R.layout.merge_gallery_post, this);
        captionView = Views.findById(this, R.id.gallery_post_caption);
        imageView = Views.findById(this, R.id.gallery_post_image);
    }

    public void update(final GalleryItem item) {
        captionView.setText(item.getTitle());
        Picasso.with(getContext()).load(item.getThumbnailUrl()).into(imageView);
    }

}
