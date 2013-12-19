package uk.co.ataulm.mijur.app;

import android.app.Activity;
import android.os.Bundle;

import com.etsy.android.grid.StaggeredGridView;
import com.novoda.notils.caster.Views;

import uk.co.ataulm.imgur.R;

public class Gallery extends Activity {

    private StaggeredGridView grid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        grid = Views.findById(this, R.id.grid);
        setupGrid();
    }

    private void setupGrid() {

    }

}
