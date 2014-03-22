package com.ataulm.mijur.imgur;

import com.ataulm.mijur.imgur.response.GalleryResponse;

import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;
import com.ataulm.mijur.model.Gallery;

public enum Imgur {

    IMGUR;

    private static final Api API = ImgurRestAdapter.newInstance();

    public Observable<GalleryResponse> getGalleryWith(Gallery.Section section, Gallery.Sort sort, int page) {
        return API.getGallery(section.toString(), sort.toString(), page);
    }

    public static interface Api {

        @GET("/gallery/{section}/{sort}/{page}")
        Observable<GalleryResponse> getGallery(@Path("section") String section, @Path("sort") String sort, @Path("page") int page);

    }

}
