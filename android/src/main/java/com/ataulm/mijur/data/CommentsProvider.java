package com.ataulm.mijur.data;

import java.util.List;

import rx.Observable;

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

    public Observable<List<Comment>> getCommentsForPost(String id) {
        return retriever.fetchCommentsForPost(id);
    }

    // TODO: As a user, I want to see the first level comments on a given post
    // TODO: As a user, I want to see the first level replies to a given comment

}
