package com.ataulm.mijur.data.parser;

import com.google.gson.annotations.SerializedName;

import java.util.List;

class GsonCommentResponse {

    @SerializedName("data")
    List<GsonComment> data;

    @SerializedName("success")
    boolean success;

    @SerializedName("status")
    int status;

}
