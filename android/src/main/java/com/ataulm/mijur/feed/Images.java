package com.ataulm.mijur.feed;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Images implements Iterable<ImageGalleryItem> {

    private final List<ImageGalleryItem> images;

    public Images(List<ImageGalleryItem> images) {
        this.images = Collections.unmodifiableList(images);
    }

    public static Images newInstance(List<ImageGalleryItem> images) {
        return new Images(images);
    }

    public static Images empty() {
        return new Images(Collections.<ImageGalleryItem>emptyList());
    }

    @Override
    public Iterator<ImageGalleryItem> iterator() {
        return images.iterator();
    }

    public int size() {
        return images.size();
    }

}
