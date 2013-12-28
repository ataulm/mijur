package uk.co.ataulm.mijur.app.gallery;

import android.content.Context;
import android.content.CursorLoader;

import uk.co.ataulm.mijur.app.Provider;

class GalleryItemCursorLoader extends CursorLoader {

    private static final String FALSE = "0";

    public GalleryItemCursorLoader(Context context) {
        super(context,
                Provider.Uris.GALLERY_ITEM.uri(),
                null,
                Provider.GalleryItem.IS_ANIMATED + " = ? AND " + Provider.GalleryItem.IS_ALBUM + " = ?",
                new String[]{FALSE, FALSE},
                Provider.GalleryItem.SUBMISSION_DATETIME.toString() + " DESC");
    }

}
