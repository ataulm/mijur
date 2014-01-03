package uk.co.ataulm.mijur.api;

import uk.co.ataulm.mijur.api.response.GalleryResponse;
import retrofit.http.GET;
import retrofit.http.Path;

public interface ImgurApi {

    @GET("/gallery/{section}/{sort}/{page}")
    GalleryResponse getGallery(@Path("section") String section, @Path("sort") String sort, @Path("page") int page);

}
