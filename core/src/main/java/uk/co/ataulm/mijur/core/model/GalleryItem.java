package uk.co.ataulm.mijur.core.model;

/**
 * GalleryElementThing could be either a GalleryImage or GalleryAlbum.
 */
public class GalleryItem {

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
}
