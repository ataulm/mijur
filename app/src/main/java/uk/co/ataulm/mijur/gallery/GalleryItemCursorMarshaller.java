package uk.co.ataulm.mijur.gallery;

import android.database.Cursor;

import com.novoda.notils.cursor.CursorMarshaller;

import org.joda.time.DateTime;

import uk.co.ataulm.mijur.Provider;
import uk.co.ataulm.mijur.model.GalleryItem;

public class GalleryItemCursorMarshaller implements CursorMarshaller<GalleryItem> {

    @Override
    public GalleryItem marshall(Cursor cursor) {
        GalleryItem item = new GalleryItem();

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
