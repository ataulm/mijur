package com.ataulm.mijur.riker.bitmap;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.ataulm.mijur.riker.ContentFetcher;
import com.ataulm.mijur.riker.Displayer;
import com.ataulm.mijur.riker.Riker;
import com.ataulm.mijur.riker.StreamConverter;

import java.io.InputStream;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class BitmapRiker implements Riker<Bitmap, ImageView> {

    private final ContentFetcher contentFetcher;
    private final Displayer<Bitmap, ImageView> displayer;
    private final StreamConverter<Bitmap> streamConverter;

    public static BitmapRiker newInstance() {
        return new BitmapRiker(new ContentFetcher(), new BitmapStreamConverter(), new BitmapDisplayer());
    }

    BitmapRiker(ContentFetcher contentFetcher, StreamConverter<Bitmap> streamConverter, Displayer<Bitmap, ImageView> displayer) {
        this.contentFetcher = contentFetcher;
        this.displayer = displayer;
        this.streamConverter = streamConverter;
    }

    @Override
    public Observable<InputStream> fetch(String url) {
        return contentFetcher.observableFetchingInputStreamFrom(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<ImageView> display(Bitmap content, ImageView view) {
        displayer.display(content, view);

        return Observable.just(view);
    }

    @Override
    public Observable<ImageView> load(final String url, final ImageView view) {
        return fetch(url).flatMap(new Func1<InputStream, Observable<Bitmap>>() {

            @Override
            public Observable<Bitmap> call(InputStream inputStream) {
                return streamConverter.observableConverting(inputStream, view.getWidth(), view.getHeight());
            }

        }).flatMap(new Func1<Bitmap, Observable<ImageView>>() {

            @Override
            public Observable<ImageView> call(Bitmap bitmap) {
                return display(bitmap, view);
            }

        });
    }

}
