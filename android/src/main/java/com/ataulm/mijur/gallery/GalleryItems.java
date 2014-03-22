package com.ataulm.mijur.gallery;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class GalleryItems implements Iterable<GalleryItem> {

    private final List<GalleryItem> galleryItems;

    public GalleryItems(List<GalleryItem> galleryItems) {
        this.galleryItems = Collections.unmodifiableList(galleryItems);
    }

    @Override
    public Iterator<GalleryItem> iterator() {
        return galleryItems.iterator();
    }

    public List<GalleryItem> asList() {
        return galleryItems;
    }

}
