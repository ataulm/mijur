package uk.co.ataulm.mijur.core.api;


import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;


/**
 * Attempts OAuth 2.0 authentication without use of Scribe
 */
public class Imgur {
    private static final String PROPERTY_KEY_AUTHORISATION = "Authorization";
    private static final String PROPERTY_VALUE_CLIENT_ID_PREFIX = "Client-ID ";
    private static Imgur imgurAuth;

    private Imgur() {
    }

    public static Imgur get() {
        if (imgurAuth == null) {
            imgurAuth = new Imgur();
        }
        return imgurAuth;
    }

    /**
     * Adds authorisation headers to the given connection.
     * <p/>
     * If the user is logged in, an access token should be used, but otherwise it will use application's client ID.
     * Using the client ID rather than access token will mean only public read-only resources can be retrieved, and user
     * actions are limited (essentially limited to browsing only).
     *
     * @param connection
     */
    public static void addAuthorisationHeadersTo(HttpURLConnection connection) {
        connection.setRequestProperty(PROPERTY_KEY_AUTHORISATION, PROPERTY_VALUE_CLIENT_ID_PREFIX + ApiConstants.API_CLIENT_ID);
    }

    public void addAuthorisationHeadersTo(HttpURLConnection v){

    }

    public static void main(String... args) throws IOException {
        URL url = new URL("https://api.imgur.com/3/gallery/");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        Imgur.addAuthorisationHeadersTo(connection);

        InputStream responseIn = connection.getInputStream();
        StringBuilder stringBuilder = new StringBuilder();
        Scanner scanner = new Scanner(responseIn);
        while (scanner.hasNext()) {
            stringBuilder.append(scanner.next());
        }

        System.out.println(stringBuilder.toString());
    }
}
