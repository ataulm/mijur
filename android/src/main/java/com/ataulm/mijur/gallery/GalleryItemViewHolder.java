package com.ataulm.mijur.gallery;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ataulm.mijur.data.GalleryItem;
import com.ataulm.mijur.view.GalleryItemView;

class GalleryItemViewHolder extends RecyclerView.ViewHolder {

    public GalleryItemViewHolder(View itemView) {
        super(itemView);
    }

    void bind(GalleryItem item) {
        ((GalleryItemView) itemView).update(item, null);
    }

}
