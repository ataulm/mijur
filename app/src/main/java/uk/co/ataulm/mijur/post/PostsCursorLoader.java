package uk.co.ataulm.mijur.post;

import android.content.Context;
import android.content.CursorLoader;

import uk.co.ataulm.mijur.Provider;

public class PostsCursorLoader extends CursorLoader {

    private static final String FALSE = "0";

    public PostsCursorLoader(Context context) {
        super(context);
        setUri(Provider.Uris.GALLERY_ITEM.uri());
        setSelection(Provider.GalleryItem.IS_ANIMATED + " = ? AND " + Provider.GalleryItem.IS_ALBUM + " = ?");
        setSelectionArgs(new String[]{FALSE, FALSE});
        setSortOrder(Provider.GalleryItem.SUBMISSION_DATETIME.toString() + " DESC");
    }

}
