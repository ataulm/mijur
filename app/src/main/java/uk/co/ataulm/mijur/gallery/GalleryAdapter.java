package uk.co.ataulm.mijur.gallery;

import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Random;

import uk.co.ataulm.mijur.R;
import uk.co.ataulm.mijur.base.MijurAdapter;
import uk.co.ataulm.mijur.model.GalleryItem;

class GalleryAdapter extends MijurAdapter<GalleryItem> {

    private static final Random RANDOM = new Random();
    private static final SparseArray<Double> POSITION_HEIGHT_RATIOS = new SparseArray<Double>();

    private final GalleryItemListener listener;

    public GalleryAdapter() {
        super();
        this.listener = new DummyGalleryItemListener();
    }

    public GalleryAdapter(GalleryItemListener listener) {
        super();
        this.listener = listener;
    }

    @Override
    public long getItemId(int position) {
        return position + 1;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            view = createView(parent);
        }
        updateView((GalleryItemView) view, position);
        return view;
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

    static class DummyGalleryItemListener implements GalleryItemListener {

        @Override
        public void onGalleryItemClicked(GalleryItem item) {
        }

    }

}
