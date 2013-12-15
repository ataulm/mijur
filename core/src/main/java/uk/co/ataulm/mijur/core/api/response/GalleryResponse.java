package uk.co.ataulm.mijur.core.api.response;

import java.util.List;

import uk.co.ataulm.mijur.core.model.GalleryAlbum;

public class GalleryResponse {

    // this is a list of galleryalbums/galleryimages
    public List<GalleryAlbum> data;
    public boolean success;
    public int status;

}
