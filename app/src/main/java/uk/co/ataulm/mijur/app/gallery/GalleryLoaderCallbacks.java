package uk.co.ataulm.mijur.app.gallery;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import com.novoda.notils.logger.Novogger;

import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Instant;

import uk.co.ataulm.mijur.core.model.GalleryItem;

class GalleryLoaderCallbacks implements LoaderManager.LoaderCallbacks {

    static final int CURSOR_LOADER = 0;
    static final int API_LOADER = 1;

    private Context context;
    private GalleryAdapter adapter;

    GalleryLoaderCallbacks(Context context, GalleryAdapter adapter) {
        this.context = context.getApplicationContext();
        this.adapter = adapter;
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        if (id == CURSOR_LOADER) {
            return new GalleryItemCursorLoader(context);
        } else if (id == API_LOADER) {
            return new GalleryItemApiLoader(context);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {
        if (loader.getId() == CURSOR_LOADER) {
            onCursorLoadFinished(loader, (Cursor) data);
        } else if (loader.getId() == API_LOADER) {
            onApiLoadFinished(loader, (List<GalleryItem>) data);
        }
    }

    private void onApiLoadFinished(Loader loader, List<GalleryItem> galleryItems) {
        GalleryItemPersister.persist(context.getContentResolver(), galleryItems);
        SharedPreferences prefs = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        prefs.edit().putString(GalleryActivity.PREFS_LAST_FETCHED, new DateTime(Instant.now()).toString()).apply();
        Toast.makeText(context, "Gallery refreshed from Imgur!", Toast.LENGTH_SHORT).show();
    }

    private void onCursorLoadFinished(Loader loader, Cursor cursor) {
        adapter.changeCursor(cursor);
        Toast.makeText(context, "Gallery loaded!", Toast.LENGTH_SHORT).show();
        Novogger.d("adapter count: " + adapter.getCount());
    }

    @Override
    public void onLoaderReset(Loader loader) {
    }

}
