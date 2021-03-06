package com.ataulm.mijur.data.parser;

import com.google.gson.annotations.SerializedName;

class GsonGalleryItem {

    @SerializedName("id")
    String id;

    @SerializedName("title")
    String title;

    @SerializedName("description")
    String description;

    @SerializedName("datetime")
    long datetime;

    @SerializedName("link")
    String link;

    @SerializedName("is_album")
    boolean isAlbum;

    ///////////////////////
    /* Only Album */
    ///////////////////////

    @SerializedName("cover")
    String cover;

    @SerializedName("cover_width")
    int coverWidth;

    @SerializedName("cover_height")
    int coverHeight;

    ///////////////////////
    /* Only Image */
    ///////////////////////

    @SerializedName("animated")
    boolean animated;

    @SerializedName("width")
    int width;

    @SerializedName("height")
    int height;

}
