package com.ataulm.mijur.dory;

import com.ataulm.mijur.dory.bitmap.BitmapDory;

public class Dorys {

    private static BitmapDory bitmapDory;

    public static BitmapDory bitmapDory() {
        if (bitmapDory == null) {
            bitmapDory = BitmapDory.newInstance();
        }
        return bitmapDory;
    }

}
