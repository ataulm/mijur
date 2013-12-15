package uk.co.ataulm.mijur.core.api;

import retrofit.RetrofitError;

public class ImgurApiError extends RuntimeException {

    public ImgurApiError(RetrofitError throwable) {
        super("Tried fetching " + throwable.getUrl(), throwable);
    }

}
