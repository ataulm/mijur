package com.ataulm.mijur.gallery;

import com.ataulm.mijur.imgur.Imgur;
import com.ataulm.mijur.imgur.response.GalleryResponse;
import com.ataulm.mijur.model.Gallery;

import rx.Observable;
import rx.functions.Func1;

public class ApiGalleryItemsProvider implements GalleryItemsProvider {

    @Override
    public Observable<GalleryItems> newObservable() {
        Observable<GalleryResponse> galleryResponseObservable = Imgur.INSTANCE.galleryWith(Gallery.Section.HOT, Gallery.Sort.TIME, 0);

        return galleryResponseObservable.flatMap(new Func1<GalleryResponse, Observable<GalleryItems>>() {

            @Override
            public Observable<GalleryItems> call(GalleryResponse galleryResponse) {
                return Observable.just(unwrap(galleryResponse));
            }

            private GalleryItems unwrap(GalleryResponse galleryResponse) {
                return new GalleryItems(galleryResponse.data);
            }

        });
    }

}
