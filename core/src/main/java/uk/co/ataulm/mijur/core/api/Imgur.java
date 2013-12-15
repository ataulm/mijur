package uk.co.ataulm.mijur.core.api;

import java.net.HttpURLConnection;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import uk.co.ataulm.mijur.core.api.error.ImgurApiError;
import uk.co.ataulm.mijur.core.api.error.ImgurApiResourceNotFoundError;
import uk.co.ataulm.mijur.core.model.*;

/**
 * Attempts OAuth 2.0 authentication without use of Scribe
 */
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

    public static Image getImageWith(String id) {
        try {
            return instance().getImage(id).data;
        } catch (RetrofitError e) {
            if (e.getResponse() != null && isBadHttp(e.getResponse().getStatus())) {
                throw new ImgurApiResourceNotFoundError(e);
            }
            throw new ImgurApiError(e);
        }
    }

    public static GalleryImage getGalleryImageWith(String id) {
        try {
            return instance().getGalleryImage(id).data;
        } catch (RetrofitError e) {
            if (e.getResponse() != null && isBadHttp(e.getResponse().getStatus())) {
                throw new ImgurApiResourceNotFoundError(e);
            }
            throw new ImgurApiError(e);
        }
    }

    public static Album getAlbumWith(String id) {
        try {
            return instance().getAlbum(id).data;
        } catch (RetrofitError e) {
            if (e.getResponse() != null && isBadHttp(e.getResponse().getStatus())) {
                throw new ImgurApiResourceNotFoundError(e);
            }
            throw new ImgurApiError(e);
        }
    }

    public static GalleryAlbum getGalleryAlbumWith(String id) {
        try {
            return instance().getGalleryAlbum(id).data;
        } catch (RetrofitError e) {
            if (e.getResponse() != null && isBadHttp(e.getResponse().getStatus())) {
                throw new ImgurApiResourceNotFoundError(e);
            }
            throw new ImgurApiError(e);
        }
    }

    public static void main(String... args) {
        Imgur.getGalleryWith("hot", "time", 1);
    }

    public static Gallery getGalleryWith(String section, String sort, int page) {
        try {
            Gallery gallery = instance().getGallery(section, sort, page).getData();
            System.out.println("Gallery has count of: " + gallery.getCount());
            return gallery;
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
