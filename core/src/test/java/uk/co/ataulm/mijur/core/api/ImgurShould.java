package uk.co.ataulm.mijur.core.api;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ImgurShould {

    @Test
    public void retain_a_single_instance() throws Exception {
        ImgurApi one = Imgur.instance();
        ImgurApi two = Imgur.instance();

        assertTrue(one == two);
    }

    @Test
    public void mark_image_response_as_successful_for_valid_image_id() throws Exception {
        String validId = "E8PCXjm";

        ImageResponse image = Imgur.getImageWith(validId);

        assertTrue(image.success);
    }

    @Test (expected = ImgurApiResourceNotFoundError.class)
    public void fail_for_invalid_image_id() throws Exception {
        String invalidId = "gerteqtwerterwhfrghdajfalksfwoirwfga";

        Imgur.getImageWith(invalidId);
    }
}
