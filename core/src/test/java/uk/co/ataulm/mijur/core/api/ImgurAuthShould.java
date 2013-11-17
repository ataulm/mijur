package uk.co.ataulm.mijur.core.api;

import org.junit.Test;
import org.scribe.oauth.OAuthService;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class ImgurAuthShould {

    @Test
    public void retain_a_single_instance() throws Exception {
        OAuthService service = mock(OAuthService.class);

        ImgurAuth one = ImgurAuth.getInstance(service);
        ImgurAuth two = ImgurAuth.getInstance(service);

        assertThat(one == two, is(true));
    }
}
