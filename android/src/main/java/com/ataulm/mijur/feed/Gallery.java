package com.ataulm.mijur.feed;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Gallery implements Iterable<GalleryItem> {

    private final List<GalleryItem> galleryItems;

    private Gallery(List<GalleryItem> galleryItems) {
        this.galleryItems = Collections.unmodifiableList(galleryItems);
    }

    public static Gallery newInstance(List<GalleryItem> galleryItems) {
        return new Gallery(galleryItems);
    }

    public static Gallery empty() {
        return new Gallery(Collections.<GalleryItem>emptyList());
    }

    @Override
    public Iterator<GalleryItem> iterator() {
        return galleryItems.iterator();
    }

    public int size() {
        return galleryItems.size();
    }

    public GalleryItem get(int position) {
        return galleryItems.get(position);
    }

}
