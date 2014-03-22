package com.ataulm.mijur.gallery.android;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ataulm.mijur.base.android.MijurAdapter;
import com.ataulm.mijur.gallery.GalleryItem;

import com.ataulm.mijur.R;

class GalleryAdapter extends MijurAdapter<GalleryItem> {

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            view = createView(LayoutInflater.from(parent.getContext()));
        }
        ((GalleryItemView) view).updateWith(getItem(position));
        return view;
    }

    private View createView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.gallery_item_view, null, false);
    }

}
