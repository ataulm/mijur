package uk.co.ataulm.imgur.core;

import retrofit.http.GET;
import retrofit.http.Path;

import uk.co.ataulm.imgur.core.ApiConstants;
import uk.co.ataulm.imgur.core.datamodel.Image;

public class Imgur {
    interface Api {
        static final String API_URL = "https://api.imgur.com/3/";
        static final String CLIENT_ID = ApiConstants.API_CLIENT_ID;
        static final String CLIENT_SECRET = "what";//BuildConfig.API_CLIENT_SECRET;

        @GET("/image/{id}")
        Image image(@Path("id") String id);
    }

    public static void main(String... args) {
        System.out.println("client id = " + Api.CLIENT_ID);
        System.out.println("client secret = " + Api.CLIENT_SECRET);

//        RestAdapter restAdapter = new RestAdapter.Builder().setServer(Api.API_URL).build();
//
//        Api imgur = restAdapter.create(Api.class);
//        Image returnedImage = imgur.image("CoSS30e");
    }
}
