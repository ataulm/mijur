package com.ataulm.mijur.data.parser;

import com.ataulm.mijur.data.*;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class GalleryParser {

    private final Gson gson;

    private GalleryParser(Gson gson) {
        this.gson = gson;
    }

    public static GalleryParser newInstance() {
        return new GalleryParser(new Gson());
    }

    public Gallery parse(String json) {
        GsonGalleryResponse gsonGalleryResponse = gson.fromJson(json, GsonGalleryResponse.class);
        return parse(gsonGalleryResponse);
    }

    private Gallery parse(GsonGalleryResponse gsonGalleryResponse) {
        if (gsonGalleryResponse.success) {
            return parseGallery(gsonGalleryResponse.data);
        }
        return Gallery.empty();
    }

    private Gallery parseGallery(List<GsonGalleryItem> gsonGalleryItems) {
        List<GalleryItem> galleryItems = new ArrayList<GalleryItem>(gsonGalleryItems.size());
        for (GsonGalleryItem item : gsonGalleryItems) {
            galleryItems.add(parseGalleryItem(item));
        }
        return Gallery.newInstance(galleryItems);
    }

    private GalleryItem parseGalleryItem(GsonGalleryItem gsonGalleryItem) {
        GalleryItemCommon core = extractGalleryItemCommonFrom(gsonGalleryItem);

        if (gsonGalleryItem.isAlbum) {
            return parseAlbum(core, gsonGalleryItem);
        }
        return parseImage(core, gsonGalleryItem);
    }

    private GalleryItemCommon extractGalleryItemCommonFrom(GsonGalleryItem gsonGalleryItem) {
        return new GalleryItemCommon(gsonGalleryItem.id,
                gsonGalleryItem.title,
                gsonGalleryItem.description,
                new Time(gsonGalleryItem.datetime),
                gsonGalleryItem.link);
    }

    private Image parseImage(GalleryItemCommon core, GsonGalleryItem gsonGalleryItem) {
        return new Image(core, gsonGalleryItem.width, gsonGalleryItem.height, gsonGalleryItem.animated);
    }

    private Album parseAlbum(GalleryItemCommon core, GsonGalleryItem gsonGalleryItem) {
        final String coverLink = String.format("http://i.imgur.com/%s.jpg", gsonGalleryItem.cover);
        final Album.Cover cover = new Album.Cover(coverLink, gsonGalleryItem.coverWidth, gsonGalleryItem.coverHeight);
        return Album.newInstance(core, cover);
    }

}
