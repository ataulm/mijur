package com.ataulm.mijur.data;

public class Comment {

    private final String id;
    private final String author;
    private final int upvotes;
    private final int downvotes;
    private final String text;
    private final String parentId;

    public Comment(String id, String author, int upvotes, int downvotes, String text, String parentId) {
        this.id = id;
        this.author = author;
        this.upvotes = upvotes;
        this.downvotes = downvotes;
        this.text = text;
        this.parentId = parentId;
    }

    public String getText() {
        return text;
    }

}
