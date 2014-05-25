package com.ataulm.mijur.data;

import com.ataulm.mijur.data.parser.CommentParser;

import java.io.InputStream;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class CommentRetriever {

    private static final String MOCK_JSON_COMMENT_PATTERN = "mocks/%s_comments.json";

    private final AssetFileManager assetFileManager;
    private final CommentParser commentParser;

    private CommentRetriever(AssetFileManager assetFileManager, CommentParser commentParser) {
        this.assetFileManager = assetFileManager;
        this.commentParser = commentParser;
    }

    public static CommentRetriever newInstance() {
        return new CommentRetriever(AssetFileManager.newInstance(), CommentParser.newInstance());
    }

    public Observable<List<Comment>> fetchCommentsForPost(final String postId) {
        return Observable.create(new Observable.OnSubscribe<List<Comment>>() {

            @Override
            public void call(Subscriber<? super List<Comment>> subscriber) {
                String pathToCommentsJson = String.format(MOCK_JSON_COMMENT_PATTERN, postId);

                InputStream inputStream = assetFileManager.open(pathToCommentsJson);
                List<Comment> comments = commentParser.parse(inputStream);
                subscriber.onNext(comments);
            }

        }).subscribeOn(Schedulers.io());
    }

}
