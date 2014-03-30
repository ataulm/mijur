package com.ataulm.mijur.dory;

import java.io.InputStream;

import rx.Observable;

public interface StreamConverter<T> {

    Observable<T> observableConverting(InputStream stream, int width, int height);

}
