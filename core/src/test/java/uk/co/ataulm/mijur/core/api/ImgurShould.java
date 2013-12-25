package uk.co.ataulm.mijur.core.api;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.junit.Test;

import uk.co.ataulm.mijur.core.model.Gallery;
import uk.co.ataulm.mijur.core.model.GalleryItem;

import static org.junit.Assert.assertNotNull;
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

    @Test
    public void allow_jpg_extensions_for_gif_images() throws Exception {
        GalleryItem item = new GalleryItem();
        // for http://i.imgur.com/U6l0Ddb.gif
        item.id = "U6l0Ddb";

        // returns http://i.imgur.com/U6l0Ddb.jpg
        String path = GalleryItem.getImageUrlFor(item);
        URL url = new URL(path);
        URLConnection connection = url.openConnection();
        InputStream stream = connection.getInputStream();

        assertNotNull(stream);
    }

}
