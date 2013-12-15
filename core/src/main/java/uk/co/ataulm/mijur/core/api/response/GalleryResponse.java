package uk.co.ataulm.mijur.core.api.response;

import java.util.List;

import uk.co.ataulm.mijur.core.model.Gallery;
import uk.co.ataulm.mijur.core.model.GalleryElement;

public class GalleryResponse {

    public List<GalleryElement> data;
    public boolean success;
    public int status;

    public Gallery getData() {
        return new Gallery(data);
    }

}
