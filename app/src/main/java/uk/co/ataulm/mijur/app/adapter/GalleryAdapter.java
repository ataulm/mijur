package uk.co.ataulm.mijur.app.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;

public class GalleryAdapter extends SimpleCursorAdapter {

    private static final int FLAGS_NOT_USED = 0;

    public GalleryAdapter(Context context, int layout, Cursor c, String[] from, int[] to) {
        super(context, layout, c, from, to, FLAGS_NOT_USED);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = createView(parent);
        }

        updateView(convertView, position);

        return convertView;
    }

    private View createView(ViewGroup parent) {
        // TODO: create a new View
        return null;
    }

    private void updateView(View convertView, int position) {
        // TODO: update the view's contents, based on its position
    }

}
