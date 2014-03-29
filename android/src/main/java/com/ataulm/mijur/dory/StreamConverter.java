package com.ataulm.mijur.dory;

import java.io.InputStream;

import rx.Observable;

interface StreamConverter<T> {

    Observable<T> observableConverting(InputStream stream, int width, int height);

}
