package com.ataulm.mijur.gallery;

import android.os.Bundle;

import com.ataulm.mijur.BuildConfig;
import com.ataulm.mijur.R;
import com.ataulm.mijur.base.android.MijurActivity;
import com.ataulm.mijur.data.GalleryItem;
import com.ataulm.mijur.data.GalleryProvider;
import com.ataulm.mijur.view.PostView;
import com.novoda.notils.caster.Views;
import com.novoda.notils.logger.simple.Log;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ImageActivity extends MijurActivity {

    public static final String EXTRA_POST_ID = BuildConfig.PACKAGE_NAME + ".EXTRA_POST_ID";

    private Subscription gallerySubscription;
    private PostContentUpdater postContentUpdater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        PostView post = Views.findById(this, R.id.post);
        postContentUpdater = new PostContentUpdater(post);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String postId = getIntent().getStringExtra(EXTRA_POST_ID);

        gallerySubscription = GalleryProvider.instance().getGalleryItem(postId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(postContentUpdater);
    }

    @Override
    protected void onPause() {
        super.onPause();
        gallerySubscription.unsubscribe();
    }

    private static class PostContentUpdater implements Observer<GalleryItem> {

        private final PostView view;

        private PostContentUpdater(PostView view) {
            this.view = view;
        }

        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            Log.e("PostContentUpdater", e);
        }

        @Override
        public void onNext(GalleryItem galleryItem) {
            view.update(galleryItem);
        }

    }

}
