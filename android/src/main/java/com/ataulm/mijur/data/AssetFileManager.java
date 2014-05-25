package com.ataulm.mijur.data;

import android.content.res.AssetManager;

import com.ataulm.mijur.base.DeveloperError;
import com.ataulm.mijur.base.android.MijurApplication;

import java.io.IOException;
import java.io.InputStream;

class AssetFileManager {

    private final AssetManager assetManager;

    AssetFileManager(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    public static AssetFileManager newInstance() {
        return new AssetFileManager(MijurApplication.context().getAssets());
    }

    public InputStream open(String file) {
        try {
            return assetManager.open(file);
        } catch (IOException e) {
            throw DeveloperError.because("Failed to open file from Android Assets", e);
        }
    }

}
