package uk.co.ataulm.imgur.app;

import android.app.Activity;
import android.os.Bundle;

import com.etsy.android.grid.StaggeredGridView;

import uk.co.ataulm.imgur.R;

public class Gallery extends Activity {

    private StaggeredGridView grid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
    }

}
