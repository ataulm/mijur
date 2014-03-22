package uk.co.ataulm.mijur.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

public class Image implements Parcelable {

    public String id;
    public String title;
    public String description;
    public long datetime;
    public String type;
    public boolean animated;
    public int width;
    public int height;
    public long size;
    public int views;
    public long bandwidth;
    public String deletehash;
    public String section;
    public String link;

    public Image() {
    }

    public Image(Parcel parcel) {
        id = parcel.readString();
        title = parcel.readString();
        description = parcel.readString();
        datetime = parcel.readLong();
        views = parcel.readInt();
        link = parcel.readString();
        type = parcel.readString();
        animated = parcel.readInt() == 1 ? true : false;
        width = parcel.readInt();
        height = parcel.readInt();
        size = parcel.readLong();
        bandwidth = parcel.readLong();
        deletehash = parcel.readString();
        section = parcel.readString();
    }

    public static boolean isUseable(Image image) {
        return !TextUtils.isEmpty(image.id)
                && !TextUtils.isEmpty(image.link)
                && image.width > 0
                && image.height > 0;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        return builder.append(Image.class.getName()).append("{")
                .append("id: ").append(id).append(", ")
                .append("title: ").append(title).append(", ")
                .append("description: ").append(description).append(", ")
                .append("datetime: ").append(datetime).append(", ")
                .append("type: ").append(type).append(", ")
                .append("animated: ").append(animated).append(", ")
                .append("width: ").append(width).append(", ")
                .append("height: ").append(height).append(", ")
                .append("size: ").append(size).append(", ")
                .append("views: ").append(views).append(", ")
                .append("bandwidth: ").append(bandwidth).append(", ")
                .append("deletehash: ").append(deletehash).append(", ")
                .append("section: ").append(section).append(", ")
                .append("link: ").append(link).append("}")
                .toString();
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
        parcel.writeString(type);
        parcel.writeInt(animated == true ? 1 : 0);
        parcel.writeInt(width);
        parcel.writeInt(height);
        parcel.writeLong(size);
        parcel.writeLong(bandwidth);
        parcel.writeString(deletehash);
        parcel.writeString(section);
    }
}
