package com.ataulm.mijur.gallery.android;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ataulm.mijur.R;
import com.ataulm.mijur.base.android.MijurAdapter;
import com.ataulm.mijur.gallery.GalleryItem;

class GalleryAdapter extends MijurAdapter<GalleryItem> {

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            view = createView(LayoutInflater.from(parent.getContext()), position);
        }
        ((GalleryItemView) view).updateWith(getItem(position));
        return view;
    }

    private View createView(LayoutInflater inflater, int position) {
        if (position % 2 == 0) {
            return inflater.inflate(R.layout.gallery_item_view_1, null, false);
        }
        return inflater.inflate(R.layout.gallery_item_view_2, null, false);
    }

}
