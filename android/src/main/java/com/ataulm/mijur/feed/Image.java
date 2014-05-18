package com.ataulm.mijur.feed;

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

    @Override
    public String getDescription() {
        return core.description;
    }

    @Override
    public Time getGallerySubmissionTime() {
        return core.gallerySubmissionTime;
    }

    @Override
    public String getImageUrl() {
        return core.link;
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
