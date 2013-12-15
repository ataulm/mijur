package uk.co.ataulm.mijur.core.api;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ImgurClientShould {

    @Test
    public void retain_a_single_instance() throws Exception {
        ImgurClient.Imgur one = ImgurClient.get();
        ImgurClient.Imgur two = ImgurClient.get();

        assertThat(one == two, is(true));
    }

    @Test
    public void return_an_image_object_for_a_valid_image_id() throws Exception {
        String validId = "E8PCXjm";

        ImageResponse image = ImgurClient.get().image(validId);

        assertThat(image.success, is(true));
    }
}
