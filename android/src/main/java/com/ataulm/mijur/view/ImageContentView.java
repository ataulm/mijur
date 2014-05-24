package com.ataulm.mijur.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ataulm.mijur.R;
import com.ataulm.mijur.data.Image;
import com.novoda.notils.caster.Views;
import com.squareup.picasso.Picasso;

public class ImageContentView extends LinearLayout {

    private TextView titleView;
    private MatchParentWidthImageView imageView;
    private TextView descriptionView;

    public ImageContentView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageContentView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onFinishInflate() {
        setOrientation(VERTICAL);

        View.inflate(getContext(), R.layout.merge_image_content, this);
        titleView = Views.findById(this, R.id.image_content_title);
        imageView = Views.findById(this, R.id.image_content_image);
        descriptionView = Views.findById(this, R.id.image_content_description);
    }

    public void update(final Image image) {
        titleView.setText(image.getTitle());
        descriptionView.setText(image.getDescription());
        Picasso.with(getContext()).load(image.getThumbnailUrl()).into(imageView);
    }

}
