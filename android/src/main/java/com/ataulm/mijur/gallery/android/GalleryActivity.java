package com.ataulm.mijur.gallery.android;

import android.os.Bundle;
import android.widget.ListView;

import com.ataulm.mijur.R;
import com.ataulm.mijur.base.android.MijurActivity;
import com.ataulm.mijur.gallery.ApiGalleryItemsProvider;
import com.ataulm.mijur.gallery.GalleryItems;
import com.novoda.notils.caster.Views;
import com.novoda.notils.logger.simple.Log;
import com.novoda.notils.logger.toast.Toaster;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class GalleryActivity extends MijurActivity {

    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        list = Views.findById(this, R.id.gallery_list);
        list.setAdapter(new GalleryAdapter());

        subscribeToGalleryItemsObservable();
    }

    private void subscribeToGalleryItemsObservable() {
//        Observable<GalleryItems> observable = MockGalleryItemsProvider.getInstance().newObservable();
        Observable<GalleryItems> observable = new ApiGalleryItemsProvider().newObservable();
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new GalleryItemsObserver());
    }

    private class GalleryItemsObserver implements Observer<GalleryItems> {

        @Override
        public void onNext(GalleryItems galleryItems) {
            ((GalleryAdapter) list.getAdapter()).swapList(galleryItems.asList());
            Log.d("onNext: list count = " + list.getAdapter().getCount());
        }

        @Override
        public void onCompleted() {
            Log.d("onCompleted: list count = " + list.getAdapter().getCount());
        }

        @Override
        public void onError(Throwable e) {
            new Toaster(GalleryActivity.this).popToast("well, poop.");
            Log.e("onError", e);
        }

    }

}
