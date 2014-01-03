package uk.co.ataulm.mijur.gallery;

import android.content.Context;
import android.database.Cursor;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

import java.util.Random;

import uk.co.ataulm.mijur.R;
import uk.co.ataulm.mijur.model.GalleryItem;

class GalleryAdapter extends CursorAdapter {

    private static final Random RANDOM = new Random();
    private static final SparseArray<Double> POSITION_HEIGHT_RATIOS = new SparseArray<Double>();

    private final GalleryItemListener listener;

    public GalleryAdapter(Context context, Cursor c, GalleryItemListener listener) {
        super(context, c, 0);
        this.listener = listener;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return createView(parent);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        updateView((GalleryItemView) view, cursor.getPosition());
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

    @Override
    public GalleryItem getItem(int position) {
        Cursor cursor = getCursor();
        return GalleryItemPersister.newGalleryItemFrom(cursor, position);
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
