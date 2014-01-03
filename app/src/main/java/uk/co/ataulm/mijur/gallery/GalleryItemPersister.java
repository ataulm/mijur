package uk.co.ataulm.mijur.gallery;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Instant;

import uk.co.ataulm.mijur.Provider;
import uk.co.ataulm.mijur.model.GalleryItem;

public class GalleryItemPersister {

    private static final Uri GALLERY_ITEM_URI = Provider.Uris.GALLERY_ITEM.uri();

    public static void persist(ContentResolver contentResolver, List<GalleryItem> galleryItems) {
        List<ContentValues> values = new ArrayList<ContentValues>();
        for (GalleryItem item : galleryItems) {
            if (!update(contentResolver, item)) {
                values.add(newContentValuesFrom(item));
            }
        }
        contentResolver.bulkInsert(GALLERY_ITEM_URI, values.toArray(new ContentValues[values.size()]));
    }

    private static boolean update(ContentResolver contentResolver, GalleryItem item) {
        ContentValues values = newUpdateContentValuesFrom(item);

        // TODO: looking to do an INSERT OR REPLACE (sqlite has) or UPSERT (sqlite has not)
        String selection = Provider.GalleryItem._ID + "= '" + item.id + "'";
        Cursor cursor = contentResolver.query(GALLERY_ITEM_URI, null, selection, null, null);
        if (cursor == null || cursor.getCount() == 0) {
            return false;
        }

        contentResolver.update(GALLERY_ITEM_URI, values, selection, null);
        return true;
    }

    private static ContentValues newUpdateContentValuesFrom(GalleryItem item) {
        ContentValues values = new ContentValues();
        values.put(Provider.GalleryItem.PAGE_VIEWS.name(), item.views);
        values.put(Provider.GalleryItem.UPVOTES.name(), item.ups);
        values.put(Provider.GalleryItem.DOWNVOTES.name(), item.downs);
        values.put(Provider.GalleryItem.SCORE.name(), item.score);
        values.put(Provider.GalleryItem.BANDWIDTH_BYTES.name(), item.bandwidth);
        values.put(Provider.GalleryItem.LAST_SYNCED_DATETIME.name(), new DateTime(Instant.now()).toString());
        return values;
    }

    private static ContentValues newContentValuesFrom(GalleryItem item) {
        ContentValues values = new ContentValues();
        values.put(Provider.GalleryItem._ID.toString(), item.id);
        values.put(Provider.GalleryItem.TITLE.toString(), item.title);
        values.put(Provider.GalleryItem.DESCRIPTION.toString(), item.description);
        values.put(Provider.GalleryItem.SUBMISSION_DATETIME.toString(), new DateTime(item.datetime * 1000).toString());
        values.put(Provider.GalleryItem.PAGE_VIEWS.toString(), item.views);
        values.put(Provider.GalleryItem.URL.toString(), item.link);
        values.put(Provider.GalleryItem.UPLOADER.toString(), item.account_url);
        values.put(Provider.GalleryItem.UPVOTES.toString(), item.ups);
        values.put(Provider.GalleryItem.DOWNVOTES.toString(), item.downs);
        values.put(Provider.GalleryItem.SCORE.toString(), item.score);
        values.put(Provider.GalleryItem.IS_ALBUM.toString(), item.is_album);
        values.put(Provider.GalleryItem.COVER_IMAGE_ID.toString(), item.cover);
        values.put(Provider.GalleryItem.ALBUM_LAYOUT.toString(), item.layout);
        values.put(Provider.GalleryItem.NUM_IMAGES.toString(), item.images_count);
        values.put(Provider.GalleryItem.IMAGE_MIME_TYPE.toString(), item.type);
        values.put(Provider.GalleryItem.IS_ANIMATED.toString(), item.animated);
        values.put(Provider.GalleryItem.WIDTH.toString(), item.width);
        values.put(Provider.GalleryItem.HEIGHT.toString(), item.height);
        values.put(Provider.GalleryItem.SIZE_BYTES.toString(), item.size);
        values.put(Provider.GalleryItem.BANDWIDTH_BYTES.toString(), item.bandwidth);
        values.put(Provider.GalleryItem.DELETE_HASH.toString(), item.deletehash);
        values.put(Provider.GalleryItem.LAST_SYNCED_DATETIME.toString(), new DateTime(Instant.now()).toString());
        values.put(Provider.GalleryItem.FIRST_SYNCED_DATETIME.toString(), new DateTime(Instant.now()).toString());
        return values;
    }

