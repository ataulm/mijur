package com.ataulm.mijur.imgur;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import com.ataulm.mijur.ApiConstants;

public class ImgurRestAdapter {

    private static final String API_URL = "https://api.imgur.com/3";
    private static final String PROPERTY_KEY_AUTHORISATION = "Authorization";
    private static final String PROPERTY_VALUE_CLIENT_ID_PREFIX = "Client-ID ";

    public static Imgur.Api newInstance() {
        RestAdapter adapter = new RestAdapter.Builder()
                .setServer(API_URL)
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addHeader(PROPERTY_KEY_AUTHORISATION, PROPERTY_VALUE_CLIENT_ID_PREFIX + ApiConstants.API_CLIENT_ID);
                    }
                }).build();

        return adapter.create(Imgur.Api.class);
    }

}