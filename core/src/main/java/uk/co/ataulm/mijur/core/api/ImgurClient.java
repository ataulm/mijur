package uk.co.ataulm.mijur.core.api;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Attempts OAuth 2.0 authentication without use of Scribe
 */
public class ImgurClient implements RequestInterceptor {
    private static final String PROPERTY_KEY_AUTHORISATION = "Authorization";
    private static final String PROPERTY_VALUE_CLIENT_ID_PREFIX = "Client-ID ";
    private static final String API_URL = "https://api.imgur.com/3";
    private static ImgurClient imgurAuth;

    private ImgurClient() {
    }

    public static ImgurClient get() {
        if (imgurAuth == null) {
            imgurAuth = new ImgurClient();
        }
        return imgurAuth;
    }

    public static void main(String... args) throws IOException {
        testManually();
        testWithRetrofit();
    }

    private static void testManually() throws IOException {
        URL url = new URL(API_URL + "/image/E8PCXjm");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty(PROPERTY_KEY_AUTHORISATION, PROPERTY_VALUE_CLIENT_ID_PREFIX + ApiConstants.API_CLIENT_ID);

        InputStream responseIn = connection.getInputStream();
        StringBuilder stringBuilder = new StringBuilder();
        Scanner scanner = new Scanner(responseIn);
        while (scanner.hasNext()) {
            stringBuilder.append(scanner.next());
        }

        // prints full json output
        System.out.println(stringBuilder.toString());
    }

    private static void testWithRetrofit() {
        RestAdapter adapter = new RestAdapter.Builder()
                .setServer(API_URL)
                .setRequestInterceptor(ImgurClient.get())
                .build();

        Imgur imgur = adapter.create(Imgur.class);
        Image image = imgur.getImage("E8PCXjm");

        // prints "null"
        System.out.println(image.link);
    }

    @Override
    public void intercept(RequestFacade request) {
        request.addHeader(PROPERTY_KEY_AUTHORISATION, PROPERTY_VALUE_CLIENT_ID_PREFIX + ApiConstants.API_CLIENT_ID);
    }

    interface Imgur {
        @GET("/image/{id}")
        Image getImage(@Path("id") String id);
    }
}
