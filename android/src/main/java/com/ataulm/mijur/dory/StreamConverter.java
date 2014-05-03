package com.ataulm.mijur.dory;

import rx.Observable;

public interface StreamConverter<T> {

    /**
     * Converts a byte array into an object of type T.
     *
     * The width and height are the desired dimensions of the output.
     */
    Observable<T> observableConverting(byte[] input, int width, int height);

}
