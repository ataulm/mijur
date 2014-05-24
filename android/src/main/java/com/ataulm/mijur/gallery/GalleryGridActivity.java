package com.ataulm.mijur.gallery;

import android.os.Bundle;
import android.widget.ListView;

import com.ataulm.mijur.R;
import com.ataulm.mijur.base.android.MijurActivity;
import com.ataulm.mijur.data.Gallery;
import com.ataulm.mijur.data.GalleryItem;
import com.ataulm.mijur.data.GalleryProvider;
import com.ataulm.mijur.view.GalleryItemView;
import com.novoda.notils.caster.Views;
import com.novoda.notils.logger.simple.Log;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class GalleryGridActivity extends MijurActivity implements GalleryItemView.OnClickListener {

    private ListView list;
    private Subscription feedSubscription;
    private GalleryViewUpdater galleryViewUpdater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        final GridAdapter adapter = new GridAdapter(getLayoutInflater(), Gallery.empty(), this);
        list = Views.findById(this, R.id.gallery_list);
        list.setAdapter(adapter);

        galleryViewUpdater = new GalleryViewUpdater(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        feedSubscription = GalleryProvider.instance().getGallery()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(galleryViewUpdater);
    }

    @Override
    protected void onPause() {
        super.onPause();
        feedSubscription.unsubscribe();
    }

    @Override
    public void onClick(GalleryItem item) {
        navigate().toGalleryPostActivity(item);
    }

    private static class GalleryViewUpdater implements Observer<Gallery> {

        private final GridAdapter adapter;

        private GalleryViewUpdater(GridAdapter adapter) {
            this.adapter = adapter;
        }

        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            Log.e("GalleryUpdater", e);
        }

        @Override
        public void onNext(Gallery gallery) {
            adapter.update(gallery);
        }

    }

}
