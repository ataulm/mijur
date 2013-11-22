package uk.co.ataulm.mijur.core.api;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ImgurAuthShould {

    @Test
    public void retain_a_single_instance() throws Exception {
        ImgurAuthApi one = ImgurAuthApi.getInstance();
        ImgurAuthApi two = ImgurAuthApi.getInstance();

        assertThat(one == two, is(true));
    }
}
