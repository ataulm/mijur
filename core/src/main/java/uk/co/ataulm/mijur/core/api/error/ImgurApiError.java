package uk.co.ataulm.mijur.core.api.error;

import retrofit.RetrofitError;

public class ImgurApiError extends RuntimeException {

    public ImgurApiError(RetrofitError throwable) {
        super("Tried fetching " + throwable.getUrl(), throwable);
    }

}
