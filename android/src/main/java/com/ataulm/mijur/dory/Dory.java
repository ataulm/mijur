package com.ataulm.mijur.dory;

import android.view.View;
import android.widget.ImageView;

import com.novoda.notils.logger.simple.Log;
import com.novoda.notils.logger.toast.Toaster;

import java.io.InputStream;

import rx.Observable;
import rx.Observer;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class Dory {

    public static class Bitmaps {

        private static Dory instance;

        public static Dory getInstance() {
            if (instance == null) {
                instance = new Dory(new ContentFetcher(), new DisplayManager(new BitmapDisplayer(), new BitmapStreamConverter()));
            }
            return instance;
        }

    }

    private final ContentFetcher contentFetcher;
    private final DisplayManager displayManager;

    public Dory(ContentFetcher retriever, DisplayManager displayManager) {
        this.contentFetcher = retriever;
        this.displayManager = displayManager;
    }

    public void display(final String url, final ImageView view) {
        Log.d("display: " + url);
        contentFetcher.observableFetchingInputStreamFrom(url).flatMap(new Func1<InputStream, Observable<View>>() {

            @Override
            public Observable<View> call(InputStream inputStream) {
                return displayManager.observableLoadingContentIntoView(inputStream, view);
            }

        })
                .subscribeOn(Schedulers.io())
                .subscribe(new ContentDisplayedInViewObserver(view));
    }

    private static class ContentDisplayedInViewObserver implements Observer<View> {

        private final ImageView view;

        public ContentDisplayedInViewObserver(ImageView view) {
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
