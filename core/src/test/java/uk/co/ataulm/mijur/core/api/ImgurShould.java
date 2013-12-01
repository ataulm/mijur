package uk.co.ataulm.mijur.core.api;

import org.junit.Ignore;
import org.junit.Test;

import java.net.HttpURLConnection;
import java.net.URL;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ImgurShould {
    private static final String PROPERTY_KEY_AUTHORISATION = "Authorization";
    private static final String AUTHORISATION_ENDPOINT = "https://api.imgur.com/oauth2/token";
    private static final String PROPERTY_VALUE_CLIENT_ID_PREFIX = "Client-ID ";

    @Test
    public void retain_a_single_instance() throws Exception {
        Imgur one = Imgur.getInstance();
        Imgur two = Imgur.getInstance();

        assertThat(one == two, is(true));
    }

    // TODO: investigate
    @Ignore("fails - getRequestProperty returns null")
    @Test
    public void add_client_id_to_http_connections_when_no_access_token_available() throws Exception {
        URL authUrl = new URL(AUTHORISATION_ENDPOINT);
        HttpURLConnection connection = (HttpURLConnection) authUrl.openConnection();

        Imgur.addAuthorisationHeadersTo(connection);

        String expected = PROPERTY_VALUE_CLIENT_ID_PREFIX + ApiConstants.API_CLIENT_ID;
        String actual = connection.getRequestProperty(PROPERTY_KEY_AUTHORISATION);
        assertThat(expected.equals(actual), is(true));
    }
}
