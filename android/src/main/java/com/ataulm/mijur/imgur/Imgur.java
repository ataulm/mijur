package com.ataulm.mijur.imgur;

import com.ataulm.mijur.imgur.response.GalleryResponse;
import com.ataulm.mijur.model.Gallery;

import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

public enum Imgur {

    INSTANCE;

    private static final Api API = ImgurRestAdapter.newInstance();

    public Observable<GalleryResponse> galleryWith(Gallery.Section section, Gallery.Sort sort, int page) {
        return API.galleryWith(section.toString(), sort.toString(), page);
    }

    public static interface Api {

        @GET("/gallery/{section}/{sort}/{page}")
        Observable<GalleryResponse> galleryWith(@Path("section") String section, @Path("sort") String sort, @Path("page") int page);

    }

}
