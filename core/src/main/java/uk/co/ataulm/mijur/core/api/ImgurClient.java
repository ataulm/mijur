package uk.co.ataulm.mijur.core.api;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Attempts OAuth 2.0 authentication without use of Scribe
 */
public class ImgurClient implements RequestInterceptor {
    private static final String PROPERTY_KEY_AUTHORISATION = "Authorization";
    private static final String PROPERTY_VALUE_CLIENT_ID_PREFIX = "Client-ID ";
    private static final String API_URL = "https://api.imgur.com/3";

    private static ImgurClient imgurAuth;
    private static Imgur imgur;

    private ImgurClient() {
    }

    public static Imgur get() {
        if (imgurAuth == null) {
            setupImgurClient();
        }
        return imgur;
    }

    private static void setupImgurClient() {
        imgurAuth = new ImgurClient();
        RestAdapter adapter = new RestAdapter.Builder()
            .setServer(API_URL)
            .setRequestInterceptor(imgurAuth)
            .build();
        imgur = adapter.create(Imgur.class);
    }

    @Override
    public void intercept(RequestFacade request) {
        request.addHeader(PROPERTY_KEY_AUTHORISATION, PROPERTY_VALUE_CLIENT_ID_PREFIX + ApiConstants.API_CLIENT_ID);
    }

    public interface Imgur {
        @GET("/image/{id}")
        ImageResponse image(@Path("id") String id);
    }
}
