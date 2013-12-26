package uk.co.ataulm.mijur.app.gallery;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
import android.widget.Toast;

import com.novoda.notils.logger.Novogger;

import java.util.List;

import uk.co.ataulm.mijur.app.MijurListAdapter;
import uk.co.ataulm.mijur.core.model.GalleryItem;

class GalleryLoaderCallbacks implements LoaderManager.LoaderCallbacks<List<GalleryItem>> {

    private Context context;
    private MijurListAdapter adapter;

    GalleryLoaderCallbacks(Context context, MijurListAdapter adapter) {
        this.context = context.getApplicationContext();
        this.adapter = adapter;
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        Novogger.d("onCreateLoader.adapter count: " + adapter.getCount());
        return new GalleryItemLoader(context);
    }

    @Override
    public void onLoadFinished(Loader<List<GalleryItem>> loader, List<GalleryItem> data) {
        adapter.swapData(data);
        Toast.makeText(context, "Loaded most viral, newest first", Toast.LENGTH_SHORT).show();
        Novogger.d("adapter count: " + adapter.getCount());
    }

    @Override
    public void onLoaderReset(Loader loader) {
    }
}
