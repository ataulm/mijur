package uk.co.ataulm.mijur.post;

import android.content.Context;
import android.content.CursorLoader;

import uk.co.ataulm.mijur.Provider;

class PostCursorLoader extends CursorLoader {

    public PostCursorLoader(Context context, String id) {
        super(context);
        setUri(Provider.Uris.GALLERY_ITEM.uri());
        setSelection(Provider.GalleryItem._ID.name() + " = '" + id + "'");
    }

}
