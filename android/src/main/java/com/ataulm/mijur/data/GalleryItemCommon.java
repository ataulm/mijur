package com.ataulm.mijur.data;

public class GalleryItemCommon {

    private static final GalleryItemCommon NONE = new GalleryItemCommon("", "", "", Time.invalid(), "");

    public final String id;
    public final String title;
    public final String description;
    public final Time gallerySubmissionTime;
    public final String link;

    public GalleryItemCommon(String id, String title, String description, Time gallerySubmissionTime, String link) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.gallerySubmissionTime = gallerySubmissionTime;
        this.link = link;
    }

    public static GalleryItemCommon none() {
        return NONE;
    }

}
