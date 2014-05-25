package com.ataulm.mijur.data.parser;

import com.google.gson.annotations.SerializedName;

import java.util.List;

class GsonComment {

    @SerializedName("id")
    String id;

    @SerializedName("author")
    String author;

    @SerializedName("ups")
    int upvotes;

    @SerializedName("downs")
    int downvotes;

    @SerializedName("comment")
    int text;

    @SerializedName("parent_id")
    int parentId;

    @SerializedName("children")
    List<GsonComment> children;

}
