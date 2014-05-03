package com.ataulm.mijur.riker;

import com.ataulm.mijur.riker.bitmap.BitmapRiker;

public class Rikers {

    private static BitmapRiker bitmapRiker;

    public static BitmapRiker bitmapRiker() {
        if (bitmapRiker == null) {
            bitmapRiker = BitmapRiker.newInstance();
        }
        return bitmapRiker;
    }

}
