package uk.co.ataulm.mijur.app;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.Toast;

import com.etsy.android.grid.StaggeredGridView;
import com.novoda.notils.caster.Views;
import com.novoda.notils.logger.Novogger;

import uk.co.ataulm.imgur.R;
import uk.co.ataulm.mijur.app.adapter.GalleryAdapter;

public class Gallery extends Activity implements GalleryAdapter.GalleryItemListener {

    private StaggeredGridView grid;
    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        adapter = new GalleryAdapter(this);
        grid = Views.findById(this, R.id.grid);
        setupGrid();
    }

    private void setupGrid() {

    }

    @Override
    public void onGalleryItemClicked(String itemId) {
        Novogger.v("Clicked:" + itemId);
        Toast.makeText(this, "Clicked: " + itemId, Toast.LENGTH_SHORT).show();
    }
}
