package com.ataulm.mijur.dory;

import android.view.View;

import com.ataulm.mijur.dory.function.DisplayContentInView;

import java.io.InputStream;

import rx.Observable;

public class DisplayManager<T, U extends View> {

    private final Displayer<T, U> displayer;
    private final StreamConverter<T> streamConverter;

    public DisplayManager(Displayer displayer, StreamConverter streamConverter) {
        this.displayer = displayer;
        this.streamConverter = streamConverter;
    }

    public Observable<U> observableLoadingContentIntoView(InputStream stream, U view) {
        int width = view.getWidth();
        int height = view.getHeight();
        return streamConverter.observableConverting(stream, width, height).map(new DisplayContentInView<T, U>(displayer, view));
    }

    public Observable<U> observableLoadingContentIntoView(InputStream stream, U view, int width, int height) {
        return streamConverter.observableConverting(stream, width, height).map(new DisplayContentInView<T, U>(displayer, view));
    }

}
