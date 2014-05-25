package com.ataulm.mijur.data;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Comments implements Iterable<Comment> {

    private final List<Comment> comments;

    public Comments(List<Comment> comments) {
        this.comments = Collections.unmodifiableList(comments);
    }

    public static Comments newInstance(List<Comment> comments) {
        return new Comments(comments);
    }

    public static Comments empty() {
        return new Comments(Collections.<Comment>emptyList());
    }

    @Override
    public Iterator<Comment> iterator() {
        return comments.iterator();
    }

    public int size() {
        return comments.size();
    }

    public Comment get(int position) {
        return comments.get(position);
    }

}
