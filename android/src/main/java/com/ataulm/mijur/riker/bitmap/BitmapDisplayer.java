package com.ataulm.mijur.riker.bitmap;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.ataulm.mijur.riker.Displayer;

public class BitmapDisplayer implements Displayer<Bitmap, ImageView> {

    @Override
    public void display(Bitmap content, ImageView view) {
        view.setImageBitmap(content);
    }

}
