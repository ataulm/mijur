package com.ataulm.mijur.gallery;

import android.os.Bundle;

import com.ataulm.mijur.BuildConfig;
import com.ataulm.mijur.R;
import com.ataulm.mijur.base.android.MijurActivity;
import com.ataulm.mijur.data.*;
import com.ataulm.mijur.view.GalleryPostView;
import com.novoda.notils.caster.Views;
import com.novoda.notils.logger.simple.Log;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class GalleryPostActivity extends MijurActivity {

    public static final String EXTRA_GALLERY_ITEM_ID = BuildConfig.PACKAGE_NAME + ".EXTRA_GALLERY_ITEM_ID";

    private Subscription feedSubscription;
    private GalleryPostUpdater galleryPostUpdater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_post);

        GalleryPostView post = Views.findById(this, R.id.gallery_post);
        galleryPostUpdater = new GalleryPostUpdater(post);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String galleryItemId = getExtraGalleryItemId();
        feedSubscription = GalleryProvider.instance().getGalleryItem(galleryItemId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(galleryPostUpdater);
    }

    private String getExtraGalleryItemId() {
        return getIntent().getStringExtra(EXTRA_GALLERY_ITEM_ID);
    }

    @Override
    protected void onPause() {
        super.onPause();
        feedSubscription.unsubscribe();
    }

    private static class GalleryPostUpdater implements Observer<GalleryItem> {

        private final GalleryPostView view;

        private GalleryPostUpdater(GalleryPostView view) {
            this.view = view;
        }

        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            Log.e("GalleryPostUpdater", e);
        }

        @Override
        public void onNext(GalleryItem galleryItem) {
            view.update(galleryItem);
        }

    }

}
