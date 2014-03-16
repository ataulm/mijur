package uk.co.ataulm.mijur.api.response;

import java.util.List;

import uk.co.ataulm.mijur.model.Gallery;
import uk.co.ataulm.mijur.model.GalleryItem;

public class GalleryResponse {

    public List<GalleryItem> data;
    public boolean success;
    public int status;

    public Gallery getData() {
        return new Gallery(data);
    }

}
