package uk.co.ataulm.mijur.post;

import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.novoda.notils.caster.Views;
import com.novoda.notils.cursor.SimpleCursorList;

import java.util.List;

import uk.co.ataulm.mijur.R;
import uk.co.ataulm.mijur.base.DeveloperError;
import uk.co.ataulm.mijur.base.MijurActivity;
import uk.co.ataulm.mijur.gallery.GalleryItemCursorLoader;
import uk.co.ataulm.mijur.gallery.GalleryItemCursorMarshaller;
import uk.co.ataulm.mijur.model.GalleryItem;

public class PostActivity extends MijurActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private ViewPager posts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        posts = Views.findById(this, R.id.posts);

        if (savedInstanceState == null) {
            getLoaderManager().initLoader(R.id.loader_post_cursor, null, this);
        } else {
            getLoaderManager().restartLoader(R.id.loader_post_cursor, null, this);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (id == R.id.loader_post_cursor) {
            return new PostsCursorLoader(this);
        }
        throw new DeveloperError("Unknown loader initialised.");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        data.moveToFirst();
        List<GalleryItem> galleryItems = new SimpleCursorList<GalleryItem>(data, new GalleryItemCursorMarshaller());

        PostPagerAdapter adapter = new PostPagerAdapter(getFragmentManager(), galleryItems);
        posts.setAdapter(adapter);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }

}
