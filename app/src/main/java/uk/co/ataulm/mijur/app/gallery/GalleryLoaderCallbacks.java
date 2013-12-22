package uk.co.ataulm.mijur.app.gallery;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;

import java.util.List;

import uk.co.ataulm.mijur.app.MijurListAdapter;
import uk.co.ataulm.mijur.core.model.GalleryElement;

class GalleryLoaderCallbacks implements LoaderManager.LoaderCallbacks<List<GalleryElement>> {

    private Context context;
    private MijurListAdapter adapter;

    GalleryLoaderCallbacks(Context context, MijurListAdapter adapter) {
        this.context = context.getApplicationContext();
        this.adapter = adapter;
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return new GalleryElementLoader(context);
    }

    @Override
    public void onLoadFinished(Loader<List<GalleryElement>> loader, List<GalleryElement> data) {
        adapter.swapData(data);
    }

    @Override
    public void onLoaderReset(Loader loader) {
    }
}
