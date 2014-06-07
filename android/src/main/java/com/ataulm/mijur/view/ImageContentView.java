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
    private ClickListener listener;

    public ImageContentView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageContentView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setImageClickListener(ClickListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onFinishInflate() {
        setOrientation(VERTICAL);

        View.inflate(getContext(), R.layout.merge_image_content, this);
        titleView = Views.findById(this, R.id.image_title_text);
        imageView = Views.findById(this, R.id.image_content_image);
        descriptionView = Views.findById(this, R.id.image_description_text);
    }

    public void update(final Image image) {
        titleView.setText(image.getTitle());
        descriptionView.setText(image.getDescription());
        descriptionView.setVisibility(image.getDescription().isEmpty() ? GONE : VISIBLE);
        Picasso.with(getContext()).load(image.getPreviewImageUrl()).into(imageView);

        imageView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                listener.onClick(image);
            }

        });
    }

    public interface ClickListener {

        void onClick(Image image);

    }

}
