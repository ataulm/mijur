package uk.co.ataulm.mijur.app.gallery;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import com.novoda.notils.logger.Novogger;

class GalleryLoaderCallbacks implements LoaderManager.LoaderCallbacks<Cursor> {

    private Context context;
    private GalleryAdapter adapter;

    GalleryLoaderCallbacks(Context context, GalleryAdapter adapter) {
        this.context = context.getApplicationContext();
        this.adapter = adapter;
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        Novogger.d("onCreateLoader.adapter count: " + adapter.getCount());
        return new GalleryItemCursorLoader(context);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.changeCursor(data);
        Toast.makeText(context, "Loaded most viral, newest first", Toast.LENGTH_SHORT).show();
        Novogger.d("adapter count: " + adapter.getCount());
    }

    @Override
    public void onLoaderReset(Loader loader) {
    }
}
