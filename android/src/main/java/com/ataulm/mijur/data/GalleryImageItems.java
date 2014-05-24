package com.ataulm.mijur.data;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class GalleryImageItems implements Iterable<Image> {

    private final List<Image> images;

    public GalleryImageItems(List<Image> images) {
        this.images = Collections.unmodifiableList(images);
    }

    public static GalleryImageItems newInstance(List<Image> images) {
        return new GalleryImageItems(images);
    }

    public static GalleryImageItems empty() {
        return new GalleryImageItems(Collections.<Image>emptyList());
    }

    @Override
    public Iterator<Image> iterator() {
        return images.iterator();
    }

    public int size() {
        return images.size();
    }

}
