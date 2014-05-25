package com.ataulm.mijur.data;

import com.ataulm.mijur.data.parser.AlbumParser;

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
            }

        }).subscribeOn(Schedulers.io());
    }

}
