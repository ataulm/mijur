package uk.co.ataulm.mijur.gallery;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.novoda.notils.logger.Novogger;

import java.util.Collections;
import java.util.List;

import uk.co.ataulm.mijur.api.Imgur;
import uk.co.ataulm.mijur.model.Gallery;
import uk.co.ataulm.mijur.model.GalleryItem;

class GalleryItemApiLoader extends AsyncTaskLoader<List<GalleryItem>> {

    private List<GalleryItem> data;
    private int page;

    public GalleryItemApiLoader(Context context) {
        super(context);
        page = 0;
    }

    @Override
    protected void onStartLoading() {
        if (data != null) {
            deliverResult(data);
        } else {
            forceLoad();
        }
    }

    @Override
    public List<GalleryItem> loadInBackground() {
        if (networkIsAvailable()) {
            Gallery gallery = Imgur.getGalleryWith(Gallery.Section.HOT, Gallery.Sort.TIME, page);
            return gallery.elements;
        }
        return Collections.emptyList();
    }

    private boolean networkIsAvailable() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();
    }

    @Override
    public void deliverResult(List<GalleryItem> data) {
        Novogger.d("deliverResult, data:" + data.size());
        if (isReset()) {
            return;
        }

        this.data = data;

        if (isStarted()) {
            super.deliverResult(data);
        }
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    @Override
    protected void onReset() {
        onStopLoading();
        this.data = null;
    }

}
