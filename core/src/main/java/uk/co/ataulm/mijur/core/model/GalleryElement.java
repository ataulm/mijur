package uk.co.ataulm.mijur.core.model;

/**
 * GalleryElementThing could be either a GalleryImage or GalleryAlbum.
 */
public class GalleryElement {

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

}
