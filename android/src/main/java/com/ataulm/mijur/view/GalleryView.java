package com.ataulm.mijur.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.ataulm.mijur.feed.Gallery;
import com.ataulm.mijur.feed.GalleryItem;

public class GalleryView extends ListView {

    public GalleryView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GalleryView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

}
