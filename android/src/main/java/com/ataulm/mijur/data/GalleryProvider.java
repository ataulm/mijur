package com.ataulm.mijur.data;

import com.ataulm.mijur.base.DeveloperError;

import rx.Observable;
import rx.functions.Action1;
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

    public Observable<GalleryItem> getGalleryItem(final String id) {
        return getGallery().flatMap(new Func1<Gallery, Observable<? extends GalleryItem>>() {

            @Override
            public Observable<? extends GalleryItem> call(Gallery gallery) {
                for (GalleryItem item : gallery) {
                    if (item.getId().equals(id)) {
                        return evaluateAndReturn(item);
                    }
                }
                throw DeveloperError.because("Specified item not found in Gallery");
            }

        });
    }

    /**
     * Gets the album (with all its Images) else returns the item (Image).
     */
    private Observable<? extends GalleryItem> evaluateAndReturn(GalleryItem item) {
        if (item.isAlbum()) {
            return getAlbum(item.getId());
        }
        return Observable.just(item);
    }

    private Observable<Album> getAlbum(final String id) {
        return AlbumProvider.instance().getAlbumWithId(id);
    }

    private Observable<Gallery> refreshGallery() {
        return retriever.fetch().doOnNext(new Action1<Gallery>() {
            @Override
            public void call(Gallery galleryItems) {
                cachedGallery = galleryItems;
            }
        });
    }

}
