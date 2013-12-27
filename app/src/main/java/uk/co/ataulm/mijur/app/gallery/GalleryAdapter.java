package uk.co.ataulm.mijur.app.gallery;

import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Random;

import uk.co.ataulm.mijur.R;
import uk.co.ataulm.mijur.app.MijurListAdapter;
import uk.co.ataulm.mijur.core.model.GalleryItem;

class GalleryAdapter extends MijurListAdapter<GalleryItem> {

    private static final Random RANDOM = new Random();
    private static final SparseArray<Double> POSITION_HEIGHT_RATIOS = new SparseArray<Double>();

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
        GalleryItem item = getItem(position);
        view.setHeightRatio(calculateHeightRatio(position));

        view.updateWith(position, item, listener);
    }

    private double calculateHeightRatio(int position) {
        double ratio = POSITION_HEIGHT_RATIOS.get(position, 0.0);
        if (ratio == 0) {
            ratio = getRandomHeightRatio();
            POSITION_HEIGHT_RATIOS.append(position, ratio);
        }
        return ratio;
    }

    private double getRandomHeightRatio() {
        // HEIGHT will be 1.0 - 1.5 the WIDTH
        return (RANDOM.nextDouble() / 2.0) + 1.0;
    }

    interface GalleryItemListener {

        void onGalleryItemClicked(GalleryItem item);

    }

}
