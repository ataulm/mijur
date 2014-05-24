package com.ataulm.mijur.data;

import rx.Observable;

public class AlbumProvider {

    private static AlbumProvider albumProvider;

    private final AlbumRetriever retriever;

    private AlbumProvider() {
        this.retriever = AlbumRetriever.newInstance();
    }

    public static AlbumProvider instance() {
        if (albumProvider == null) {
            albumProvider = new AlbumProvider();
        }
        return albumProvider;
    }

    public Observable<Album> getAlbumWithId(String id) {
        return retriever.fetchAlbum(id);
    }

}
