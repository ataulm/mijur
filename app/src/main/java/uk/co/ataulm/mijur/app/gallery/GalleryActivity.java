package uk.co.ataulm.mijur.app.gallery;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.etsy.android.grid.StaggeredGridView;
import com.novoda.notils.caster.Views;
import com.novoda.notils.logger.Novogger;

import uk.co.ataulm.mijur.R;
import uk.co.ataulm.mijur.app.MijurListAdapter;
import uk.co.ataulm.mijur.core.model.GalleryItem;

public class GalleryActivity extends Activity implements GalleryAdapter.GalleryItemListener {

    private StaggeredGridView grid;
    private MijurListAdapter<GalleryItem> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        adapter = new GalleryAdapter(this);
        setupGrid();

        getLoaderManager().initLoader(0, null, new GalleryLoaderCallbacks(this, adapter));
    }

    private void setupGrid() {
        grid = Views.findById(this, R.id.gridview);
        grid.setAdapter(adapter);
    }

    @Override
    public void onGalleryItemClicked(GalleryItem item) {
        Novogger.v("Clicked:" + item.toString());
        Toast.makeText(this, "Clicked: " + item.id, Toast.LENGTH_SHORT).show();
    }
}
