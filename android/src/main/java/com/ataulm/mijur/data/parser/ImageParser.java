package com.ataulm.mijur.data.parser;

import com.ataulm.mijur.data.GalleryItemCommon;
import com.ataulm.mijur.data.Image;
import com.ataulm.mijur.data.Time;

public class ImageParser {

    private ImageParser() {
    }

    public static ImageParser newInstance() {
        return new ImageParser();
    }

    public Image parse(GsonImage gsonImage) {
        GalleryItemCommon core = parseCore(gsonImage);
        boolean animated = gsonImage.animated;
        int width = gsonImage.width;
        int height = gsonImage.height;
        return new Image(core, width, height, animated);
    }

    public Image parse(GsonGalleryItem galleryItem) {
        if (galleryItem.isAlbum) {
            throw new IllegalArgumentException("This is an album, not an image!");
        }
        return parseImage(galleryItem);
    }

    private Image parseImage(GsonGalleryItem galleryItem) {
        GalleryItemCommon core = new GalleryItemCommon(galleryItem.id,
                galleryItem.title == null ? "" : galleryItem.title,
                galleryItem.description == null ? "" : galleryItem.description,
                new Time(galleryItem.datetime),
                galleryItem.link);

        boolean animated = galleryItem.animated;
        int width = galleryItem.width;
        int height = galleryItem.height;

        return new Image(core, width, height, animated);
    }

    private GalleryItemCommon parseCore(GsonImage gsonImage) {
        return new GalleryItemCommon(gsonImage.id,
                gsonImage.title == null ? "" : gsonImage.title,
                gsonImage.description == null ? "" : gsonImage.description,
                new Time(gsonImage.datetime),
                gsonImage.link);
    }

}
