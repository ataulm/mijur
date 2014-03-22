package com.ataulm.mijur.gallery;

import rx.Observable;

public class MockGalleryItemsProvider implements GalleryItemsProvider {

    private static MockGalleryItemsProvider instance;

    private final GalleryItems galleryItems;

    private MockGalleryItemsProvider(GalleryItems galleryItems) {
        this.galleryItems = galleryItems;
    }

    public static GalleryItemsProvider getInstance() {
        if (instance == null) {
            instance = new MockGalleryItemsProvider(new GalleryItems(MockGalleryItems.getInstance()));
        }
        return instance;
    }

    @Override
    public Observable<GalleryItems> newObservable() {
        return Observable.just(galleryItems);
    }

}
