package com.ataulm.mijur.data;

import android.graphics.Point;

public class Image implements GalleryItem {

    private final GalleryItemCommon core;
    private final Point size;
    private final boolean animated;

    public Image(GalleryItemCommon core, Point size, boolean animated) {
        this.core = core;
        this.size = size;
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
