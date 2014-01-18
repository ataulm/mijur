package uk.co.ataulm.mijur.gallery;

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

import uk.co.ataulm.mijur.R;
import uk.co.ataulm.mijur.model.GalleryItem;

class GalleryLoaderCallbacks implements LoaderManager.LoaderCallbacks {

    private final GalleryItemHeaderView header;
    private final Context context;
    private final GalleryAdapter adapter;

    GalleryLoaderCallbacks(Context context, GalleryAdapter adapter, GalleryItemHeaderView header) {
        this.context = context.getApplicationContext();
        this.adapter = adapter;
        this.header = header;
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        if (id == R.id.loader_gallery_cursor) {
            return new GalleryItemCursorLoader(context);
        } else if (id == R.id.loader_gallery_api) {
            return new GalleryItemApiLoader(context);
        } else if (id == R.id.loader_gallery_header_view) {
            return new GalleryItemHeaderLoader(context);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {
        if (loader.getId() == R.id.loader_gallery_cursor) {
            onCursorLoadFinished(loader, (Cursor) data);
        } else if (loader.getId() == R.id.loader_gallery_api) {
            onApiLoadFinished(loader, (List<GalleryItem>) data);
        } else if (loader.getId() == R.id.loader_gallery_header_view) {
            onHeaderLoadFinished(loader, (Cursor) data);
        }
    }

    @Override
    public void onLoaderReset(Loader loader) {
    }

    private void onHeaderLoadFinished(Loader loader, Cursor cursor) {
        header.updateWith(GalleryItemPersister.newGalleryItemFrom(cursor, 0));
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

}
