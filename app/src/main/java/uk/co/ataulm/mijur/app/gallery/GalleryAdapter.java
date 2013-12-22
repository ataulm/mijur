package uk.co.ataulm.mijur.app.gallery;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uk.co.ataulm.mijur.R;
import uk.co.ataulm.mijur.app.MijurListAdapter;
import uk.co.ataulm.mijur.core.model.GalleryElement;

class GalleryAdapter extends MijurListAdapter<GalleryElement> {

    private final GalleryItemListener listener;

    GalleryAdapter(GalleryItemListener listener) {
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
        GalleryElement item = getItem(position);

        view.updateWith(position, item, listener);
    }

    interface GalleryItemListener {

        void onGalleryItemClicked(String itemId);

    }

}
