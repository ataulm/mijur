package com.ataulm.mijur.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ataulm.mijur.R;
import com.ataulm.mijur.data.Comment;
import com.novoda.notils.caster.Views;

public class CommentView extends RelativeLayout {

    private TextView userTextView;
    private TextView scoreTextView;
    private TextView timeTextView;
    private TextView commentTextView;

    public CommentView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CommentView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onFinishInflate() {
        View.inflate(getContext(), R.layout.merge_post_comment, this);
        userTextView = Views.findById(this, R.id.user_text);
        scoreTextView = Views.findById(this, R.id.score_text);
        timeTextView = Views.findById(this, R.id.time_text);
        commentTextView = Views.findById(this, R.id.comment_text);
    }

    public void update(Comment comment) {
        userTextView.setText(comment.getAuthor());
        scoreTextView.setText(comment.getScore());
        timeTextView.setText(comment.getTime().toString());
        commentTextView.setText(comment.getText());
    }

}
