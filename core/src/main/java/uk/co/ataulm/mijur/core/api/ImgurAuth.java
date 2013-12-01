package uk.co.ataulm.mijur.core.api;


import java.net.HttpURLConnection;

/**
 * Attempts OAuth 2.0 authentication without use of Scribe
 */
public class ImgurAuth {
    private static final String PROPERTY_KEY_AUTHORISATION = "Authorization";
    private static final String PROPERTY_VALUE_CLIENT_ID_PREFIX = "Client-ID ";
    private static final String PROPERTY_VALUE_BEARER_PREFIX = "Bearer ";
    private static ImgurAuth imgurAuth;

    private String accessToken;
    private String refreshToken;
    private long tokenExpiry;

    private ImgurAuth() {
    }

    public static ImgurAuth getInstance() {
        if (imgurAuth == null) {
            imgurAuth = new ImgurAuth();
        }
        return imgurAuth;
    }

    public static boolean isLoggedIn() {
        return getInstance().accessToken != null;
    }

    public static boolean isLoggedOut() {
        return !isLoggedIn();
    }

    /**
     * Simply clears the access and refresh token references.
     * <p/>
     * Caller should also remove access and refresh token references (e.g. in SharedPreferences) on
     * client side.
     */
    public static void logout() {
        getInstance().accessToken = null;
        getInstance().refreshToken = null;
    }

    // TODO
    public static String requestNewAccessToken() {
        return null;
    }

    /**
     * Sets access token, used for accessing resources that require user login.
     * <p/>
     * User login is required for voting, uploads, messages, commenting and viewing private images.
     *
     * @param accessToken
     * @return this ImgurAuth
     */
    public ImgurAuth withAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    /**
     * Sets refresh token (used to retrieve new access tokens).
     *
     * @param refreshToken
     * @return this ImgurAuth
     */
    public ImgurAuth withRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
        return this;
    }

    /**
     * Sets length of time (milliseconds) that access tokens are valid for.
     *
     * @param tokenExpiry
     * @return this ImgurAuth
     */
    public ImgurAuth expiresIn(long tokenExpiry) {
        this.tokenExpiry = tokenExpiry;
        return this;
    }

    /**
     * Adds authorisation headers to the given connection.
     * <p/>
     * If the user is logged in, the access token is used, otherwise the application's client ID.
     * Using the client ID rather than access token will mean only public read-only resources can be
     * retrieved, and user actions are limited (essentially limited to browsing only).
     *
     * @param connection
     */
    public static void addAuthorisationHeadersTo(HttpURLConnection connection) {
        String accessToken = getInstance().accessToken;
        if (weCanUse(accessToken)) {
            connection.setRequestProperty(PROPERTY_KEY_AUTHORISATION, PROPERTY_VALUE_BEARER_PREFIX + accessToken);
        } else {
            connection.setRequestProperty(PROPERTY_KEY_AUTHORISATION, PROPERTY_VALUE_CLIENT_ID_PREFIX + ApiConstants.API_CLIENT_ID);
        }
    }

    private static boolean weCanUse(String string) {
        return string != null && string.length() > 0;
    }
}
