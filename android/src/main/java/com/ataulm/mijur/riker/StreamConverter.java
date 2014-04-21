package com.ataulm.mijur.riker;

import java.io.InputStream;

import rx.Observable;

public interface StreamConverter<T> {

    /**
     * Converts an input stream into an object of type T.
     *
     * The width and height are the desired dimensions of the output.
     */
    Observable<T> observableConverting(InputStream stream, int width, int height);

}
