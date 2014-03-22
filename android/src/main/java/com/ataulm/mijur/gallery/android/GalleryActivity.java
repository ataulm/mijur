package com.ataulm.mijur.gallery.android;

import android.os.Bundle;
import android.widget.ListView;

import com.ataulm.mijur.base.DeveloperError;
import com.ataulm.mijur.base.android.MijurActivity;
import com.ataulm.mijur.gallery.GalleryItems;
import com.ataulm.mijur.gallery.MockGalleryItemsProvider;
import com.novoda.notils.caster.Views;
import com.novoda.notils.logger.toast.Toaster;

import rx.Observable;
import rx.Observer;
import uk.co.ataulm.mijur.R;

public class GalleryActivity extends MijurActivity implements Observer<GalleryItems> {

    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        list = Views.findById(this, R.id.gallery_list);
        list.setAdapter(new GalleryAdapter());

        registerForUpdates();
    }

    private void registerForUpdates() {
        Observable<GalleryItems> observable = MockGalleryItemsProvider.getInstance().getGalleryItemsObservable();
        observable.subscribe(this);
    }

    @Override
    public void onNext(GalleryItems galleryItems) {
        ((GalleryAdapter) list.getAdapter()).swapList(galleryItems.asList());
    }

    @Override
    public void onCompleted() {
        throw DeveloperError.because("Observable<GalleryItems> is on-going and shouldn't complete.");
    }

    @Override
    public void onError(Throwable e) {
        new Toaster(this).popToast("well, shit.");
    }

}
