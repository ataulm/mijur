package uk.co.ataulm.mijur.core.api;

public class ImageResponse {
    public Image data;
    public boolean success;
    public int status;

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        return builder.append(getClass().getName()).append("{\"data\": {")
                .append("id: ").append(data.id).append(", ")
                .append("title: ").append(data.title).append(", ")
                .append("description: ").append(data.description).append(", ")
                .append("datetime: ").append(data.datetime).append(", ")
                .append("type: ").append(data.type).append(", ")
                .append("animated: ").append(data.animated).append(", ")
                .append("width: ").append(data.width).append(", ")
                .append("height: ").append(data.height).append(", ")
                .append("size: ").append(data.size).append(", ")
                .append("views: ").append(data.views).append(", ")
                .append("bandwidth: ").append(data.bandwidth).append(", ")
                .append("deletehash: ").append(data.deletehash).append(", ")
                .append("section: ").append(data.section).append(", ")
                .append("link: ").append(data.link).append("}, ")
                .append("\"success\": ").append(success).append(",")
                .append("\"status\": ").append(status).append("}")
                .toString();
    }

    static class Image {
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
    }
}
