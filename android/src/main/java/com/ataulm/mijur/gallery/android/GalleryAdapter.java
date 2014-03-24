package com.ataulm.mijur.gallery.android;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ataulm.mijur.R;
import com.ataulm.mijur.base.android.MijurAdapter;
import com.ataulm.mijur.gallery.GalleryItem;

class GalleryAdapter extends MijurAdapter<GalleryItem> {

    @Override
    public int getViewTypeCount() {
        return GalleryItemViewTypeMapper.LAYOUTS.length;
    }

    @Override
    public int getItemViewType(int position) {
        return GalleryItemViewTypeMapper.viewTypeFrom(position);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            view = createView(LayoutInflater.from(parent.getContext()), position);
        }
        ((GalleryItemView) view).updateWith(getItem(position));
        return view;
    }

    private View createView(LayoutInflater inflater, int position) {
        int layout = GalleryItemViewTypeMapper.layoutResIdFrom(position);
        return inflater.inflate(layout, null, false);
    }

    private static class GalleryItemViewTypeMapper {

        private static final int[] LAYOUTS = {
                R.layout.gallery_item_view_3,
                R.layout.gallery_item_view_2,
                R.layout.gallery_item_view_1
        };

        static int layoutResIdFrom(int position) {
            return LAYOUTS[viewTypeFrom(position)];
        }

        static int viewTypeFrom(int position) {
            return position % LAYOUTS.length;
        }

    }

}
