package com.ataulm.mijur.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ScrollView;

import com.ataulm.mijur.R;
import com.ataulm.mijur.data.Album;
import com.ataulm.mijur.data.GalleryItem;
import com.ataulm.mijur.data.Image;
import com.novoda.notils.caster.Views;
import com.novoda.notils.logger.toast.Toaster;

public class GalleryPostView extends ScrollView {

    private FrameLayout contentContainerView;

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
        contentContainerView = Views.findById(this, R.id.post_content_container);
    }

    public void update(final GalleryItem item) {
        contentContainerView.removeAllViews();
        if (item.isAlbum()) {
            showAlbum((Album) item);
        } else {
            showImage((Image) item);
        }
    }

    private void showAlbum(final Album album) {
        FrameLayout root = (FrameLayout) View.inflate(getContext(), R.layout.view_album_content, contentContainerView);
        AlbumContentView view = (AlbumContentView) root.getChildAt(0);
        view.update(album, new ShowFullListener() {

            @Override
            public void onClickShowFull(GalleryItem item) {
                new Toaster(getContext()).popToast("I want more of album: " + album.getId());
            }

        });
    }

    private void showImage(Image image) {
        FrameLayout root = (FrameLayout) View.inflate(getContext(), R.layout.view_image_content, contentContainerView);
        ImageContentView view = (ImageContentView) root.getChildAt(0);
        view.update(image);
    }

}
