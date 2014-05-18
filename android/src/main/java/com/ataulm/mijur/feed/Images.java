package com.ataulm.mijur.feed;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Images implements Iterable<Image> {

    private final List<Image> images;

    public Images(List<Image> images) {
        this.images = Collections.unmodifiableList(images);
    }

    public static Images newInstance(List<Image> images) {
        return new Images(images);
    }

    public static Images empty() {
        return new Images(Collections.<Image>emptyList());
    }

    @Override
    public Iterator<Image> iterator() {
        return images.iterator();
    }

    public int size() {
        return images.size();
    }

}
