package uk.co.ataulm.mijur.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import org.joda.time.DateTime;

public class GalleryItem implements Parcelable {

    private static final String BASE_URL = "http://i.imgur.com/";
    private static final String IMAGE_EXTENSION = ".jpg";

    // both gallery album and gallery image
    public String id;
    public String title;
    public String description;
    public long datetime;
    public int views;
    public String link;
    public String vote;
    public String account_url;
    public int ups;
    public int downs;
    public int score;
    public boolean is_album;

    // gallery album
    public String cover;
    public String privacy;
    public String layout;
    public int images_count;
    public List<Image> images;

    // gallery image
    public String type;
    public boolean animated;
    public int width;
    public int height;
    public long size;
    public long bandwidth;
    public String deletehash;
    public String section;

    public DateTime firstSynced;
    public DateTime lastSynced;

    public GalleryItem() {
    }

    public GalleryItem(Parcel parcel) {
        id = parcel.readString();
        title = parcel.readString();
        description = parcel.readString();
        datetime = parcel.readLong();
        views = parcel.readInt();
        link = parcel.readString();
        vote = parcel.readString();
        account_url = parcel.readString();
        ups = parcel.readInt();
        downs = parcel.readInt();
        is_album = parcel.readInt() == 1 ? true : false;

        cover = parcel.readString();
        privacy = parcel.readString();
        layout = parcel.readString();
        images_count = parcel.readInt();
        parcel.readList(images, Image.class.getClassLoader());

        type = parcel.readString();
        animated = parcel.readInt() == 1 ? true : false;
        width = parcel.readInt();
        height = parcel.readInt();
        size = parcel.readLong();
        bandwidth = parcel.readLong();
        deletehash = parcel.readString();
        section = parcel.readString();

        firstSynced = new DateTime(parcel.readLong());
        lastSynced = new DateTime(parcel.readLong());
    }

    public static String getImageUrlFor(GalleryItem item) {
        return getUrlWithId(item) + IMAGE_EXTENSION;
    }

    /**
     * Gets url to the thumbnail of the GalleryItem at the requested size.
     *
     * @param item
     * @param thumbnailSuffix http://api.imgur.com/models/gallery_image
     * @return url the url to the image
     */
    public static String getThumbnailImageUrlFor(GalleryItem item, String thumbnailSuffix) {
        if (!isValidThumbnailSuffix(thumbnailSuffix)) {
            throw new IllegalArgumentException("Must be one of {t, m, l} (small, medium, large). Given: " + thumbnailSuffix);
        }

        return getUrlWithId(item) + thumbnailSuffix + IMAGE_EXTENSION;
    }

    private static String getUrlWithId(GalleryItem item) {
        String id = (item.is_album) ? item.cover : item.id;
        return BASE_URL + id;
    }

    private static boolean isValidThumbnailSuffix(String thumbnailSuffix) {
        if (thumbnailSuffix.equals("t")) {
            return true;
        }

        if (thumbnailSuffix.equals("m")) {
            return true;
        }

        if (thumbnailSuffix.equals("l")) {
            return true;
        }

        return false;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(GalleryItem.class.getSimpleName());
        builder.append('{')
                .append("id:").append(id).append(',')
                .append("title:").append(title).append(',')
                .append("is_album:").append(is_album).append(',')
                .append("link:").append(link).append('}');

        return builder.toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(id);
        parcel.writeString(title);
        parcel.writeString(description);
        parcel.writeLong(datetime);
        parcel.writeInt(views);
        parcel.writeString(link);
        parcel.writeString(vote);
        parcel.writeString(account_url);
        parcel.writeInt(ups);
        parcel.writeInt(downs);
        parcel.writeInt(is_album == true ? 1 : 0);

        parcel.writeString(cover);
        parcel.writeString(privacy);
        parcel.writeString(layout);
        parcel.writeInt(images_count);
        parcel.writeList(images);

        parcel.writeString(type);
        parcel.writeInt(animated == true ? 1 : 0);
        parcel.writeInt(width);
        parcel.writeInt(height);
        parcel.writeLong(size);
        parcel.writeLong(bandwidth);
        parcel.writeString(deletehash);
        parcel.writeString(section);

        parcel.writeLong(firstSynced.getMillis());
        parcel.writeLong(lastSynced.getMillis());
    }
}
