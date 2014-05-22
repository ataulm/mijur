package com.ataulm.mijur.feed;

import java.util.Iterator;

public class AlbumGalleryItem implements GalleryItem, Iterable<ImageGalleryItem> {

    private final GalleryItemCore core;
    private final Cover cover;

    private Images images;

    public AlbumGalleryItem(GalleryItemCore core, Cover cover) {
        this.core = core;
        this.cover = cover;
        this.images = Images.empty();
    }

    public void set(Images images) {
        this.images = images;
    }

    public int size() {
        return images.size();
    }

    @Override
    public Iterator<ImageGalleryItem> iterator() {
        return images.iterator();
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
    public Time getGallerySubmissionTime() {
        return core.gallerySubmissionTime;
    }

    @Override
    public String getThumbnailUrl() {
        int dot = cover.link.lastIndexOf(".");
        return cover.link.substring(0, dot) + "m" + cover.link.substring(dot, cover.link.length());
    }

    @Override
    public int getWidth() {
        return cover.width;
    }

    @Override
    public int getHeight() {
        return cover.height;
    }

    public static class Cover {

        private final String link;
        private final int width;
        private final int height;

        public Cover(String link, int width, int height) {
            this.link = link;
            this.width = width;
            this.height = height;
        }

    }

}
