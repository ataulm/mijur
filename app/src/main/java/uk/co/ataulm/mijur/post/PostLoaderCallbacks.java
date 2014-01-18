package uk.co.ataulm.mijur.post;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;

import uk.co.ataulm.mijur.R;
import uk.co.ataulm.mijur.base.DeveloperError;
import uk.co.ataulm.mijur.gallery.GalleryItemPersister;
import uk.co.ataulm.mijur.model.GalleryItem;

class PostLoaderCallbacks implements LoaderManager.LoaderCallbacks<Cursor> {

    private final Context context;
    private final String postId;
    private final GalleryItemLoadedCallback callback;

    PostLoaderCallbacks(Context context, String postId, GalleryItemLoadedCallback callback) {
        this.context = context.getApplicationContext();
        this.postId = postId;
        this.callback = callback;
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        if (id == R.id.loader_post_cursor) {
            return new PostCursorLoader(context, postId);
        } else {
            throw new DeveloperError("Unexpected loader created with id: " + id);
        }
    }

    @Override
    public void onLoadFinished(Loader loader, Cursor cursor) {
        if (loader.getId() == R.id.loader_post_cursor) {
            GalleryItem galleryItem = GalleryItemPersister.newGalleryItemFrom(cursor, 0);
            callback.onGalleryItemLoaded(galleryItem);
        }
    }

    @Override
    public void onLoaderReset(Loader loader) {
    }

}
