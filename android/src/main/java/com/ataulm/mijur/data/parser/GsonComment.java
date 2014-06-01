package com.ataulm.mijur.data.parser;

import com.google.gson.annotations.SerializedName;

import java.util.List;

class GsonComment {

    @SerializedName("id")
    long id;

    @SerializedName("author")
    String author;

    @SerializedName("ups")
    int upvotes;

    @SerializedName("downs")
    int downvotes;

    @SerializedName("comment")
    String text;

    @SerializedName("parent_id")
    long parentId;

    @SerializedName("datetime")
    long datetime;

    @SerializedName("children")
    List<GsonComment> children;


}
