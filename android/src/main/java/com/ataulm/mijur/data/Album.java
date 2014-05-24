package com.ataulm.mijur.data;

public class Album implements GalleryItem {

    private static final Album EMPTY = new Album(GalleryItemCore.none(), Cover.none(), Images.empty());

    private final GalleryItemCore core;
    private final Cover cover;
    private final Images images;

    private Album(GalleryItemCore core, Cover cover, Images images) {
        this.core = core;
        this.cover = cover;
        this.images = images;
    }

    public static Album newInstance(GalleryItemCore core, Cover cover) {
        return new Album(core, cover, Images.empty());
    }

    public static Album newInstance(GalleryItemCore core, Cover cover, Images images) {
        return new Album(core, cover, images);
    }

    public static Album empty() {
        return EMPTY;
    }

    public String getId() {
        return core.id;
    }

    @Override
    public String getTitle() {
        return core.title;
    }

    public String getDescription() {
        return core.description;
    }

    public int size() {
        return images.size();
    }

    public Images getImages() {
        return images;
    }

    @Override
    public boolean isAlbum() {
        return true;
    }

    @Override
    public String getThumbnailUrl() {
        int dot = cover.link.lastIndexOf(".");
        return cover.link.substring(0, dot) + "m" + cover.link.substring(dot, cover.link.length());
    }

    public static class Cover {

        private static final Cover NONE = new Cover("", 0, 0);

        private final String link;
        private final int width;
        private final int height;

        public Cover(String link, int width, int height) {
            this.link = link;
            this.width = width;
            this.height = height;
        }

        public static Cover none() {
            return NONE;
        }

    }

}
