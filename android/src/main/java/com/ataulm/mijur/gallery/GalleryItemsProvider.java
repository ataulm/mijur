package com.ataulm.mijur.gallery;

import rx.Observable;

public interface GalleryItemsProvider {

    Observable<GalleryItems> newObservable();

}
