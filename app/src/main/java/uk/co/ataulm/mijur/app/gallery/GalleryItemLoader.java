package uk.co.ataulm.mijur.app.gallery;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.novoda.notils.logger.Novogger;

import java.util.Collections;
import java.util.List;

import uk.co.ataulm.mijur.core.api.Imgur;
import uk.co.ataulm.mijur.core.model.Gallery;
import uk.co.ataulm.mijur.core.model.GalleryItem;

/**
 * GalleryItemLoader is used to retrieve a list of GalleryItems using the Imgur api.
 * <p/>
 * It doesn't currently monitor for content changes because the data is not modified after it is retrieved.
 * It may monitor for content changes later if and when a database is used (at which point this loader may be obsolete),
 * which could be used to cache "pages" of the Gallery (but not the images themselves).
 */
class GalleryItemLoader extends AsyncTaskLoader<List<GalleryItem>> {

    private List<GalleryItem> data;
    private int page;

    public GalleryItemLoader(Context context) {
        super(context);
        page = 0;
    }

    @Override
    protected void onStartLoading() {
        if (data != null) {
            Novogger.d("onStartLoading, data not null");
            deliverResult(data);
        } else {
            Novogger.d("onStartLoading, forceLoad()");
            forceLoad();
        }
    }

    @Override
    public List<GalleryItem> loadInBackground() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            Gallery gallery = Imgur.getGalleryWith(Gallery.Section.HOT, Gallery.Sort.TIME, page);
            GalleryItemPersister.persist(getContext().getContentResolver(), gallery.elements);
            Novogger.d("loadInBackground, gallery size: " + gallery.size());
            return gallery.elements;
        }

        // TODO: fallback to cached gallery
        return Collections.emptyList();
    }

    @Override
    public void deliverResult(List<GalleryItem> data) {
        Novogger.d("deliverResult, data:" + data.size());
        if (isReset()) {
            return;
        }

        this.data = data;

        if (isStarted()) {
            Novogger.d("deliverResult, isStarted");
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
