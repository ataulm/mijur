package uk.co.ataulm.mijur.app.gallery;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.etsy.android.grid.StaggeredGridView;
import com.novoda.notils.caster.Views;
import com.novoda.notils.logger.Novogger;

import org.joda.time.DateTime;
import org.joda.time.Instant;
import org.joda.time.Interval;

import uk.co.ataulm.mijur.R;
import uk.co.ataulm.mijur.core.model.GalleryItem;

public class GalleryActivity extends Activity implements GalleryAdapter.GalleryItemListener {

    static final String PREFS_LAST_FETCHED = "uk.co.ataulm.mijur.prefs.last_fetched";
    private static final int MINUTES_UNTIL_GALLERY_STALE = 60;
    private static final int MINIMUM_WAIT_TIL_REFRESH = 5;

    private StaggeredGridView grid;
    private GalleryLoaderCallbacks loaderCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        GalleryItemHeaderView header = (GalleryItemHeaderView) getLayoutInflater().inflate(R.layout.gallery_header_view, null);

        GalleryAdapter adapter = new GalleryAdapter(getApplicationContext(), null, this);
        loaderCallbacks = new GalleryLoaderCallbacks(this, adapter, header);

        grid = Views.findById(this, R.id.gridview);
        grid.addHeaderView(header);
        grid.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getLoaderManager().initLoader(GalleryLoaderCallbacks.HEADER_LOADER, null, loaderCallbacks);
        getLoaderManager().initLoader(GalleryLoaderCallbacks.CURSOR_LOADER, null, loaderCallbacks);

        if (galleryDataOlderThan(MINUTES_UNTIL_GALLERY_STALE)) {
            getLoaderManager().initLoader(GalleryLoaderCallbacks.API_LOADER, null, loaderCallbacks);
        }
    }

    private boolean galleryDataOlderThan(int minutes) {
        SharedPreferences prefs = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        DateTime lastFetched = new DateTime(prefs.getString(PREFS_LAST_FETCHED, new DateTime(0).toString()));
        Interval interval = new Interval(lastFetched, new DateTime(Instant.now()));

        return interval.toDuration().getStandardMinutes() >= minutes;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.gallery, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_refresh:
                if (galleryDataOlderThan(MINIMUM_WAIT_TIL_REFRESH)) {
                    getLoaderManager().restartLoader(GalleryLoaderCallbacks.API_LOADER, null, loaderCallbacks);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onGalleryItemClicked(GalleryItem item) {
        Novogger.v("Clicked:" + item.toString());
        Toast.makeText(this, "Clicked: " + item.id, Toast.LENGTH_SHORT).show();
    }

}
