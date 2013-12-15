package uk.co.ataulm.mijur.core.model;

import java.util.List;

/**
 * GalleryElementThing could be either a GalleryImage or GalleryAlbum.
 */
public class GalleryElement {

    // Both GalleryImage and GalleryAlbum
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
    // GalleryImage
    public String type;
    public boolean animated;
    public int width;
    public int height;
    public long size;
    public long bandwidth;
    public String deletehash;
    public String section;
    // GalleryAlbum
    public String cover;
    public String privacy;
    public String layout;
    public int images_count;
    public List<Image> images;

    @Override
    public String toString() {
        if (is_album) {
            return getGalleryAlbumAsString();
        }
        return getGalleryImageAsString();
    }

    private String getGalleryImageAsString() {
        StringBuilder builder = new StringBuilder();
        return builder.append(getClass().getName()).append("{")
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
                .append("link: ").append(link).append("}")
                .append("vote: ").append(vote).append("}")
                .append("section: ").append(section).append(", ")
                .append("account_url: ").append(account_url).append(", ")
                .append("ups: ").append(ups).append(", ")
                .append("downs: ").append(downs).append(", ")
                .append("score: ").append(score).append(", ")
                .append("is_album: ").append(is_album).append(", ")
                .toString();
    }

    private String getGalleryAlbumAsString() {
        StringBuilder builder = new StringBuilder();
        return builder.append(getClass().getName()).append("{")
                .append("id: ").append(id).append(", ")
                .append("title: ").append(title).append(", ")
                .append("description: ").append(description).append(", ")
                .append("datetime: ").append(datetime).append(", ")
                .append("cover: ").append(cover).append(", ")
                .append("account_url: ").append(account_url).append(", ")
                .append("privacy: ").append(privacy).append(", ")
                .append("layout: ").append(layout).append(", ")
                .append("views: ").append(views).append(", ")
                .append("link: ").append(link).append(", ")
                .append("ups: ").append(ups).append(", ")
                .append("downs: ").append(downs).append(", ")
                .append("score: ").append(score).append(", ")
                .append("is_album: ").append(is_album).append(", ")
                .append("vote: ").append(vote).append(", ")
                .append("images_count: ").append(images_count).append(", ")
                .append("images: ").append(getImagesAsString()).append("}")
                .toString();
    }

    private String getImagesAsString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        for (Image image : images) {
            builder.append(Image.class.getSimpleName())
                    .append("{")
                    .append(image.toString())
                    .append("}");
        }
        builder.append("}");
        return builder.toString();
    }

}