    public static GalleryItem newGalleryItemFrom(Cursor cursor, int position) {
        cursor.moveToPosition(position);
        GalleryItem item = new GalleryItem();

        if (cursor.getCount() == 0) {
            return item;
        }

        item.id = cursor.getString(cursor.getColumnIndex(Provider.GalleryItem._ID.toString()));
        item.title = cursor.getString(cursor.getColumnIndex(Provider.GalleryItem.TITLE.toString()));
        item.description = cursor.getString(cursor.getColumnIndex(Provider.GalleryItem.DESCRIPTION.toString()));
        item.datetime = new DateTime(cursor.getString(cursor.getColumnIndex(Provider.GalleryItem.SUBMISSION_DATETIME.toString()))).getMillis();
        item.lastSynced = new DateTime(cursor.getString(cursor.getColumnIndex(Provider.GalleryItem.LAST_SYNCED_DATETIME.toString())));
        item.firstSynced = new DateTime(cursor.getString(cursor.getColumnIndex(Provider.GalleryItem.FIRST_SYNCED_DATETIME.toString())));
        item.views = cursor.getInt(cursor.getColumnIndex(Provider.GalleryItem.PAGE_VIEWS.toString()));
        item.link = cursor.getString(cursor.getColumnIndex(Provider.GalleryItem.URL.toString()));
        item.account_url = cursor.getString(cursor.getColumnIndex(Provider.GalleryItem.UPLOADER.toString()));
        item.ups = cursor.getInt(cursor.getColumnIndex(Provider.GalleryItem.UPVOTES.toString()));
        item.downs = cursor.getInt(cursor.getColumnIndex(Provider.GalleryItem.DOWNVOTES.toString()));
        item.score = cursor.getInt(cursor.getColumnIndex(Provider.GalleryItem.SCORE.toString()));
        item.is_album = (cursor.getInt(cursor.getColumnIndex(Provider.GalleryItem.IS_ALBUM.toString())) == 0) ? false : true;
        item.cover = cursor.getString(cursor.getColumnIndex(Provider.GalleryItem.COVER_IMAGE_ID.toString()));
        item.layout = cursor.getString(cursor.getColumnIndex(Provider.GalleryItem.ALBUM_LAYOUT.toString()));
        item.images_count = cursor.getInt(cursor.getColumnIndex(Provider.GalleryItem.NUM_IMAGES.toString()));
        item.type = cursor.getString(cursor.getColumnIndex(Provider.GalleryItem.IMAGE_MIME_TYPE.toString()));
        item.animated = (cursor.getInt(cursor.getColumnIndex(Provider.GalleryItem.IS_ANIMATED.toString())) == 0) ? false : true;
        item.width = cursor.getInt(cursor.getColumnIndex(Provider.GalleryItem.WIDTH.toString()));
        item.height = cursor.getInt(cursor.getColumnIndex(Provider.GalleryItem.HEIGHT.toString()));
        item.size = cursor.getLong(cursor.getColumnIndex(Provider.GalleryItem.SIZE_BYTES.toString()));
        item.bandwidth = cursor.getLong(cursor.getColumnIndex(Provider.GalleryItem.BANDWIDTH_BYTES.toString()));
        item.deletehash = cursor.getString(cursor.getColumnIndex(Provider.GalleryItem.DELETE_HASH.toString()));
        return item;
    }

}
