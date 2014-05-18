package com.ataulm.mijur.feed.parser;

import com.ataulm.mijur.feed.*;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class FeedParser {

    private final Gson gson;

    private FeedParser(Gson gson) {
        this.gson = gson;
    }

    public static FeedParser newInstance() {
        return new FeedParser(new Gson());
    }

    public Feed parse(String json) {
        GsonGalleryResponse gsonFeed = gson.fromJson(json, GsonGalleryResponse.class);
        return parseFeed(gsonFeed);
    }

    private Feed parseFeed(GsonGalleryResponse gsonGalleryResponse) {
        if (gsonGalleryResponse.success) {
            List<GalleryItem> galleryItems = new ArrayList<GalleryItem>(gsonGalleryResponse.data.size());
            for (GsonGalleryItem item : gsonGalleryResponse.data) {
                galleryItems.add(parseGalleryItem(item));
            }
            return Feed.newInstance(Gallery.newInstance(galleryItems));
        } else {
            return Feed.empty();
        }
    }

    private GalleryItem parseGalleryItem(GsonGalleryItem gsonGalleryItem) {
        GalleryItemCore core = parseGalleryItemCore(gsonGalleryItem);

        if (gsonGalleryItem.isAlbum) {
            return parseImage(core, gsonGalleryItem);
        }
        return parseAlbum(core, gsonGalleryItem);
    }

    private GalleryItemCore parseGalleryItemCore(GsonGalleryItem gsonGalleryItem) {
        return new GalleryItemCore(gsonGalleryItem.id,
                gsonGalleryItem.title,
                gsonGalleryItem.description,
                new Time(gsonGalleryItem.datetime),
                gsonGalleryItem.link);
    }

    private Image parseImage(GalleryItemCore core, GsonGalleryItem gsonGalleryItem) {
        return new Image(core, gsonGalleryItem.animated, gsonGalleryItem.width, gsonGalleryItem.height);
    }

    private Album parseAlbum(GalleryItemCore core, GsonGalleryItem gsonGalleryItem) {
        final String coverLink = String.format("http://i.imgur.com/%s.jpg", gsonGalleryItem.cover);
        final Album.Cover cover = new Album.Cover(coverLink, gsonGalleryItem.coverWidth, gsonGalleryItem.coverHeight);
        return new Album(core, cover);
    }

}
