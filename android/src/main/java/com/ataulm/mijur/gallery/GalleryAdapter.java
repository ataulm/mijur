package com.ataulm.mijur.gallery;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ataulm.mijur.R;
import com.ataulm.mijur.data.Gallery;
import com.ataulm.mijur.data.GalleryItem;

class GalleryAdapter extends RecyclerView.Adapter<GalleryItemViewHolder> {

    private final LayoutInflater layoutInflater;
    private final OnGalleryItemClickListener onClickListener;

    private Gallery gallery;

    GalleryAdapter(LayoutInflater layoutInflater, OnGalleryItemClickListener onClickListener) {
        this.layoutInflater = layoutInflater;
        this.onClickListener = onClickListener;

        this.gallery = Gallery.empty();
    }

    @Override
    public GalleryItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.view_gallery_item, parent, false);
        return new GalleryItemViewHolder(view, onClickListener);
    }

    @Override
    public void onBindViewHolder(GalleryItemViewHolder holder, int position) {
        GalleryItem galleryItem = gallery.get(position);
        holder.bind(galleryItem);
    }

    @Override
    public int getItemCount() {
        return gallery.size();
    }

    void update(Gallery gallery) {
        this.gallery = gallery;
        notifyDataSetChanged();
    }

    public interface OnGalleryItemClickListener {

        void onClick(GalleryItem item);

    }

}
