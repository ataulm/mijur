package uk.co.ataulm.mijur.core.model;

/**
 * GalleryItem could be either a GalleryImage or GalleryAlbum.
 */
public class GalleryItem {

    public String id;
    public String title;
    public String description;
    public long datetime;
    public String cover;
    public int views;
    public String link;
    public String vote;
    public String account_url;
    public int ups;
    public int downs;
    public int score;
    public boolean is_album;

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

    public static String getImageUrlFor(GalleryItem item) {
        if (item.is_album) {
            // worried it's not a jpg? The jpg extension also works for gifs on Imgur (2013.12.24)
            return String.format("http://i.imgur.com/%s.jpg", item.cover);
        }
        return item.link;
    }

}
