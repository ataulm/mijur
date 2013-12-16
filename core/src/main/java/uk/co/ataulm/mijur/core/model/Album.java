package uk.co.ataulm.mijur.core.model;

import java.util.List;

import uk.co.ataulm.mijur.core.util.ImgurUtils;

public class Album {

    public String id;
    public String title;
    public String description;
    public long datetime;
    public String cover;
    public String account_url;
    public String privacy;
    public String layout;
    public int views;
    public String link;
    public String deletehash;
    public int images_count;
    public List<Image> images;

    public static boolean isUseable(Album album) {
        return ImgurUtils.isNotEmpty(album.id)
                && ImgurUtils.isNotEmpty(album.link)
                && album.images_count > 0
                && album.privacy.equals("public");
    }

    @Override
    public String toString() {
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
                .append("deletehash: ").append(deletehash).append(", ")
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
