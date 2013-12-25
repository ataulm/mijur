package uk.co.ataulm.mijur.app;

import android.net.Uri;

import novoda.lib.sqliteprovider.provider.SQLiteContentProviderImpl;
import uk.co.ataulm.mijur.BuildConfig;

public class ContentProvider extends SQLiteContentProviderImpl {

    public static final Uri AUTHORITY = Uri.parse("content://" + BuildConfig.PROVIDER_AUTHORITY);

    public enum Uris {
        GALLERY_ITEM;

        public Uri uri() {
            return AUTHORITY.buildUpon().appendPath(name()).build();
        }
    }

    public enum GalleryItem {
        id,                     // String, id of the image/album (unique primary key)
        title,                  // String, caption
        description,            // String, description
        datetime,               // long, time submitted to gallery (epoch)
        views,                  // int, number of views
        link,                   // String, url to image (direct), or album (webpage)
        account_url,            // String, username of uploader's account
        ups,                    // int, upvotes
        downs,                  // int, downvotes
        score,                  // int, net votes
        is_album,               // boolean
        cover,                  // String, id of the cover image if this is album (else null)
        layout,                 // String, view layout of album (if album)
        images_count,           // int, number of images in album (if album)
        type,                   // String, image mime type
        animated,               // boolean
        width,                  // int, pixels
        height,                 // int, pixels
        size,                   // long, bytes
        bandwidth,              // long, bytes
        deletehash,             // String, if you're logged in as image owner
    }

    /**
     * Manages the association between a GalleryImage and a GalleryAlbum.
     *
     * This data structure contains GalleryImages which are part of a GalleryAlbum. Each GalleryImage may be part of
     * multiple GalleryAlbums (though unlikely).
     */
    public enum GalleryAlbum {
        _id,                    // int, unique primary key (row id) autoincrement
        image,                  // String id, unlikely, but the same image can belong to multiple albums
        album                   // String id, album the image belongs to
    }

}
