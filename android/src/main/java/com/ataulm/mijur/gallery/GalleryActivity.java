package com.ataulm.mijur.gallery;

import android.os.Bundle;
import android.widget.ListView;

import com.ataulm.mijur.R;
import com.ataulm.mijur.base.android.MijurActivity;
import com.ataulm.mijur.feed.Feed;
import com.ataulm.mijur.feed.FeedProvider;
import com.ataulm.mijur.feed.Gallery;
import com.novoda.notils.caster.Views;
import com.novoda.notils.logger.simple.Log;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class GalleryActivity extends MijurActivity {

    private ListView list;
    private FeedProvider provider;
    private Subscription feedSubscription;
    private GalleryUpdater galleryUpdater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        final GalleryAdapter adapter = new GalleryAdapter(getLayoutInflater(), Gallery.empty());
        list = Views.findById(this, R.id.gallery_list);
        list.setAdapter(adapter);

        provider = new FeedProvider();
        galleryUpdater = new GalleryUpdater(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        feedSubscription = provider.getFeed()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(galleryUpdater);
    }

    @Override
    protected void onPause() {
        super.onPause();
        feedSubscription.unsubscribe();
    }

    private static class GalleryUpdater implements Observer<Feed> {

        private final GalleryAdapter adapter;

        private GalleryUpdater(GalleryAdapter adapter) {
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
        public void onNext(Feed feed) {
            adapter.update(feed.gallery);
        }

    }

}
