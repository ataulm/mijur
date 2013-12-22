package uk.co.ataulm.mijur.app.gallery;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.Toast;

import com.novoda.notils.caster.Views;
import com.novoda.notils.logger.Novogger;

import uk.co.ataulm.imgur.R;
import uk.co.ataulm.mijur.app.MijurListAdapter;
import uk.co.ataulm.mijur.core.model.Gallery;
import uk.co.ataulm.mijur.core.model.GalleryElement;

public class GalleryActivity extends Activity implements GalleryAdapter.GalleryItemListener {

    private GridView grid;
    private MijurListAdapter<GalleryElement> adapter;
    private Gallery gallery;
    private GalleryElement element;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        adapter = new GalleryAdapter(this);
        setupGrid();

        getLoaderManager().initLoader(0, null, new GalleryLoaderCallbacks(this, adapter));
    }

    private void setupGrid() {
        grid = Views.findById(this, R.id.grid);
        grid.setAdapter(adapter);
    }

    @Override
    public void onGalleryItemClicked(String itemId) {
        Novogger.v("Clicked:" + itemId);
        Toast.makeText(this, "Clicked: " + itemId, Toast.LENGTH_SHORT).show();
    }
}
