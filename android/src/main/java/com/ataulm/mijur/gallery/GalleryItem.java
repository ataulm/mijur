package com.ataulm.mijur.gallery;

public class GalleryItem {

    public final String caption;
    public final String description;
    public final String imageUrl;

    private GalleryItem(String caption, String description, String imageUrl) {
        this.caption = caption;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public static GalleryItem from(String caption, String description, String imageUrl) {
        if (description == null) {
            description = "";
        }
        return new GalleryItem(caption, description, imageUrl);
    }

    public static GalleryItem from(String caption, String imageUrl) {
        return GalleryItem.from(caption, null, imageUrl);
    }

}
