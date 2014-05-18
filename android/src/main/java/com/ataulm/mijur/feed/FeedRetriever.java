package com.ataulm.mijur.feed;

import android.content.res.AssetManager;

import com.ataulm.mijur.base.DeveloperError;
import com.ataulm.mijur.base.android.MijurApplication;
import com.ataulm.mijur.feed.parser.FeedParser;

import java.io.IOException;
import java.io.InputStream;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class FeedRetriever {

    private final AssetFileManager assetFileManager;
    private final FeedParser feedParser;

    private FeedRetriever(AssetFileManager assetFileManager, FeedParser feedParser) {
        this.assetFileManager = assetFileManager;
        this.feedParser = feedParser;
    }

    public static FeedRetriever newInstance() {
        return new FeedRetriever(AssetFileManager.newInstance(), FeedParser.newInstance());
    }

    public Observable<Feed> fetch() {
        return Observable.create(new Observable.OnSubscribe<Feed>() {

            @Override
            public void call(Subscriber<? super Feed> subscriber) {
                InputStream inputStream = assetFileManager.open("mocks/feed.json");
                Feed feed = feedParser.parse(inputStream);
                subscriber.onNext(feed);
                subscriber.onCompleted();
            }

        }).subscribeOn(Schedulers.io());
    }

    private static class AssetFileManager {

        private final AssetManager assetManager;

        private AssetFileManager(AssetManager assetManager) {
            this.assetManager = assetManager;
        }

        public static AssetFileManager newInstance() {
            return new AssetFileManager(MijurApplication.context().getAssets());
        }

        public InputStream open(String file) {
            try {
                return assetManager.open(file);
            } catch (IOException e) {
                throw DeveloperError.because("Failed to open file from Android Assets", e);
            }
        }

    }

}
