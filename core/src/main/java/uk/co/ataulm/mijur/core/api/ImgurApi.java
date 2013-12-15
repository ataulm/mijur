package uk.co.ataulm.mijur.core.api;

import retrofit.http.GET;
import retrofit.http.Path;
import uk.co.ataulm.mijur.core.api.response.AlbumResponse;
import uk.co.ataulm.mijur.core.api.response.GalleryAlbumResponse;
import uk.co.ataulm.mijur.core.api.response.GalleryImageResponse;
import uk.co.ataulm.mijur.core.api.response.ImageResponse;

public interface ImgurApi {
    @GET("/image/{id}")
    ImageResponse getImage(@Path("id") String id);

    @GET("/gallery/image/{id}")
    GalleryImageResponse getGalleryImage(@Path("id") String id);

    @GET("/album/{id}")
    AlbumResponse getAlbum(@Path("id") String id);

    @GET("/gallery/album/{id}")
    GalleryAlbumResponse getGalleryAlbum(@Path("id") String id);
}
