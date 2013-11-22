package uk.co.ataulm.mijur.core.api;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.DefaultApi20;
import org.scribe.model.*;
import org.scribe.oauth.OAuth20ServiceImpl;
import org.scribe.oauth.OAuthService;

/**
 * Until I can learn a bit more about using OAuth with Imgur, I followed Brian Hsu's example:
 * http://bone.twbbs.org.tw/blog/2013-05-08-ScribeImgUr3-English.html
 */
public class ImgurAuthApi extends DefaultApi20 {
    private static final String AUTHORISATION_URL = "https://api.imgur.com/oauth2/authorize";
    private static final String ACCESS_TOKEN_URL = "https://api.imgur.com/oauth2/token";
    private static final String AUTH_URL_PARAMETERS_PIN = "%s?client_id=%s&response_type=pin";
    private static final String AUTH_URL_PARAMETERS_CODE = "%s?client_id=%s&response_type=code";
    private static final String OUT_OF_BAND_CALLBACK = "oob";

    private static ImgurAuthApi imgurAuth;

    private ImgurAuthApi() {
    }

    @Override
    public Verb getAccessTokenVerb() {
        return Verb.POST;
    }

    public static ImgurAuthApi getInstance() {
        if (imgurAuth == null) {
            imgurAuth = new ImgurAuthApi();
        }
        return imgurAuth;
    }

    @Override
    public String getAccessTokenEndpoint() {
        return ACCESS_TOKEN_URL;
    }

    @Override
    public String getAuthorizationUrl(OAuthConfig config) {
        if (isOutOfBand(config)) {
            return getAuthorisationUrlWithPinResponse();
        }
        return getAuthorisationUrlWithCodeResponse();
    }

    private String getAuthorisationUrlWithPinResponse() {
        return String.format(AUTH_URL_PARAMETERS_PIN, AUTHORISATION_URL, ApiConstants.API_CLIENT_ID);
    }

    private String getAuthorisationUrlWithCodeResponse() {
        return String.format(AUTH_URL_PARAMETERS_CODE, AUTHORISATION_URL, ApiConstants.API_CLIENT_ID);
    }

    private boolean isOutOfBand(OAuthConfig config) {
        return config.getCallback().equals(OUT_OF_BAND_CALLBACK);
    }

    @Override
    public OAuthService createService(OAuthConfig config) {
        return new ImgurOAuthService(this, config);
    }

    private class ImgurOAuthService extends OAuth20ServiceImpl {
        static final String GRANT_TYPE = "grant_type";
        static final String GRANT_TYPE_PIN = "pin";
        static final String GRANT_TYPE_AUTH_CODE = "authorization_code";
        static final String HEADER_AUTH = "Authorization";
        static final String HEADER_BEARER_PREFIX = "Bearer ";
        static final String HEADER_CLIENT_ID_PREFIX = "Client-ID ";

        DefaultApi20 api;
        OAuthConfig config;
        OAuthRequest request;

        ImgurOAuthService(DefaultApi20 api, OAuthConfig config) {
            super(api, config);
        }

        @Override
        public Token getAccessToken(Token requestToken, Verifier verifier) {
            request = new OAuthRequest(
                    api.getAccessTokenVerb(),
                    api.getAccessTokenEndpoint()
            );

            // ImgUr API ver 3 using POST to get token, and we need pass
            // parameters using Body parameter, query string will not work.
            request.addBodyParameter(OAuthConstants.CLIENT_ID, config.getApiKey());
            request.addBodyParameter(OAuthConstants.CLIENT_SECRET, config.getApiSecret());
            request = getRequestWithConfiguredGrantType(verifier);

            Response response = request.send();

            return api.getAccessTokenExtractor().extract(response.getBody());
        }

        @Override
        public void signRequest(Token accessToken, OAuthRequest request) {
            if (accessToken != null) {
                request.addHeader(HEADER_AUTH, HEADER_BEARER_PREFIX + accessToken.getToken());
            } else {
                request.addHeader(HEADER_AUTH, HEADER_CLIENT_ID_PREFIX + config.getApiKey());
            }
        }

        private OAuthRequest getRequestWithConfiguredGrantType(Verifier verifier) {
            if (isOutOfBand(config)) {
                request.addBodyParameter(GRANT_TYPE, GRANT_TYPE_PIN);
                request.addBodyParameter(GRANT_TYPE_PIN, verifier.getValue());
            }
            request.addBodyParameter(GRANT_TYPE, GRANT_TYPE_AUTH_CODE);
            return request;
        }
    }

    /**
     * Working out the authentication workflow for Imgur.
     *
     * @param args the command line arguments, ignored
     */
    public static void main(String... args) {
        final String protectedResourceUrl = "https://api.imgur.com/3/account/me/albums";

        ImgurAuthApi imgurAuthApi = ImgurAuthApi.getInstance();
        OAuthService service = new ServiceBuilder().provider(ImgurAuthApi.class).apiKey(ApiConstants.API_CLIENT_ID).apiSecret(ApiConstants.API_CLIENT_SECRET).build();

        String authUrl = service.getAuthorizationUrl(null);

//        Scanner in = new Scanner(System.in);
//
//        System.out.println("=== ImgUr's OAuth Workflow ===");
//        System.out.println();
//
//        System.out.println("Now go and authorize Scribe here:");
//        String authorizationUrl = service.getAuthorizationUrl(null);
//        System.out.println(authorizationUrl);
//        System.out.println("And paste the verifier here");
//        System.out.print(">>");
//        Verifier verifier = new Verifier(in.nextLine());
//        System.out.println();
//
//        // Trade the Request Token and Verfier for the Access Token
//        System.out.println("Trading the Request Token for an Access Token...");
//        Token accessToken = service.getAccessToken(null, verifier);
//        System.out.println("Got the Access Token!");
//        System.out.println("(if your curious it looks like this: " + accessToken + " )");
//        System.out.println();
//
//        // Now let's go and ask for a protected resource!
//        System.out.println("Now we're going to access a protected resource...");
//        OAuthRequest request = new OAuthRequest(Verb.GET, protectedResourceUrl);
//        service.signRequest(accessToken, request);
//        Response response = request.send();
//        System.out.println("Got it! Lets see what we found...");
//        System.out.println();
//        System.out.println(response.getBody());
//
//        System.out.println();
//        System.out.println("Thats it man! Go and build something awesome with Scribe! :)");
    }
}
