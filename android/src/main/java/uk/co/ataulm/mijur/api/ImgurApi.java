package uk.co.ataulm.mijur.api;

import retrofit.http.GET;
import retrofit.http.Path;
import uk.co.ataulm.mijur.api.response.GalleryResponse;

public interface ImgurApi {

    @GET("/gallery/{section}/{sort}/{page}")
    GalleryResponse getGallery(@Path("section") String section, @Path("sort") String sort, @Path("page") int page);

}
