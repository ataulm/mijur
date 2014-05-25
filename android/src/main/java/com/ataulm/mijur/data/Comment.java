package com.ataulm.mijur.data;

public class Comment {

    private final long id;
    private final String author;
    private final int upvotes;
    private final int downvotes;
    private final String text;
    private final long parentId;

    public Comment(long id, String author, int upvotes, int downvotes, String text, long parentId) {
        this.id = id;
        this.author = author;
        this.upvotes = upvotes;
        this.downvotes = downvotes;
        this.text = text;
        this.parentId = parentId;
    }

    public long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public boolean isReply() {
        return parentId != 0;
    }

    public boolean parentIdIs(long id) {
        return parentId == id;
    }

}
