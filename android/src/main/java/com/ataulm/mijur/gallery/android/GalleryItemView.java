package com.ataulm.mijur.gallery.android;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ataulm.mijur.gallery.GalleryItem;
import com.novoda.notils.caster.Views;

import uk.co.ataulm.mijur.Matisse;
import uk.co.ataulm.mijur.R;

public class GalleryItemView extends LinearLayout {

    private ImageView thumbImage;
    private TextView captionText;

    public GalleryItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GalleryItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onFinishInflate() {
        thumbImage = Views.findById(this, R.id.gallery_image_thumb);
        captionText = Views.findById(this, R.id.gallery_text_caption);
    }

    public void updateWith(GalleryItem galleryItem) {
        Matisse.load(galleryItem.imageUrl, thumbImage);
        captionText.setText(galleryItem.caption);
    }

}