package uk.co.ataulm.mijur.app.gallery;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.novoda.notils.logger.Novogger;

import java.util.List;

import uk.co.ataulm.mijur.core.api.Imgur;
import uk.co.ataulm.mijur.core.model.Gallery;
import uk.co.ataulm.mijur.core.model.GalleryElement;

/**
 * GalleryElementLoader is used to retrieve a list of GalleryElements using the Imgur api.
 * <p/>
 * It doesn't currently monitor for content changes because the data is not modified after it is retrieved.
 * It may monitor for content changes later if and when a database is used (at which point this loader may be obsolete),
 * which could be used to cache "pages" of the Gallery (but not the images themselves).
 */
class GalleryElementLoader extends AsyncTaskLoader<List<GalleryElement>> {

    private List<GalleryElement> data;
    private int page;

    public GalleryElementLoader(Context context) {
        super(context);
        page = 1;
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
    public List<GalleryElement> loadInBackground() {
        Gallery gallery = Imgur.getGalleryWith(Gallery.Section.HOT, Gallery.Sort.TIME, page);
        Novogger.d("loadInBackground, gallery size: " + gallery.size());
        return gallery.elements;
    }

    @Override
    public void deliverResult(List<GalleryElement> data) {
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
