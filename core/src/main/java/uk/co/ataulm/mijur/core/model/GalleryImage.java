package uk.co.ataulm.mijur.core.model;

import uk.co.ataulm.mijur.core.util.ImgurUtils;

public class GalleryImage implements GalleryElement {

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
    public String link;
    public String vote;
    public String section;
    public String account_url;
    public int ups;
    public int downs;
    public int score;
    public boolean is_album;

    public static boolean isUseable(GalleryImage image) {
        return  image != null
                && ImgurUtils.isNotEmpty(image.id)
                && ImgurUtils.isNotEmpty(image.link)
                && ImgurUtils.isNotEmpty(image.title)
                && image.width > 0
                && image.height > 0;
    }

    @Override
    public String toString() {
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

}
