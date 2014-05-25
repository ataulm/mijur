package com.ataulm.mijur.data;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Func1;

public class CommentsProvider {

    private static CommentsProvider commentsProvider;

    private final CommentsRetriever retriever;

    private CommentsProvider() {
        this.retriever = CommentsRetriever.newInstance();
    }

    public static CommentsProvider instance() {
        if (commentsProvider == null) {
            commentsProvider = new CommentsProvider();
        }
        return commentsProvider;
    }

    public Observable<Comments> getTopLevelCommentsForPost(String id) {
        return getCommentsForPost(id).map(new Func1<Comments, Comments>() {

            @Override
            public Comments call(Comments comments) {
                List<Comment> filteredComments = new ArrayList<Comment>();
                for (Comment comment : comments) {
                    if (!comment.isReply()) {
                        filteredComments.add(comment);
                    }
                }
                return new Comments(filteredComments);
            }

        });
    }

    public Observable<Comments> getRepliesToComment(String postId, final int commentId) {
        return getCommentsForPost(postId).map(new Func1<Comments, Comments>() {

            @Override
            public Comments call(Comments comments) {
                List<Comment> filteredComments = new ArrayList<Comment>();
                for (Comment comment : comments) {
                    if (comment.parentIdIs(commentId)) {
                        filteredComments.add(comment);
                    }
                }
                return new Comments(filteredComments);
            }

        });
    }

    private Observable<Comments> getCommentsForPost(String id) {
        return retriever.fetchCommentsForPost(id);
    }

}
