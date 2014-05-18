package com.ataulm.mijur.feed;

import rx.Observable;
import rx.functions.Func1;

public class FeedProvider {

    private final FeedRetriever retriever;

    private Feed cachedFeed;

    public FeedProvider() {
        this.retriever = FeedRetriever.newInstance();
    }

    public Observable<Feed> getFeed() {
        if (cachedFeed == null) {
            return refreshFeed();
        }
        return Observable.just(cachedFeed);
    }

    private Observable<Feed> refreshFeed() {
        return retriever.fetch().map(new Func1<Feed, Feed>() {

            @Override
            public Feed call(Feed feed) {
                cachedFeed = feed;
                return feed;
            }

        });
    }

}
