package uk.co.ataulm.mijur.api;

import java.net.HttpURLConnection;

import uk.co.ataulm.mijur.api.error.ImgurApiError;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import uk.co.ataulm.mijur.ApiConstants;
import uk.co.ataulm.mijur.api.error.ImgurApiResourceNotFoundError;
import uk.co.ataulm.mijur.model.Gallery;

public class Imgur {

    private static final String API_URL = "https://api.imgur.com/3";
    private static final String PROPERTY_KEY_AUTHORISATION = "Authorization";
    private static final String PROPERTY_VALUE_CLIENT_ID_PREFIX = "Client-ID ";

    private static Imgur imgur;
    private static ImgurApi imgurApi;

    private Imgur() {
    }

    public static ImgurApi instance() {
        if (imgur == null) {
            imgur = new Imgur();
            imgurApi = createNewImgurApiAdapter();
        }
        return imgurApi;
    }

    private static ImgurApi createNewImgurApiAdapter() {
        RestAdapter adapter = new RestAdapter.Builder()
                .setServer(API_URL)
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addHeader(PROPERTY_KEY_AUTHORISATION, PROPERTY_VALUE_CLIENT_ID_PREFIX + ApiConstants.API_CLIENT_ID);
                    }
                }).build();

        return adapter.create(ImgurApi.class);
    }

    public static Gallery getGalleryWith(Gallery.Section section, Gallery.Sort sort, int page) {
        try {
            return instance().getGallery(section.toString(), sort.toString(), page).getData();
        } catch (RetrofitError e) {
            if (e.getResponse() != null && isBadHttp(e.getResponse().getStatus())) {
                throw new ImgurApiResourceNotFoundError(e);
            }
            throw new ImgurApiError(e);
        }
    }

    private static boolean isBadHttp(int status) {
        return !(status >= HttpURLConnection.HTTP_OK && status < HttpURLConnection.HTTP_MOVED_PERM);
    }

}
