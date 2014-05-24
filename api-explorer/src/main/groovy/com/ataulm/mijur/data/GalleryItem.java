package com.ataulm.mijur.data;

public interface GalleryItem {

    String getId();

    String getTitle();

    String getDescription();

    Time getGallerySubmissionTime();

    String getThumbnailUrl();

    int getWidth();

    int getHeight();

}
