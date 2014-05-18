package com.ataulm.mijur.feed;

public class Feed {

    private final Gallery gallery;

    private Feed(Gallery gallery) {
        this.gallery = gallery;
    }

    public static Feed newInstance(Gallery gallery) {
        return new Feed(gallery);
    }

    public static Feed empty() {
        return new Feed(Gallery.empty());
    }

}