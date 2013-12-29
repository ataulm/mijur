package uk.co.ataulm.mijur.app.gallery;

import android.content.Context;
import android.content.CursorLoader;

import uk.co.ataulm.mijur.app.Provider;

class GalleryItemCursorLoader extends CursorLoader {

    private static final String FALSE = "0";
    private static final String LIMIT_KEY = "limit";
    private static final String ROW_OFFSET = "1";
    private static final String NO_ROW_LIMIT = "999999";

    public GalleryItemCursorLoader(Context context) {
        super(context);
        setUri(Provider.Uris.GALLERY_ITEM.uri().buildUpon().appendQueryParameter(LIMIT_KEY, ROW_OFFSET + "," + NO_ROW_LIMIT).build());
        setSelection(Provider.GalleryItem.IS_ANIMATED + " = ? AND " + Provider.GalleryItem.IS_ALBUM + " = ?");
        setSelectionArgs(new String[]{FALSE, FALSE});
        setSortOrder(Provider.GalleryItem.SUBMISSION_DATETIME.toString() + " DESC");
    }

}
