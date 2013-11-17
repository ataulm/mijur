package uk.co.ataulm.mijur.core;

import retrofit.http.GET;
import retrofit.http.Path;

interface Image {

    @GET("/image/{id}")
    Image image(@Path("id") String id);
}
