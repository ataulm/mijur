package com.ataulm.mijur.dory;

import android.graphics.Bitmap;
import android.widget.ImageView;

public class BitmapDisplayer implements Displayer<Bitmap, ImageView> {

    @Override
    public void display(Bitmap content, ImageView view) {
        view.setImageBitmap(content);
    }

}
