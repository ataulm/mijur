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
        GsonGalleryResponse gsonGalleryResponse = gson.fromJson(reader, GsonGalleryResponse.class);
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
            return parseAlbumGalleryItem(core, gsonGalleryItem);
        }
        return ImageParser.newInstance().parse(gsonGalleryItem);
    }

    private GalleryItemCommon extractGalleryItemCommonFrom(GsonGalleryItem gsonGalleryItem) {
        return new GalleryItemCommon(gsonGalleryItem.id,
                gsonGalleryItem.title == null ? "": gsonGalleryItem.title,
                gsonGalleryItem.description == null ? "" : gsonGalleryItem.description,
                new Time(gsonGalleryItem.datetime),
                gsonGalleryItem.link);
    }

    private Album parseAlbumGalleryItem(GalleryItemCommon core, GsonGalleryItem gsonGalleryItem) {
        final String coverLink = String.format("http://i.imgur.com/%s.jpg", gsonGalleryItem.cover);
        final Album.Cover cover = new Album.Cover(coverLink, gsonGalleryItem.coverWidth, gsonGalleryItem.coverHeight);
        return Album.newInstance(core, cover);
    }

}
