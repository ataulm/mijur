package uk.co.ataulm.mijur.core.model;

import java.util.List;

public class Gallery {

    public final int albumCount;
    public final int imageCount;
    public List<GalleryItem> elements;

    public Gallery(List<GalleryItem> elements) {
        this.elements = elements;
        int count = 0;
        for (GalleryItem element : elements) {
            if (element.is_album) {
                count++;
            }
        }
        this.albumCount = count;
        this.imageCount = elements.size() - albumCount;
    }

    public List<GalleryItem> getValues() {
        return elements;
    }

    public int size() {
        return elements.size();
    }

    public int getAlbumCount() {
        return albumCount;
    }

    public int getImageCount() {
        return imageCount;
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
