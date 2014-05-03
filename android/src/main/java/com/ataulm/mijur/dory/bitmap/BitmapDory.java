package com.ataulm.mijur.dory.bitmap;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.ataulm.mijur.dory.ContentFetcher;
import com.ataulm.mijur.dory.Displayer;
import com.ataulm.mijur.dory.Dory;
import com.ataulm.mijur.dory.StreamConverter;

import java.io.InputStream;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class BitmapDory implements Dory<Bitmap, ImageView> {

    private final ContentFetcher contentFetcher;
    private final Displayer<Bitmap, ImageView> displayer;
    private final StreamConverter<Bitmap> streamConverter;

    public static BitmapDory newInstance() {
        return new BitmapDory(new ContentFetcher(), new BitmapStreamConverter(), new BitmapDisplayer());
    }

    BitmapDory(ContentFetcher contentFetcher, StreamConverter<Bitmap> streamConverter, Displayer<Bitmap, ImageView> displayer) {
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
                int width = view.getWidth();
                int height = view.getHeight();
                return streamConverter.observableConverting(inputStream, width, height);
            }

        }).flatMap(new Func1<Bitmap, Observable<ImageView>>() {

            @Override
            public Observable<ImageView> call(Bitmap bitmap) {
                return display(bitmap, view);
            }

        });
    }

}
