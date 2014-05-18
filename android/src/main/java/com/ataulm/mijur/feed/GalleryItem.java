package com.ataulm.mijur.feed;

public interface GalleryItem {

    String getId();

    String getTitle();

    String getDescription();

    Time getGallerySubmissionTime();

    String getThumbnailUrl();

    int getWidth();

    int getHeight();

}
