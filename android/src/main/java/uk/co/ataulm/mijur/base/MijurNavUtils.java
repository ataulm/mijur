package uk.co.ataulm.mijur.base;

import android.app.Activity;
import android.content.Intent;

import uk.co.ataulm.mijur.post.PostActivity;

public class MijurNavUtils {

    private final Activity activity;

    public MijurNavUtils(Activity activity) {
        this.activity = activity;
    }

    public void toPost(int postPosition) {
        Intent intent = new Intent(activity, PostActivity.class);
        intent.putExtra(PostActivity.EXTRA_POST_POSITION, postPosition);
        activity.startActivity(intent);
    }

}
