package uk.co.ataulm.mijur.core.api;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;

/**
 * Attempts OAuth 2.0 authentication without use of Scribe
 */
public class Imgur {
    private static final String PROPERTY_KEY_AUTHORISATION = "Authorization";
    private static final String PROPERTY_VALUE_CLIENT_ID_PREFIX = "Client-ID ";
    private static final String API_URL = "https://api.imgur.com/3";
    private static Imgur imgur;
    private static ImgurApi imgurApi;

    private Imgur() {
    }

    public static ImgurApi instance() {
        if (imgur == null) {
            setupImgurClient();
        }
        return imgurApi;
    }

    private static void setupImgurClient() {
        imgur = new Imgur();
        RestAdapter adapter = new RestAdapter.Builder()
                .setServer(API_URL)
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addHeader(PROPERTY_KEY_AUTHORISATION, PROPERTY_VALUE_CLIENT_ID_PREFIX + ApiConstants.API_CLIENT_ID);
                    }
                }).build();
        imgurApi = adapter.create(ImgurApi.class);
    }

    public static ImageResponse getImageWith(String id) {
        try {
            return instance().getImage(id);
        } catch (RetrofitError e) {
            if (e.getResponse() != null && isBadHttp(e.getResponse().getStatus())) {
                throw new ImgurApiResourceNotFoundError(e);
            }
            throw new ImgurApiError(e);
        }
    }

    private static boolean isBadHttp(int status) {
        return !(status >= 200 && status < 300);
    }

}
