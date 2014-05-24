package com.ataulm.mijur.data;

public class ImageGalleryItem implements GalleryItem {

    private final GalleryItemCore core;
    private final boolean animated;
    private final int width;
    private final int height;

    public ImageGalleryItem(GalleryItemCore core, boolean animated, int width, int height) {
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
        // TODO: prettify this such that it's part of the parsing stage (album.cover too)
        int dot = core.link.lastIndexOf(".");
        return core.link.substring(0, dot) + "m" + core.link.substring(dot, core.link.length());
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public boolean isAnimated() {
        return animated;
    }

}
