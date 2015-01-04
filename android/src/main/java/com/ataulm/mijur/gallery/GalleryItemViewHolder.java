package com.ataulm.mijur.gallery;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ataulm.mijur.data.GalleryItem;
import com.ataulm.mijur.view.GalleryItemView;

class GalleryItemViewHolder extends RecyclerView.ViewHolder {

    private final GalleryAdapter.OnGalleryItemClickListener onClickListener;

    public GalleryItemViewHolder(View itemView, GalleryAdapter.OnGalleryItemClickListener onClickListener) {
        super(itemView);
        this.onClickListener = onClickListener;
    }

    void bind(final GalleryItem item) {
        ((GalleryItemView) itemView).update(item);
        itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onClickListener.onClick(item);
            }

        });
    }

}
