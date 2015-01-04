package com.ataulm.mijur.gallery;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ataulm.mijur.R;
import com.ataulm.mijur.base.android.MijurActivity;
import com.ataulm.mijur.data.Gallery;
import com.ataulm.mijur.data.GalleryItem;
import com.ataulm.mijur.data.GalleryProvider;
import com.novoda.notils.caster.Views;
import com.novoda.notils.logger.simple.Log;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class GalleryActivity extends MijurActivity implements GalleryAdapter.OnGalleryItemClickListener {

    private Subscription feedSubscription;
    private Observer observer;
    private GalleryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        adapter = new GalleryAdapter(getLayoutInflater(), this);
        RecyclerView list = Views.findById(this, R.id.gallery_list);
        int spanCount = getResources().getInteger(R.integer.gallery_num_columns);
        list.setLayoutManager(new GridLayoutManager(this, spanCount));
        list.setAdapter(adapter);

        observer = new Observer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        feedSubscription = GalleryProvider.instance().getGallery()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
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

    private class Observer implements rx.Observer<Gallery> {

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
