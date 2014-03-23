package com.ataulm.mijur.gallery;

import com.ataulm.mijur.imgur.Imgur;
import com.ataulm.mijur.imgur.response.GalleryItemResponse;
import com.ataulm.mijur.imgur.response.GalleryResponse;
import com.ataulm.mijur.model.Gallery;

import java.util.ArrayList;
import java.util.List;

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
                List<GalleryItem> cleanedResponses = new ArrayList<GalleryItem>(galleryResponse.data.size());
                for (GalleryItemResponse response : galleryResponse.data) {
                    if (response.is_album) {
                        cleanedResponses.add(GalleryItem.from(response.title, response.description, response.cover));
                    } else {
                        cleanedResponses.add(GalleryItem.from(response.title, response.description, response.link));
                    }
                }
                return new GalleryItems(cleanedResponses);
            }

        });
    }

}
