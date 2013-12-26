package uk.co.ataulm.mijur.app.gallery;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Instant;

import uk.co.ataulm.mijur.app.ContentProvider;
import uk.co.ataulm.mijur.core.model.GalleryItem;

public class GalleryItemPersister {

    private static final Uri GALLERY_ITEM_URI = ContentProvider.Uris.GALLERY_ITEM.uri();

    public static void persist(ContentResolver contentResolver, List<GalleryItem> galleryItems) {
        List<ContentValues> values = new ArrayList<ContentValues>();
        for (GalleryItem item : galleryItems) {
            // FIXME: SQLException - "failed to update row into content://uk.co...Provider/GALLERY_ITEM"
//            if (!update(contentResolver, item)) {
                values.add(valuesFrom(item));
//            }
        }
        contentResolver.bulkInsert(GALLERY_ITEM_URI, values.toArray(new ContentValues[values.size()]));
    }

    private static boolean update(ContentResolver contentResolver, GalleryItem item) {
        ContentValues values = new ContentValues();
        values.put(ContentProvider.GalleryItem.views.name(), item.views);
        values.put(ContentProvider.GalleryItem.ups.name(), item.ups);
        values.put(ContentProvider.GalleryItem.downs.name(), item.downs);
        values.put(ContentProvider.GalleryItem.score.name(), item.score);
        values.put(ContentProvider.GalleryItem.bandwidth.name(), item.bandwidth);
        values.put(ContentProvider.GalleryItem.lastSynced.name(), new DateTime(Instant.now()).toString());

        String selection = ContentProvider.GalleryItem._id + " = '" + item.id + "'";
        return contentResolver.update(GALLERY_ITEM_URI, values, selection, null) == 1;
    }

    private static ContentValues valuesFrom(GalleryItem item) {
        ContentValues values = new ContentValues();
        values.put(ContentProvider.GalleryItem._id.name(), item.id);
        values.put(ContentProvider.GalleryItem.title.name(), item.title);
        values.put(ContentProvider.GalleryItem.description.name(), item.description);
        values.put(ContentProvider.GalleryItem.datetime.name(), new DateTime(item.datetime * 1000).toString());
        values.put(ContentProvider.GalleryItem.views.name(), item.views);
        values.put(ContentProvider.GalleryItem.link.name(), item.link);
        values.put(ContentProvider.GalleryItem.account_url.name(), item.account_url);
        values.put(ContentProvider.GalleryItem.ups.name(), item.ups);
        values.put(ContentProvider.GalleryItem.downs.name(), item.downs);
        values.put(ContentProvider.GalleryItem.score.name(), item.score);
        values.put(ContentProvider.GalleryItem.is_album.name(), item.is_album);
        values.put(ContentProvider.GalleryItem.cover.name(), item.cover);
        values.put(ContentProvider.GalleryItem.layout.name(), item.layout);
        values.put(ContentProvider.GalleryItem.images_count.name(), item.images_count);
        values.put(ContentProvider.GalleryItem.type.name(), item.type);
        values.put(ContentProvider.GalleryItem.animated.name(), item.animated);
        values.put(ContentProvider.GalleryItem.width.name(), item.width);
        values.put(ContentProvider.GalleryItem.height.name(), item.height);
        values.put(ContentProvider.GalleryItem.size.name(), item.size);
        values.put(ContentProvider.GalleryItem.bandwidth.name(), item.bandwidth);
        values.put(ContentProvider.GalleryItem.deletehash.name(), item.deletehash);
        values.put(ContentProvider.GalleryItem.lastSynced.name(), new DateTime(Instant.now()).toString());
        return values;
    }

}
