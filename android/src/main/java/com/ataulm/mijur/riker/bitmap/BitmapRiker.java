package com.ataulm.mijur.riker.bitmap;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.ataulm.mijur.dory.BitmapDisplayer;
import com.ataulm.mijur.dory.BitmapStreamConverter;
import com.ataulm.mijur.dory.ContentFetcher;
import com.ataulm.mijur.dory.Displayer;
import com.ataulm.mijur.dory.StreamConverter;
import com.ataulm.mijur.dory.function.DisplayContentInView;
import com.ataulm.mijur.riker.ContentDisplayedInViewObserver;
import com.ataulm.mijur.riker.Riker;

import java.io.InputStream;

import rx.Observable;
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
        return contentFetcher.observableFetchingInputStreamFrom(url);
    }

    @Override
    public Observable<ImageView> display(Bitmap content, ImageView view) {
        displayer.display(content, view);
        return Observable.just(view);
    }

    @Override
    public Observable<ImageView> load(String url, ImageView view) {
        // TODO: chain fetch and display
        return Observable.just(view);
    }

    // TODO: Delete anything below this line once functionality is secured /////////////////////////////////////////////

    public void display(final String url, final ImageView view) {
        Func1<InputStream, Observable<ImageView>> convertStreamThenReturnViewWithDisplayedContent = new Func1<InputStream, Observable<ImageView>>() {

            @Override
            public Observable<ImageView> call(InputStream inputStream) {
                return observableLoadingContentIntoView(inputStream, view);
            }

        };

        contentFetcher.observableFetchingInputStreamFrom(url)
                .flatMap(convertStreamThenReturnViewWithDisplayedContent)
                .subscribeOn(Schedulers.io())
                .subscribe(new ContentDisplayedInViewObserver(view));
    }

    public Observable<ImageView> observableLoadingContentIntoView(InputStream stream, ImageView view) {
        int width = view.getWidth();
        int height = view.getHeight();

        Observable convertingStreamToContent = streamConverter.observableConverting(stream, width, height);
        DisplayContentInView<Bitmap, ImageView> displayContentInView = new DisplayContentInView<Bitmap, ImageView>(displayer, view);

        return convertingStreamToContent.map(displayContentInView);
    }

    public Observable<ImageView> observableLoadingContentIntoView(InputStream stream, ImageView view, int width, int height) {
        return streamConverter.observableConverting(stream, width, height)
                .map(new DisplayContentInView<Bitmap, ImageView>(displayer, view));
    }

}
