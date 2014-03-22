package uk.co.ataulm.mijur;

import android.net.Uri;

import novoda.lib.sqliteprovider.provider.SQLiteContentProviderImpl;

public class Provider extends SQLiteContentProviderImpl {

    public static final Uri AUTHORITY = Uri.parse("content://" + BuildConfig.PROVIDER_AUTHORITY);

    public enum Uris {

        GALLERY_ITEM;

        public Uri uri() {
            return AUTHORITY.buildUpon().appendPath(name()).build();
        }

    }

    public enum GalleryItem {

        _ID,
        TITLE,
        DESCRIPTION,
        PAGE_VIEWS,
        URL,
        UPLOADER,
        UPVOTES,
        DOWNVOTES,
        SCORE,
        IS_ALBUM,
        COVER_IMAGE_ID,
        ALBUM_LAYOUT,
        NUM_IMAGES,
        IMAGE_MIME_TYPE,
        IS_ANIMATED,
        WIDTH,
        HEIGHT,
        SIZE_BYTES,
        BANDWIDTH_BYTES,
        DELETE_HASH,
        SUBMISSION_DATETIME,
        FIRST_SYNCED_DATETIME,
        LAST_SYNCED_DATETIME;

        @Override
        public String toString() {
            return name().toLowerCase();
        }

    }

    /**
     * Manages the association between a GalleryImage and a GalleryAlbum.
     * <p/>
     * This data structure contains GalleryImages which are part of a GalleryAlbum. Each GalleryImage may be part of
     * multiple GalleryAlbums (though unlikely).
     */
    public enum GalleryAlbum {

        _ID,
        IMAGE_ID,
        ALBUM_ID;

        @Override
        public String toString() {
            return name().toLowerCase();
        }

    }

}
