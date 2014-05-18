package com.ataulm.mijur.feed;

import com.google.gson.annotations.SerializedName;

import java.io.InputStream;
import java.util.List;

public class FeedParser {

    public static FeedParser newInstance() {
        return new FeedParser();
    }

    public Feed parse(InputStream stream) {
        // TODO: parse stream, return feed
        return null;
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
        final Album.Cover cover = new Album.Cover(gsonGalleryItem.link, gsonGalleryItem.coverWidth, gsonGalleryItem.coverHeight);
        return new Album(core, cover);
    }

    private static class GsonGalleryResponse {

        @SerializedName("data")
        private List<GsonGalleryItem> data;

        @SerializedName("success")
        private boolean success;

        @SerializedName("status")
        private int status;

    }

    private static class GsonGalleryItem {

        @SerializedName("id")
        public String id;

        @SerializedName("title")
        public String title;

        @SerializedName("description")
        public String description;

        @SerializedName("datetime")
        public long datetime;

        @SerializedName("link")
        public String link;

        @SerializedName("is_album")
        public boolean isAlbum;

        ///////////////////////
        /* Only GalleryAlbum */
        ///////////////////////

        @SerializedName("cover")
        public String cover;

        @SerializedName("cover_width")
        public int coverWidth;

        @SerializedName("cover_height")
        public int coverHeight;

        ///////////////////////
        /* Only GalleryImage */
        ///////////////////////

        @SerializedName("animated")
        public boolean animated;

        @SerializedName("width")
        public int width;

        @SerializedName("height")
        public int height;

    }

}
