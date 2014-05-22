package com.ataulm.mijur.feed;

import com.ataulm.mijur.base.DeveloperError;

import rx.Observable;
import rx.functions.Func1;

public class FeedProvider {

    private static FeedProvider feedProvider;

    private final FeedRetriever retriever;

    private Feed cachedFeed;

    private FeedProvider() {
        this.retriever = FeedRetriever.newInstance();
    }

    public static FeedProvider instance() {
        if (feedProvider == null) {
            feedProvider = new FeedProvider();
        }
        return feedProvider;
    }

    public Observable<Feed> getFeed() {
        if (cachedFeed == null) {
            return refreshFeed();
        }
        return Observable.just(cachedFeed);
    }

    public Observable<GalleryItem> getGalleryItemWith(final String id) {
        return getFeed().map(new Func1<Feed, GalleryItem>() {

            @Override
            public GalleryItem call(Feed feed) {
                for (GalleryItem item : feed.gallery) {
                    if (item.getId().equals(id)) {
                        return item;
                    }
                }
                throw DeveloperError.because("Specified gallery item not found in feed");
            }

        });
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
