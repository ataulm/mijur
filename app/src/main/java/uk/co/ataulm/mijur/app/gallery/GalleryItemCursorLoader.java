package uk.co.ataulm.mijur.app.gallery;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.joda.time.DateTime;
import org.joda.time.Instant;
import org.joda.time.Interval;

import uk.co.ataulm.mijur.app.Provider;
import uk.co.ataulm.mijur.core.api.Imgur;
import uk.co.ataulm.mijur.core.model.Gallery;

class GalleryItemCursorLoader extends CursorLoader {

    private static final int MINUTES_UNTIL_STALE = 60;
    private int page;
    private static final String FALSE = "0";

    public GalleryItemCursorLoader(Context context) {
        super(context, Provider.Uris.GALLERY_ITEM.uri(), null,
                Provider.GalleryItem.IS_ANIMATED + " = ? AND " + Provider.GalleryItem.IS_ALBUM + " = ?",
                new String[] {FALSE, FALSE},
                Provider.GalleryItem.SUBMISSION_DATETIME.toString());
        page = 0;
    }

    @Override
    public Cursor loadInBackground() {
        Cursor cursor = super.loadInBackground();
        if (isUnusable(cursor) || isStale(cursor)) {
            updateWithFreshData();
            return super.loadInBackground();
        }
        return cursor;
    }

    private void updateWithFreshData() {
        if (networkAvailable()) {
            Gallery gallery = Imgur.getGalleryWith(Gallery.Section.HOT, Gallery.Sort.TIME, page);
            GalleryItemPersister.persist(getContext().getContentResolver(), gallery.elements);
        }
    }

    private boolean networkAvailable() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();
    }


    private boolean isStale(Cursor cursor) {
        String lastSync = cursor.getString(cursor.getColumnIndex(Provider.GalleryItem.LAST_SYNCED_DATETIME.toString()));
        DateTime lastSyncedTime = new DateTime(lastSync);
        Interval interval = new Interval(lastSyncedTime, Instant.now());
        if (interval.toDuration().getStandardMinutes() >= MINUTES_UNTIL_STALE) {
            return true;
        }
        return false;
    }

    private boolean isUsable(Cursor cursor) {
        if (cursor == null || cursor.getCount() == 0) {
            return false;
        }
        return true;
    }

    private boolean isUnusable(Cursor cursor) {
        return !isUsable(cursor);
    }

}
