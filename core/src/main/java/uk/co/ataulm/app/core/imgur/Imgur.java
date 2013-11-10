package uk.co.ataulm.app.core.imgur;

import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Path;
import uk.co.ataulm.app.core.imgur.datamodel.Image;

public class Imgur {
    interface Api {
        static final String API_URL = "https://api.imgur.com/3/";
        static String CLIENT_ID = "<your_imgur_client_id>";
        static String CLIENT_SECRET = "<your_imgur_client_secret>";

        @GET("/image/{id}")
        Image image(@Path("id") String id);
    }

    public static void main(String... args) {
        RestAdapter restAdapter = new RestAdapter.Builder().setServer(Api.API_URL).build();

        Api imgur = restAdapter.create(Api.class);
        Image returnedImage = imgur.image("CoSS30e");
    }
}
