package com.ataulm.mijur.feed;

import java.util.List;

public class Feed {

    private static class FeedGalleryResponse {

        private final List<GalleryItemCore> data;

        private FeedGalleryResponse(List<GalleryItemCore> data) {
            this.data = data;
        }

    }

}