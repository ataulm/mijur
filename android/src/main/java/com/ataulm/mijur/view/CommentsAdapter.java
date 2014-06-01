package com.ataulm.mijur.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.ataulm.mijur.R;
import com.ataulm.mijur.data.Comment;
import com.ataulm.mijur.data.Comments;

class CommentsAdapter extends BaseAdapter {

    private Comments comments;

    CommentsAdapter(Comments comments) {
        this.comments = comments;
    }

    public static CommentsAdapter newInstance() {
        return new CommentsAdapter(Comments.empty());
    }

    public void update(Comments comments) {
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
