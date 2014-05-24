package com.ataulm.mijur.data;

public class Image implements GalleryItem {

    private final GalleryItemCore core;
    private final boolean animated;
    private final int width;
    private final int height;

    public Image(GalleryItemCore core, boolean animated, int width, int height) {
        this.core = core;
        this.animated = animated;
        this.width = width;
        this.height = height;
    }

    @Override
    public String getId() {
        return core.id;
    }

    @Override
    public String getTitle() {
        return core.title;
    }

    public String getDescription() {
        return core.description;
    }

    public Time getGallerySubmissionTime() {
        return core.gallerySubmissionTime;
    }

    @Override
    public String getThumbnailUrl() {
        // TODO: prettify this such that it's part of the parsing stage (album.cover too)
        int dot = core.link.lastIndexOf(".");
        return core.link.substring(0, dot) + "m" + core.link.substring(dot, core.link.length());
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public boolean isAlbum() {
        return false;
    }

    public boolean isAnimated() {
        return animated;
    }

}
