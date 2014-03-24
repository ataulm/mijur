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
        Observable<GalleryItems> galleryItemsObservable = galleryResponseObservable.map(new Func1<GalleryResponse, GalleryItems>() {

            @Override
            public GalleryItems call(GalleryResponse galleryResponse) {
                return unwrap(galleryResponse);
            }

            private GalleryItems unwrap(GalleryResponse galleryResponse) {
                List<GalleryItem> cleanedResponses = new ArrayList<GalleryItem>(galleryResponse.data.size());
                for (GalleryItemResponse response : galleryResponse.data) {
                    String imageUrl;
                    if (response.is_album) {
                        imageUrl = "http://i.imgur.com/" + response.cover + ".jpg";
                    } else {
                        imageUrl = response.link;
                    }
                    cleanedResponses.add(GalleryItem.from(response.title, response.description, imageUrl));
                }
                return new GalleryItems(cleanedResponses);
            }

        });

        return galleryItemsObservable;
    }

}
