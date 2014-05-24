package com.ataulm.mijur.data.parser;

import com.ataulm.mijur.data.*;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.InputStream;
import java.io.InputStreamReader;
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

    public Gallery parse(InputStream stream) {
        JsonReader reader = new JsonReader(new InputStreamReader(stream));
        GsonGalleryResponse gsonGallery = gson.fromJson(reader, GsonGalleryResponse.class);
        return parse(gsonGallery);
    }

    public Gallery parse(String json) {
        GsonGalleryResponse gsonGallery = gson.fromJson(json, GsonGalleryResponse.class);
        return parse(gsonGallery);
    }

    private Gallery parse(GsonGalleryResponse gsonGalleryResponse) {
        if (gsonGalleryResponse.success) {
            List<GalleryItem> galleryItems = new ArrayList<GalleryItem>(gsonGalleryResponse.data.size());
            for (GsonGalleryItem item : gsonGalleryResponse.data) {
                galleryItems.add(parseGalleryItem(item));
            }
            return Gallery.newInstance(galleryItems);
        } else {
            return Gallery.empty();
        }
    }

    private GalleryItem parseGalleryItem(GsonGalleryItem gsonGalleryItem) {
        GalleryItemCore core = parseGalleryItemCore(gsonGalleryItem);

        if (gsonGalleryItem.isAlbum) {
            return parseAlbum(core, gsonGalleryItem);
        }
        return parseImage(core, gsonGalleryItem);
    }

    private GalleryItemCore parseGalleryItemCore(GsonGalleryItem gsonGalleryItem) {
        return new GalleryItemCore(gsonGalleryItem.id,
                gsonGalleryItem.title,
                gsonGalleryItem.description,
                new Time(gsonGalleryItem.datetime),
                gsonGalleryItem.link);
    }

    private ImageGalleryItem parseImage(GalleryItemCore core, GsonGalleryItem gsonGalleryItem) {
        return new ImageGalleryItem(core, gsonGalleryItem.animated, gsonGalleryItem.width, gsonGalleryItem.height);
    }

    private AlbumGalleryItem parseAlbum(GalleryItemCore core, GsonGalleryItem gsonGalleryItem) {
        final String coverLink = String.format("http://i.imgur.com/%s.jpg", gsonGalleryItem.cover);
        final AlbumGalleryItem.Cover cover = new AlbumGalleryItem.Cover(coverLink, gsonGalleryItem.coverWidth, gsonGalleryItem.coverHeight);
        return new AlbumGalleryItem(core, cover);
    }

}
