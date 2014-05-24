package com.ataulm.mijur.data.parser;

import com.google.gson.annotations.SerializedName;

class GsonImage {

    @SerializedName("id")
    String id;

    @SerializedName("title")
    String title;

    @SerializedName("description")
    String description;

    @SerializedName("link")
    String link;

    @SerializedName("animated")
    boolean animated;

    @SerializedName("width")
    int width;

    @SerializedName("height")
    int height;

    @SerializedName("datetime")
    int datetime;

}
