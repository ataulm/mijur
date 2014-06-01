package com.ataulm.mijur.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.ataulm.mijur.R;
import com.ataulm.mijur.data.*;
import com.novoda.notils.caster.Views;
import com.novoda.notils.logger.toast.Toaster;

public class PostView extends FrameLayout {

    private final CommentsAdapter commentsAdapter;

    private FrameLayout contentContainerView;
    private ListView commentsListView;
    private ClickListener listener;

    public PostView(Context context, AttributeSet attrs) {
        super(context, attrs);
        commentsAdapter = CommentsAdapter.newInstance();
    }

    public PostView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        commentsAdapter = CommentsAdapter.newInstance();
    }

    public void setPostViewClickListener(ClickListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onFinishInflate() {
        View.inflate(getContext(), R.layout.merge_post, this);

        commentsListView = Views.findById(this, R.id.post_comments_list);
        contentContainerView = (FrameLayout) LayoutInflater.from(getContext()).inflate(R.layout.view_post_content_container, commentsListView, false);
        commentsListView.addHeaderView(contentContainerView);

        commentsListView.setAdapter(commentsAdapter);
    }

    public void update(final GalleryItem item) {
        contentContainerView.removeAllViews();
        if (item.isAlbum()) {
            showAlbum((Album) item);
        } else {
            showImage((Image) item);
        }
    }

    public void update(Comments comments) {
        commentsAdapter.update(comments);
    }

    private void showAlbum(final Album album) {
        FrameLayout root = (FrameLayout) View.inflate(getContext(), R.layout.view_album_content, contentContainerView);
        AlbumContentView view = (AlbumContentView) root.getChildAt(0);
        view.setAlbumClickListener(listener);
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
        view.setImageClickListener(listener);
        view.update(image);
    }

    public interface ClickListener extends ImageContentView.ClickListener, AlbumContentView.AlbumClickListener {
    }

}
