package uk.co.ataulm.mijur.app.gallery;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.etsy.android.grid.StaggeredGridView;
import com.novoda.notils.caster.Views;
import com.novoda.notils.logger.Novogger;

import uk.co.ataulm.mijur.R;
import uk.co.ataulm.mijur.core.model.GalleryItem;

public class GalleryActivity extends Activity implements GalleryAdapter.GalleryItemListener {

    private StaggeredGridView grid;
    private GalleryAdapter adapter;
    private GalleryLoaderCallbacks loaderCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        adapter = new GalleryAdapter(getApplicationContext(), null, this);
        loaderCallbacks = new GalleryLoaderCallbacks(this, adapter);
        setupGrid();

        getLoaderManager().initLoader(0, null, loaderCallbacks);
    }

    private void setupGrid() {
        grid = Views.findById(this, R.id.gridview);
        grid.setAdapter(adapter);
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
                getLoaderManager().restartLoader(0, null, loaderCallbacks);
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
