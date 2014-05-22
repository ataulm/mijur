package com.ataulm.mijur.feed;

public class Feed {

    private static final Feed EMPTY = new Feed(Gallery.empty());

    public final Gallery gallery;

    private Feed(Gallery gallery) {
        this.gallery = gallery;
    }

    public static Feed newInstance(Gallery gallery) {
        return new Feed(gallery);
    }

    public static Feed empty() {
        return EMPTY;
    }

    public boolean isValid() {
        return this != empty();
    }

}