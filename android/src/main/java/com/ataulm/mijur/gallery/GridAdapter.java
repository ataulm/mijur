package com.ataulm.mijur.gallery;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.ataulm.mijur.R;
import com.ataulm.mijur.feed.Gallery;
import com.ataulm.mijur.feed.GalleryItem;
import com.ataulm.mijur.view.GalleryItemView;

class GridAdapter extends BaseAdapter {

    private final LayoutInflater layoutInflater;

    private Gallery gallery;
    private final GalleryItemView.OnClickListener listener;

    public GridAdapter(LayoutInflater layoutInflater, Gallery gallery, GalleryItemView.OnClickListener listener) {
        this.layoutInflater = layoutInflater;
        this.gallery = gallery;
        this.listener = listener;
    }

    public void update(Gallery gallery) {
        this.gallery = gallery;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return gallery.size();
    }

    @Override
    public GalleryItem getItem(int position) {
        return gallery.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId().hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = createView(parent);
        }
        ((GalleryItemView) view).update(getItem(position), listener);

        if (position % 2 == 0) {
            view.setBackgroundColor(Color.GREEN);
        } else {
            view.setBackgroundColor(Color.BLUE);
        }
        return view;
    }

    private View createView(ViewGroup parent) {
        return layoutInflater.inflate(R.layout.view_gallery_item, parent, false);
    }

}
