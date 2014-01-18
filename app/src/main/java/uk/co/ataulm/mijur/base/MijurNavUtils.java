package uk.co.ataulm.mijur.base;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import uk.co.ataulm.mijur.post.PostActivity;

public class MijurNavUtils {

    private final Activity activity;

    public MijurNavUtils(Activity activity) {
        this.activity = activity;
    }

    public void toPost(Uri uri) {
        Intent intent = new Intent(activity, PostActivity.class);
        intent.setData(uri);
        activity.startActivity(intent);
    }

}
