package com.ataulm.mijur.model;

import com.ataulm.mijur.imgur.response.GalleryItemResponse;

import java.util.List;

public class Gallery {

    public final List<GalleryItemResponse> elements;

    public Gallery(List<GalleryItemResponse> elements) {
        this.elements = elements;
    }

    public int size() {
        return elements.size();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(getClass().getName());
        builder.append("{");

        for (GalleryItemResponse element : elements) {
            builder.append(element.link).append(", ");
        }

        builder.delete(builder.lastIndexOf(", "), builder.length());
        builder.append("}");
        return builder.toString();
    }

    public enum Section {
        HOT, TOP, USER;

        @Override
        public String toString() {
            return super.toString().toLowerCase();
        }
    }

    public enum Sort {
        VIRAL, TIME;

        @Override
        public String toString() {
            return super.toString().toLowerCase();
        }
    }

}
