package com.ataulm.mijur.data;

public class Image implements GalleryItem {

    private final GalleryItemCommon core;
    private final int width;
    private final int height;
    private final boolean animated;

    public Image(GalleryItemCommon core, int width, int height, boolean animated) {
        this.core = core;
        this.width = width;
        this.height = height;
        this.animated = animated;
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
        int dot = core.link.lastIndexOf(".");
        return core.link.substring(0, dot) + "m" + core.link.substring(dot, core.link.length());
    }

    @Override
    public boolean isAlbum() {
        return false;
    }

}
