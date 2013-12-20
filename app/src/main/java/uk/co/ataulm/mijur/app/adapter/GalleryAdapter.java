package uk.co.ataulm.mijur.app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uk.co.ataulm.imgur.R;

public class GalleryAdapter extends MijurListAdapter<GalleryItem> {

    private final GalleryItemListener listener;

    public GalleryAdapter(GalleryItemListener listener) {
        this.listener = listener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = createView(parent);
        }

        updateView((GalleryItemView) convertView, position);

        return convertView;
    }

    private View createView(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        return inflater.inflate(R.layout.gallery_item_view, null, false);
    }

    private void updateView(GalleryItemView view, int position) {
        GalleryItem item = getItem(position);

        view.updateWith(position, item, listener);
    }

    public interface GalleryItemListener {

        void onGalleryItemClicked(String itemId);

    }

}
