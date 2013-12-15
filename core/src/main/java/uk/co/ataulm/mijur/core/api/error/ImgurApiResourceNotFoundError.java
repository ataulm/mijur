package uk.co.ataulm.mijur.core.api.error;

import retrofit.RetrofitError;

public class ImgurApiResourceNotFoundError extends RuntimeException {

    public ImgurApiResourceNotFoundError(RetrofitError throwable) {
        super("Tried fetching " + throwable.getUrl(), throwable);
    }

}
