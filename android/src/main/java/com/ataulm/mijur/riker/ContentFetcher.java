package com.ataulm.mijur.riker;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import rx.Observable;
import rx.Subscriber;

import static rx.Observable.OnSubscribe;

public class ContentFetcher {

    private static final int READ_TIMEOUT_MILLIS = 10 * 1000;
    private static final int CONNECT_TIMEOUT_MILLIS = 10 * 1000;

    public Observable<InputStream> observableFetchingInputStreamFrom(final String target) {
        return Observable.create(new OnSubscribe<InputStream>() {

            URL url;
            HttpURLConnection connection;

            @Override
            public void call(Subscriber<? super InputStream> subscriber) {
                try {
                    url = new URL(target);
                } catch (MalformedURLException e) {
                    subscriber.onError(e);
                    return;
                }

                try {
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setConnectTimeout(CONNECT_TIMEOUT_MILLIS);
                    connection.setReadTimeout(READ_TIMEOUT_MILLIS);
                    subscriber.onNext(connection.getInputStream());
                } catch (IOException e) {
                    subscriber.onError(e);
                } finally {
                    connection.disconnect();
                }
            }

        });
    }

}
