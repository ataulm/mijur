package com.ataulm.mijur.feed;

public interface GalleryItem {

    String getId();

    String getTitle();

    String getDescription();

    Time getGallerySubmissionTime();

    String getImageUrl();

    int getWidth();

    int getHeight();

}
