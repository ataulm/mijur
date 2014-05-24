package com.ataulm.mijur.data;

import android.content.res.AssetManager;

import com.ataulm.mijur.base.DeveloperError;
import com.ataulm.mijur.base.android.MijurApplication;
import com.ataulm.mijur.data.parser.AlbumParser;

import java.io.IOException;
import java.io.InputStream;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class AlbumRetriever {

    private static final String MOCK_JSON_ALBUM_PATTERN = "mocks/%s.json";

    private final AssetFileManager assetFileManager;
    private final AlbumParser albumParser;

    private AlbumRetriever(AssetFileManager assetFileManager, AlbumParser albumParser) {
        this.assetFileManager = assetFileManager;
        this.albumParser = albumParser;
    }

    public static AlbumRetriever newInstance() {
        return new AlbumRetriever(AssetFileManager.newInstance(), AlbumParser.newInstance());
    }

    public Observable<Album> fetchAlbum(final String albumId) {
        return Observable.create(new Observable.OnSubscribe<Album>() {

            @Override
            public void call(Subscriber<? super Album> subscriber) {
                String pathToAlbumJson = String.format(MOCK_JSON_ALBUM_PATTERN, albumId);

                InputStream inputStream = assetFileManager.open(pathToAlbumJson);
                Album album = albumParser.parse(inputStream);
                subscriber.onNext(album);
                subscriber.onCompleted();
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
