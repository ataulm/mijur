package com.ataulm.mijur.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ataulm.mijur.R;
import com.ataulm.mijur.data.Image;
import com.novoda.notils.caster.Views;
import com.squareup.picasso.Picasso;

public class ImmersiveImageContentView extends FrameLayout {

    private ImageView imageView;
    private TextView titleView;
    private TextView descriptionView;
    private LinearLayout captionContainer;

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
        captionContainer = Views.findById(this, R.id.image_caption_container);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int parentLeft = getPaddingLeft();
        int parentTop = getPaddingTop();
        int parentWidth = right - left - getPaddingRight();
        int parentHeight = bottom - top - getPaddingBottom();

        layoutImage(parentLeft, parentTop, parentWidth, parentHeight);
        layoutCaption(parentLeft, parentTop, parentWidth, parentHeight);
    }

    private void layoutImage(int parentLeft, int parentTop, int parentWidth, int parentHeight) {
        imageView.layout(parentLeft, parentTop, parentWidth, parentHeight);
    }

    private void layoutCaption(int parentLeft, int parentTop, int parentWidth, int parentHeight) {
        int captionHeight = captionContainer.getMeasuredHeight();
        int top = parentTop + parentHeight - captionHeight;
        captionContainer.layout(parentLeft, top, parentLeft + parentWidth, top + captionHeight);
    }

    public void update(final Image image, final Listener listener) {
        Picasso.with(getContext()).load(image.getPreviewImageUrl()).into(imageView);
        titleView.setText(image.getTitle());
        descriptionView.setText(image.getTitle());

        imageView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                captionContainer.setVisibility(captionContainer.getVisibility() == VISIBLE ? GONE : VISIBLE);
                listener.onToggleImmersiveMode(captionContainer.getVisibility() == GONE);
            }

        });
    }

    public interface Listener {

        void onToggleImmersiveMode(boolean immersiveMode);

    }

}
