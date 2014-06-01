package com.ataulm.mijur.data;

public class Comment {

    private final long id;
    private final String author;
    private final int upvotes;
    private final int downvotes;
    private final String text;
    private final long parentId;
    private final Time time;

    public Comment(long id, String author, int upvotes, int downvotes, String text, long parentId, Time time) {
        this.id = id;
        this.author = author;
        this.upvotes = upvotes;
        this.downvotes = downvotes;
        this.text = text;
        this.parentId = parentId;
        this.time = time;
    }

    public long getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getText() {
        return text;
    }

    public String getScore() {
        return String.valueOf(upvotes - downvotes);
    }

    public Time getTime() {
        return time;
    }

    public boolean isReply() {
        return parentId != 0;
    }

    public boolean parentIdMatches(long id) {
        return parentId == id;
    }

}
