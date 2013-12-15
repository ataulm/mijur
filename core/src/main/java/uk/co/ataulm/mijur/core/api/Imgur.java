package uk.co.ataulm.mijur.core.api;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

/**
 * Attempts OAuth 2.0 authentication without use of Scribe
 */
public class Imgur implements RequestInterceptor {
    private static final String PROPERTY_KEY_AUTHORISATION = "Authorization";
    private static final String PROPERTY_VALUE_CLIENT_ID_PREFIX = "Client-ID ";
    private static final String API_URL = "https://api.imgur.com/3";

    private static Imgur imgur;
    private static ImgurApi imgurRestAdapter;

    private Imgur() {
    }

    public static ImgurApi instance() {
        if (imgur == null) {
            setupImgurClient();
        }
        return imgurRestAdapter;
    }

    private static void setupImgurClient() {
        imgur = new Imgur();
        RestAdapter adapter = new RestAdapter.Builder()
                .setServer(API_URL)
                .setRequestInterceptor(imgur)
                .build();
        imgurRestAdapter = adapter.create(ImgurApi.class);
    }

    public static ImageResponse getImageWith(String id) {
        return instance().getImage(id);
    }

    @Override
    public void intercept(RequestFacade request) {
        request.addHeader(PROPERTY_KEY_AUTHORISATION, PROPERTY_VALUE_CLIENT_ID_PREFIX + ApiConstants.API_CLIENT_ID);
    }

}
