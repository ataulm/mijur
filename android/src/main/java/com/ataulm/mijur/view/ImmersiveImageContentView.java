package com.ataulm.mijur.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ataulm.mijur.R;
import com.ataulm.mijur.data.Image;
import com.novoda.notils.caster.Views;
import com.squareup.picasso.Picasso;

public class ImmersiveImageContentView extends RelativeLayout {

    private ImageView imageView;
    private TextView titleView;
    private TextView descriptionView;

    public ImmersiveImageContentView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImmersiveImageContentView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onFinishInflate() {
        View.inflate(getContext(), R.layout.merge_immersive_image_content, this);

        imageView = Views.findById(this, R.id.image_content_image);
        titleView = Views.findById(this, R.id.image_title_text);
        descriptionView = Views.findById(this, R.id.image_description_text);
    }

    public void update(final Image image) {
        Picasso.with(getContext()).load(image.getPreviewImageUrl()).into(imageView);
        titleView.setText(image.getTitle());
        descriptionView.setText(image.getDescription());
    }

}
