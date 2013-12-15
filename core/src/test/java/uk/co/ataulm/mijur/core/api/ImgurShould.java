package uk.co.ataulm.mijur.core.api;

import org.junit.Test;

import uk.co.ataulm.mijur.core.api.error.ImgurApiResourceNotFoundError;
import uk.co.ataulm.mijur.core.model.Album;
import uk.co.ataulm.mijur.core.model.GalleryAlbum;
import uk.co.ataulm.mijur.core.model.GalleryImage;
import uk.co.ataulm.mijur.core.model.Image;

import static org.junit.Assert.assertTrue;

public class ImgurShould {

    @Test
    public void retain_a_single_instance() throws Exception {
        ImgurApi one = Imgur.instance();
        ImgurApi two = Imgur.instance();

        assertTrue(one == two);
    }

    @Test
    public void return_a_useable_image_model_given_a_valid_id() throws Exception {
        String validId = "E8PCXjm";

        Image image = Imgur.getImageWith(validId);

        assertTrue(Image.isUseable(image));
    }

    @Test(expected = ImgurApiResourceNotFoundError.class)
    public void fail_given_an_invalid_image_id() throws Exception {
        String invalidId = "gerteqtwerterwhfrghdajfalksfwoirwfga";

        Imgur.getImageWith(invalidId);
    }

    @Test
    public void return_a_useable_gallery_image_model_given_a_valid_id() throws Exception {
        String validId = "OUHDm";

        GalleryImage image = Imgur.getGalleryImageWith(validId);

        assertTrue(GalleryImage.isUseable(image));
    }

    @Test(expected = ImgurApiResourceNotFoundError.class)
    public void fail_given_an_invalid_gallery_image_id() throws Exception {
        String invalidId = "gerteqtwerterwhfrghdajfalksfwoirwfga";

        Imgur.getGalleryImageWith(invalidId);
    }

    @Test
    public void return_a_useable_album_model_given_a_valid_id() throws Exception {
        String validId = "lDRB2";

        Album album = Imgur.getAlbumWith(validId);

        assertTrue(Album.isUseable(album));
    }

    @Test (expected = ImgurApiResourceNotFoundError.class)
    public void fail_given_an_invalid_album_id() throws Exception {
        String invalidId = "gerteqtwerterwhfrghdajfalksfwoirwfga";

        Imgur.getAlbumWith(invalidId);
    }

    @Test
    public void return_a_useable_gallery_album_model_given_a_valid_id() throws Exception {
        String validId = "1S2u5";

        GalleryAlbum album = Imgur.getGalleryAlbumWith(validId);

        assertTrue(GalleryAlbum.isUseable(album));
    }

    @Test(expected = ImgurApiResourceNotFoundError.class)
    public void fail_given_an_invalid_gallery_album_id() throws Exception {
        String invalidId = "gerteqtwerterwhfrghdajfalksfwoirwfga";

        Imgur.getGalleryAlbumWith(invalidId);
    }

}
