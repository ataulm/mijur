package com.ataulm.mijur.dory;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.ataulm.mijur.dory.function.DisplayContentInView;
import com.novoda.notils.logger.simple.Log;
import com.novoda.notils.logger.toast.Toaster;

import java.io.InputStream;

import rx.Observable;
import rx.Observer;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class Dory<T, U extends View> {

    public static class Bitmaps {

        private static Dory instance;

        public static Dory getInstance() {
            if (instance == null) {
                instance = new Dory<Bitmap, ImageView>(new ContentFetcher(), new BitmapStreamConverter(), new BitmapDisplayer());
            }
            return instance;
        }

    }

    private final ContentFetcher contentFetcher;
    private final Displayer displayer;
    private final StreamConverter streamConverter;

    public Dory(ContentFetcher retriever, StreamConverter streamConverter, Displayer displayer) {
        this.contentFetcher = retriever;
        this.displayer = displayer;
        this.streamConverter = streamConverter;
    }

    public void display(final String url, final U view) {
        Log.d("display: " + url);
        contentFetcher.observableFetchingInputStreamFrom(url).flatMap(new Func1<InputStream, Observable<U>>() {

            @Override
            public Observable<U> call(InputStream inputStream) {
                return observableLoadingContentIntoView(inputStream, view);
            }

        })
                .subscribeOn(Schedulers.io())
                .subscribe(new ContentDisplayedInViewObserver(view));
    }

    public Observable<U> observableLoadingContentIntoView(InputStream stream, U view) {
        int width = view.getWidth();
        int height = view.getHeight();
        return streamConverter.observableConverting(stream, width, height).map(new DisplayContentInView<T, U>(displayer, view));
    }

    public Observable<U> observableLoadingContentIntoView(InputStream stream, U view, int width, int height) {
        return streamConverter.observableConverting(stream, width, height).map(new DisplayContentInView<T, U>(displayer, view));
    }

    private static class ContentDisplayedInViewObserver implements Observer<View> {

        private final View view;

        public ContentDisplayedInViewObserver(View view) {
            this.view = view;
        }

        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            new Toaster(view.getContext()).popToast("well, poop");
        }

        @Override
        public void onNext(View view) {
            new Toaster(view.getContext()).popToast("well, alright");
        }

    }

}
