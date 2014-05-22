package com.ataulm.mijur.base;

import android.app.Activity;
import android.content.Intent;

import com.ataulm.mijur.feed.GalleryItem;
import com.ataulm.mijur.gallery.GalleryPostActivity;

public class Navigator {

    private final Activity sourceActivity;

    private Navigator(Activity sourceActivity) {
        this.sourceActivity = sourceActivity;
    }

    public static Navigator newInstance(Activity sourceActivity) {
        return new Navigator(sourceActivity);
    }

    public void toGalleryPostActivity(GalleryItem item) {
        Intent intent = new Intent(sourceActivity, GalleryPostActivity.class);
        intent.putExtra(GalleryPostActivity.EXTRA_GALLERY_ITEM_ID, item.getId());
        sourceActivity.startActivity(intent);
    }

}
