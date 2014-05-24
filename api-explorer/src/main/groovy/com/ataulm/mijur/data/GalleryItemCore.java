package com.ataulm.mijur.data;

public class GalleryItemCore {

    public final String id;
    public final String title;
    public final String description;
    public final Time gallerySubmissionTime;
    public final String link;

    public GalleryItemCore(String id, String title, String description, Time gallerySubmissionTime, String link) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.gallerySubmissionTime = gallerySubmissionTime;
        this.link = link;
    }

}
