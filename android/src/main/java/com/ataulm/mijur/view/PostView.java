package com.ataulm.mijur.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.ataulm.mijur.R;
import com.ataulm.mijur.data.Album;
import com.ataulm.mijur.data.GalleryItem;
import com.ataulm.mijur.data.Image;
import com.novoda.notils.caster.Views;
import com.novoda.notils.logger.toast.Toaster;

public class PostView extends FrameLayout {

    private FrameLayout contentContainerView;
    private ListView commentsListView;

    public PostView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PostView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onFinishInflate() {
        View.inflate(getContext(), R.layout.merge_post, this);

        commentsListView = Views.findById(this, R.id.post_comments_list);
        contentContainerView = (FrameLayout) LayoutInflater.from(getContext()).inflate(R.layout.view_post_content_container, commentsListView, false);
        commentsListView.addHeaderView(contentContainerView);

        commentsListView.setAdapter(null);
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
