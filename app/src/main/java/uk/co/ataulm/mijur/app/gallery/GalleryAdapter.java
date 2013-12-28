package uk.co.ataulm.mijur.app.gallery;

import android.content.Context;
import android.database.Cursor;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

import java.util.Random;

import org.joda.time.DateTime;

import uk.co.ataulm.mijur.R;
import uk.co.ataulm.mijur.app.Provider;
import uk.co.ataulm.mijur.core.model.GalleryItem;

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

        GalleryItem item = new GalleryItem();
        item.id = cursor.getString(cursor.getColumnIndex(Provider.GalleryItem._ID.toString()));
        item.title = cursor.getString(cursor.getColumnIndex(Provider.GalleryItem.TITLE.toString()));
        item.description = cursor.getString(cursor.getColumnIndex(Provider.GalleryItem.DESCRIPTION.toString()));
        item.datetime = new DateTime(cursor.getString(cursor.getColumnIndex(Provider.GalleryItem.SUBMISSION_DATETIME.toString()))).getMillis();
        item.lastSynced = new DateTime(cursor.getString(cursor.getColumnIndex(Provider.GalleryItem.LAST_SYNCED_DATETIME.toString())));
        item.firstSynced = new DateTime(cursor.getString(cursor.getColumnIndex(Provider.GalleryItem.FIRST_SYNCED_DATETIME.toString())));
        item.views = cursor.getInt(cursor.getColumnIndex(Provider.GalleryItem.PAGE_VIEWS.toString()));
        item.link = cursor.getString(cursor.getColumnIndex(Provider.GalleryItem.URL.toString()));
        item.account_url = cursor.getString(cursor.getColumnIndex(Provider.GalleryItem.UPLOADER.toString()));
        item.ups = cursor.getInt(cursor.getColumnIndex(Provider.GalleryItem.UPVOTES.toString()));
        item.downs = cursor.getInt(cursor.getColumnIndex(Provider.GalleryItem.DOWNVOTES.toString()));
        item.score = cursor.getInt(cursor.getColumnIndex(Provider.GalleryItem.SCORE.toString()));
        item.is_album = (cursor.getInt(cursor.getColumnIndex(Provider.GalleryItem.IS_ALBUM.toString())) == 0) ? false : true;
        item.cover = cursor.getString(cursor.getColumnIndex(Provider.GalleryItem.COVER_IMAGE_ID.toString()));;
        item.layout = cursor.getString(cursor.getColumnIndex(Provider.GalleryItem.ALBUM_LAYOUT.toString()));;
        item.images_count = cursor.getInt(cursor.getColumnIndex(Provider.GalleryItem.NUM_IMAGES.toString()));;
        item.type = cursor.getString(cursor.getColumnIndex(Provider.GalleryItem.IMAGE_MIME_TYPE.toString()));;
        item.animated = (cursor.getInt(cursor.getColumnIndex(Provider.GalleryItem.IS_ANIMATED.toString())) == 0) ? false : true;
        item.width = cursor.getInt(cursor.getColumnIndex(Provider.GalleryItem.WIDTH.toString()));;
        item.height = cursor.getInt(cursor.getColumnIndex(Provider.GalleryItem.HEIGHT.toString()));;
        item.size = cursor.getLong(cursor.getColumnIndex(Provider.GalleryItem.SIZE_BYTES.toString()));;
        item.bandwidth = cursor.getLong(cursor.getColumnIndex(Provider.GalleryItem.BANDWIDTH_BYTES.toString()));;
        item.deletehash = cursor.getString(cursor.getColumnIndex(Provider.GalleryItem.DELETE_HASH.toString()));;

        return item;
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
