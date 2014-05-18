package com.ataulm.mijur.base.android;

import android.app.Application;
import android.content.Context;

public class MijurApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static Context context() {
        return context;
    }

}
