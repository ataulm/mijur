package com.ataulm.mijur.data;

import android.graphics.Point;

public class Album implements GalleryItem {

    private static final Album EMPTY = new Album(GalleryItemCommon.none(), Cover.none(), Images.empty());

    private final GalleryItemCommon core;
    private final Cover cover;
    private final Images images;

    private Album(GalleryItemCommon core, Cover cover, Images images) {
        this.core = core;
        this.cover = cover;
        this.images = images;
    }

    public static Album newInstance(GalleryItemCommon core, Cover cover) {
        return new Album(core, cover, Images.empty());
    }

    public static Album newInstance(GalleryItemCommon core, Cover cover, Images images) {
        return new Album(core, cover, images);
    }

    public static Album empty() {
        return EMPTY;
    }

    @Override
    public String getId() {
        return core.id;
    }

    @Override
    public String getTitle() {
        return core.title;
    }

    @Override
    public String getDescription() {
        return core.description;
    }

    @Override
    public String getPreviewImageUrl() {
        int dot = cover.link.lastIndexOf(".");
        return cover.link.substring(0, dot) + "m" + cover.link.substring(dot, cover.link.length());
    }

    @Override
    public boolean isAlbum() {
        return true;
    }

    public int size() {
        return images.size();
    }

    public Images getImages() {
        return images;
    }

    public static class Cover {

        private static final Cover NONE = new Cover("", new Point(0, 0));

        private final String link;
        private final Point size;

        public Cover(String link, Point size) {
            this.link = link;
            this.size = size;
        }

        public static Cover none() {
            return NONE;
        }

    }

}
