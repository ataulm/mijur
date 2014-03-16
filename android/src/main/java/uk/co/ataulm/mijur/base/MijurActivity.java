package uk.co.ataulm.mijur.base;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import uk.co.ataulm.mijur.base.viewserver.ViewServerManager;

public abstract class MijurActivity extends Activity {

    private ViewServerManager viewServerManager;
    private MijurNavUtils navigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewServerManager = new ViewServerManager(this);
        viewServerManager.onCreate();
        navigator = new MijurNavUtils(this);
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

    protected MijurNavUtils navigate() {
        return navigator;
    }

    protected void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    protected void toast(int messageResId) {
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }

}
