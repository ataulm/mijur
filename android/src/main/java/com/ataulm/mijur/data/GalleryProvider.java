package com.ataulm.mijur.data;

import com.ataulm.mijur.base.DeveloperError;

import rx.Observable;
import rx.functions.Func1;

public class GalleryProvider {

    private static GalleryProvider galleryProvider;

    private final GalleryRetriever retriever;

    private Gallery cachedGallery;

    private GalleryProvider() {
        this.retriever = GalleryRetriever.newInstance();
    }

    public static GalleryProvider instance() {
        if (galleryProvider == null) {
            galleryProvider = new GalleryProvider();
        }
        return galleryProvider;
    }

    public Observable<Gallery> getGallery() {
        if (cachedGallery == null) {
            return refreshGallery();
        }
        return Observable.just(cachedGallery);
    }

    public Observable<GalleryItem> getGalleryItemWith(final String id) {
        return getGallery().map(new Func1<Gallery, GalleryItem>() {

            @Override
            public GalleryItem call(Gallery gallery) {
                for (GalleryItem item : gallery) {
                    if (item.getId().equals(id)) {
                        return item;
                    }
                }
                throw DeveloperError.because("Specified item not found in Gallery");
            }

        });
    }

    private Observable<Gallery> refreshGallery() {
        return retriever.fetch().map(new Func1<Gallery, Gallery>() {

            @Override
            public Gallery call(Gallery gallery) {
                cachedGallery = gallery;
                return gallery;
            }

        });
    }

}
