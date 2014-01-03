package uk.co.ataulm.mijur.api;

import org.junit.Test;

import uk.co.ataulm.mijur.model.Gallery;

import static org.junit.Assert.assertTrue;

public class ImgurShould {

    @Test
    public void retain_a_single_instance() throws Exception {
        ImgurApi one = Imgur.instance();
        ImgurApi two = Imgur.instance();

        assertTrue(one == two);
    }

    @Test
    public void return_a_useable_gallery_model_given_valid_parameters() throws Exception {
        Gallery gallery = Imgur.getGalleryWith(Gallery.Section.TOP, Gallery.Sort.VIRAL, 0);

        assertTrue(gallery.size() > 0);
    }

}
