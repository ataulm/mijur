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

    public Observable<List<Comment>> getTopLevelCommentsForPost(String id) {
        return getCommentsForPost(id).map(new Func1<List<Comment>, List<Comment>>() {

            @Override
            public List<Comment> call(List<Comment> comments) {
                List<Comment> filteredComments = new ArrayList<Comment>();
                for (Comment comment : comments) {
                    if (!comment.isReply()) {
                        filteredComments.add(comment);
                    }
                }
                return filteredComments;
            }

        });
    }

    public Observable<List<Comment>> getRepliesToComment(String postId, final int commentId) {
        return getCommentsForPost(postId).map(new Func1<List<Comment>, List<Comment>>() {

            @Override
            public List<Comment> call(List<Comment> comments) {
                List<Comment> filteredComments = new ArrayList<Comment>();
                for (Comment comment : comments) {
                    if (comment.parentIdIs(commentId)) {
                        filteredComments.add(comment);
                    }
                }
                return filteredComments;
            }

        });
    }

    private Observable<List<Comment>> getCommentsForPost(String id) {
        return retriever.fetchCommentsForPost(id);
    }

}
