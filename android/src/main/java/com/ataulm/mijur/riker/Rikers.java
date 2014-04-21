package com.ataulm.mijur.riker;

import android.widget.ImageView;

import com.ataulm.mijur.riker.bitmap.BitmapRiker;

public class Rikers {

    private static BitmapRiker bitmapRiker;

    public static BitmapRiker bitmapRiker() {
        if (bitmapRiker == null) {
            bitmapRiker = BitmapRiker.newInstance();
        }
        return bitmapRiker;
    }

    // TODO: add a GifRiker

    public static void main(String... args)  {
        String url = "http://imgur.com/cool_image.jpg";
        ImageView target = new ImageView(null);

        Rikers.bitmapRiker().display(url, target);
    }

}
