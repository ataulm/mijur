package uk.co.ataulm.mijur.core.model;

import java.util.List;

public class Gallery {

    public List<GalleryElement> elements;

    public Gallery(List<GalleryElement> elements) {
        this.elements = elements;
    }

    public int getCount() {
        return elements.size();
    }

}
