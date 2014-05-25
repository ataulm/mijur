package com.ataulm.mijur.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ataulm.mijur.R;
import com.ataulm.mijur.data.Comment;
import com.novoda.notils.caster.Views;

public class CommentView extends FrameLayout {

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
        commentTextView = Views.findById(this, R.id.comment_text);
    }

    public void update(Comment comment) {
        commentTextView.setText(comment.getText());
    }

}
