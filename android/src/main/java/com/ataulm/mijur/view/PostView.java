package com.ataulm.mijur.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.ataulm.mijur.R;
import com.ataulm.mijur.data.Album;
import com.ataulm.mijur.data.Comment;
import com.ataulm.mijur.data.GalleryItem;
import com.ataulm.mijur.data.Image;
import com.novoda.notils.caster.Views;
import com.novoda.notils.logger.toast.Toaster;

import java.util.Collections;
import java.util.List;

public class PostView extends FrameLayout {

    private final CommentsAdapter commentsAdapter;

    private FrameLayout contentContainerView;
    private ListView commentsListView;

    public PostView(Context context, AttributeSet attrs) {
        super(context, attrs);
        commentsAdapter = CommentsAdapter.newInstance();
    }

    public PostView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        commentsAdapter = CommentsAdapter.newInstance();
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

    public void update(List<Comment> comments) {
        commentsAdapter.update(comments);
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

    private static class CommentsAdapter extends BaseAdapter {

        private List<Comment> comments;

        private CommentsAdapter(List<Comment> comments) {
            this.comments = comments;
        }

        public static CommentsAdapter newInstance() {
            return new CommentsAdapter(Collections.<Comment>emptyList());
        }

        public void update(List<Comment> comments) {
            this.comments = comments;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return comments.size();
        }

        @Override
        public Comment getItem(int position) {
            return comments.get(position);
        }

        @Override
        public long getItemId(int position) {
            return getItem(position).getId();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View commentView = convertView;
            if (commentView == null) {
                commentView = createCommentView(parent);
            }
            Comment comment = getItem(position);
            ((CommentView) commentView).update(comment);
            return commentView;
        }

        private View createCommentView(ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            return inflater.inflate(R.layout.view_post_comment, parent, false);
        }

    }

}
