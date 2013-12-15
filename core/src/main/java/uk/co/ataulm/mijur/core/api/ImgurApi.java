package uk.co.ataulm.mijur.core.api;

import retrofit.http.GET;
import retrofit.http.Path;

public interface ImgurApi {
    @GET("/image/{id}")
    ImageResponse getImage(@Path("id") String id);

    @GET("/gallery/image/{id}")
    GalleryImageResponse getGalleryImage(@Path("id") String id);
}
