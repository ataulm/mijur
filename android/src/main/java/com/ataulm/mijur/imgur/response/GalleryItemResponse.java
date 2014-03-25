package com.ataulm.mijur.imgur.response;

import com.ataulm.mijur.model.Image;

import java.util.List;

public class GalleryItemResponse {

    // both gallery album and gallery image
    public String id;
    public String title;
    public String description;
    public long datetime;
    public int views;
    public String link;
    public String vote;
    public String account_url;
    public int ups;
    public int downs;
    public int score;
    public boolean is_album;

    // gallery album
    public String cover;
    public String privacy;
    public String layout;
    public int images_count;
    public List<Image> images;

    // gallery image
    public String type;
    public boolean animated;
    public int width;
    public int height;
    public long size;
    public long bandwidth;
    public String deletehash;
    public String section;

    public long firstSynced;
    public long lastSynced;

}
