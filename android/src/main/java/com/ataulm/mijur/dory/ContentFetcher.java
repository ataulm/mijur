package com.ataulm.mijur.dory;

import java.io.ByteArrayOutputStream;
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
    private static final int BUFFER_SIZE_BYTES = 128 * 1024;

    public Observable<byte[]> observableFetchingInputStreamFrom(final String target) {
        return Observable.create(new OnSubscribe<byte[]>() {


            @Override
            public void call(Subscriber<? super byte[]> subscriber) {
                URL url;
                HttpURLConnection connection = null;

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
                    byte[] streamAsByteArray = byteArrayFromStreamOrThrow(connection.getInputStream());
                    subscriber.onNext(streamAsByteArray);
                } catch (IOException e) {
                    subscriber.onError(e);
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }

            private byte[] byteArrayFromStreamOrThrow(InputStream stream) {
                try {
                    return byteArrayFromStream(stream);
                } catch (IOException e) {
                    throw new RuntimeException("Couldn't create bytearray from stream", e);
                }
            }

            private byte[] byteArrayFromStream(InputStream stream) throws IOException {
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                byte[] buf = new byte[BUFFER_SIZE_BYTES];
                int n;
                while ((n = stream.read(buf)) >= 0) {
                    output.write(buf, 0, n);
                }
                return output.toByteArray();
            }

        });
    }

}
