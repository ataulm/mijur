package com.ataulm.mijur.data;

import android.content.res.AssetManager;

import com.ataulm.mijur.base.DeveloperError;
import com.ataulm.mijur.base.android.MijurApplication;
import com.ataulm.mijur.data.parser.GalleryParser;

import java.io.IOException;
import java.io.InputStream;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class GalleryRetriever {

    private static final String MOCK_JSON_GALLERY = "mocks/gallery.json";

    private final AssetFileManager assetFileManager;
    private final GalleryParser galleryParser;

    private GalleryRetriever(AssetFileManager assetFileManager, GalleryParser galleryParser) {
        this.assetFileManager = assetFileManager;
        this.galleryParser = galleryParser;
    }

    public static GalleryRetriever newInstance() {
        return new GalleryRetriever(AssetFileManager.newInstance(), GalleryParser.newInstance());
    }

    public Observable<Gallery> fetch() {
        return Observable.create(new Observable.OnSubscribe<Gallery>() {

            @Override
            public void call(Subscriber<? super Gallery> subscriber) {
                InputStream inputStream = assetFileManager.open(MOCK_JSON_GALLERY);
                Gallery gallery = galleryParser.parse(inputStream);
                subscriber.onNext(gallery);
            }

        }).subscribeOn(Schedulers.io());
    }

    private static class AssetFileManager {

        private final AssetManager assetManager;

        private AssetFileManager(AssetManager assetManager) {
            this.assetManager = assetManager;
        }

        public static AssetFileManager newInstance() {
            return new AssetFileManager(MijurApplication.context().getAssets());
        }

        public InputStream open(String file) {
            try {
                return assetManager.open(file);
            } catch (IOException e) {
                throw DeveloperError.because("Failed to open file from Android Assets", e);
            }
        }

    }

}
