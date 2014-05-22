package com.ataulm.mijur.base.android;

import android.app.Activity;
import android.os.Bundle;

import com.ataulm.mijur.base.Navigator;
import com.novoda.notils.logger.simple.Log;
import com.novoda.notils.viewserver.ViewServerManager;

import com.ataulm.mijur.BuildConfig;

public abstract class MijurActivity extends Activity {

    private Navigator navigator;
    private ViewServerManager viewServerManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.SHOW_LOGS = BuildConfig.DEBUG;
        navigator = Navigator.newInstance(this);
        initViewServerManager();
        viewServerManager.onCreate();
    }

    private void initViewServerManager() {
        viewServerManager = new ViewServerManager(this);

        if (BuildConfig.DEBUG) {
            viewServerManager.enable(true);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewServerManager.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewServerManager.onDestroy();
    }

    protected Navigator navigate() {
        return navigator;
    }

}
