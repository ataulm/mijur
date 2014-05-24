package com.ataulm.mijur.data.parser;

import com.google.gson.annotations.SerializedName;

class GsonAlbumResponse {

    @SerializedName("data")
    GsonAlbum data;

    @SerializedName("success")
    boolean success;

    @SerializedName("status")
    int status;

}
