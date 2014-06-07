package com.ataulm.mijur.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ataulm.mijur.R;
import com.ataulm.mijur.data.Album;
import com.ataulm.mijur.data.Image;
import com.ataulm.mijur.data.Images;
import com.novoda.notils.caster.Views;

public class AlbumContentView extends LinearLayout implements ImageContentView.ClickListener {

    private static final String SEE_MORE_PATTERN = "See more (%d remaining)";

    private final int maxImagesShownInView;

    private TextView titleView;
    private LinearLayout imagesView;
    private TextView moreView;

    private Album album;
    private Listener listener;

    public AlbumContentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        maxImagesShownInView = context.getResources().getInteger(R.integer.max_images_per_album_preview);
    }

    public AlbumContentView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        maxImagesShownInView = context.getResources().getInteger(R.integer.max_images_per_album_preview);
    }

    public void setAlbumClickListener(Listener listener) {
        this.listener = listener;
    }

    @Override
    protected void onFinishInflate() {
        setOrientation(VERTICAL);

        View.inflate(getContext(), R.layout.merge_album_content, this);
        titleView = Views.findById(this, R.id.album_content_title);
        imagesView = Views.findById(this, R.id.album_content_images);
        moreView = Views.findById(this, R.id.album_content_more);
    }

    public void update(final Album album) {
        this.album = album;
        titleView.setText(album.getTitle());
        updateImages(album.getImages());

        moreView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                listener.onClickViewEntire(album);
            }

        });
        updateMoreView(album);
    }

    private void updateImages(Images images) {
        imagesView.removeAllViews();
        for (int i = 0; i < Math.min(images.size(), maxImagesShownInView); i++) {
            Image image = images.get(i);
            ImageContentView view = getImageContentView(i);
            view.setImageClickListener(this);
            view.update(image);
            invalidate();
        }
    }

    private ImageContentView getImageContentView(int i) {
        LinearLayout root = (LinearLayout) View.inflate(getContext(), R.layout.view_image_content, imagesView);
        return (ImageContentView) root.getChildAt(i);
    }

    private void updateMoreView(Album album) {
        if (album.size() > maxImagesShownInView) {
            moreView.setVisibility(VISIBLE);
            int remaining = album.size() - 10;
            moreView.setText(String.format(SEE_MORE_PATTERN, remaining));
        } else {
            moreView.setVisibility(GONE);
        }
    }

    @Override
    public void onClick(Image image) {
        listener.onClickViewEntire(album, image);
    }

    public interface Listener extends ShowFullListener {

        void onClickViewEntire(Album album, Image startFromImage);

    }

}
