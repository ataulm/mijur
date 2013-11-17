package uk.co.ataulm.mijur.core.api;

import retrofit.http.GET;
import retrofit.http.Path;

public class Image {
    public final String id;
    public final String title;
    public final String description;
    public final int timeAddedToGallery;
    public final String mimeType;
    public final Boolean isAnimated;
    public final int width;
    public final int height;
    public final int sizeInBytes;
    public final int numViews;
    public final int bandwidthInBytes;
    public final String deleteHash;
    public final String section;
    public final String url;

    public Image(String id, String title, String description, int timeAddedToGallery, String mimeType, Boolean isAnimated, int width, int height,
                 int sizeInBytes, int numViews, int bandwidthInBytes, String deleteHash, String section, String url) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.timeAddedToGallery = timeAddedToGallery;
        this.mimeType = mimeType;
        this.isAnimated = isAnimated;
        this.width = width;
        this.height = height;
        this.sizeInBytes = sizeInBytes;
        this.numViews = numViews;
        this.bandwidthInBytes = bandwidthInBytes;
        this.deleteHash = deleteHash;
        this.section = section;
        this.url = url;
    }

    public interface Api {
        @GET("/image/{id}")
        Image image(@Path("id") String id);
    }
}
