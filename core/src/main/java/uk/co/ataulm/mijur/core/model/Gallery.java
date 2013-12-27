package uk.co.ataulm.mijur.core.model;

import java.util.ArrayList;
import java.util.List;

public class Gallery {

    public final List<GalleryItem> elements;

    public Gallery(List<GalleryItem> elements) {
        this.elements = elements;
        // currently, no support for albums or animated images
        List<GalleryItem> toRemove = new ArrayList<GalleryItem>();
        for (GalleryItem element : elements) {
            if (element.is_album || element.animated) {
                toRemove.add(element);
            }
        }
        elements.removeAll(toRemove);
    }

    public int size() {
        return elements.size();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(getClass().getName());
        builder.append("{");

        for (GalleryItem element : elements) {
            builder.append(element.link).append(", ");
        }

        builder.delete(builder.lastIndexOf(", "), builder.length());
        builder.append("}");
        return builder.toString();
    }

    public enum Section {
        HOT, TOP, USER;

        @Override
        public String toString() {
            return super.toString().toLowerCase();
        }
    }

    public enum Sort {
        VIRAL, TIME;

        @Override
        public String toString() {
            return super.toString().toLowerCase();
        }
    }

}
