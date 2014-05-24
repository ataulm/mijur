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

public class AlbumContentView extends LinearLayout {

    private static final int MAX_IMAGES_SHOWN_IN_VIEW = 10;
    private static final String SEE_MORE_PATTERN = "See more (%d remaining)";

    private TextView titleView;
    private LinearLayout imagesView;
    private TextView descriptionView;
    private TextView moreView;

    public AlbumContentView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AlbumContentView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onFinishInflate() {
        setOrientation(VERTICAL);

        View.inflate(getContext(), R.layout.merge_album_content, this);
        titleView = Views.findById(this, R.id.album_content_title);
        imagesView = Views.findById(this, R.id.album_content_images);
        descriptionView = Views.findById(this, R.id.album_content_description);
        moreView = Views.findById(this, R.id.album_content_more);
    }

    public void update(final Album album, final ShowFullListener listener) {
        titleView.setText(album.getTitle());
        descriptionView.setText(album.getDescription());
        updateImages(album.getImages());

        moreView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                listener.onClickShowFull(album);
            }

        });
        updateMoreView(album);
    }

    private void updateImages(Images images) {
        imagesView.removeAllViews();
        for (int i = 0; i < Math.min(images.size(), MAX_IMAGES_SHOWN_IN_VIEW); i++) {
            Image image = images.get(i);
            ImageContentView view = getImageContentView(i);
            view.update(image);
            invalidate();
        }
    }

    private ImageContentView getImageContentView(int i) {
        LinearLayout root = (LinearLayout) View.inflate(getContext(), R.layout.view_image_content, imagesView);
        return (ImageContentView) root.getChildAt(i);
    }

    private void updateMoreView(Album album) {
        if (album.size() > MAX_IMAGES_SHOWN_IN_VIEW) {
            moreView.setVisibility(VISIBLE);
            int remaining = album.size() - 10;
            moreView.setText(String.format(SEE_MORE_PATTERN, remaining));
        } else {
            moreView.setVisibility(GONE);
        }
    }

}
