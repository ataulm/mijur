package com.ataulm.mijur.data;

import com.ataulm.mijur.data.parser.CommentParser;

import java.io.InputStream;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class CommentsRetriever {

    private static final String MOCK_JSON_COMMENT_PATTERN = "mocks/%s_comments.json";

    private final AssetFileManager assetFileManager;
    private final CommentParser commentParser;

    private CommentsRetriever(AssetFileManager assetFileManager, CommentParser commentParser) {
        this.assetFileManager = assetFileManager;
        this.commentParser = commentParser;
    }

    public static CommentsRetriever newInstance() {
        return new CommentsRetriever(AssetFileManager.newInstance(), CommentParser.newInstance());
    }

    public Observable<Comments> fetchCommentsForPost(final String postId) {
        return Observable.create(new Observable.OnSubscribe<Comments>() {

            @Override
            public void call(Subscriber<? super Comments> subscriber) {
                String pathToCommentsJson = String.format(MOCK_JSON_COMMENT_PATTERN, postId);

                InputStream inputStream = assetFileManager.open(pathToCommentsJson);
                Comments comments = commentParser.parse(inputStream);
                subscriber.onNext(comments);
            }

        }).subscribeOn(Schedulers.io());
    }

}
