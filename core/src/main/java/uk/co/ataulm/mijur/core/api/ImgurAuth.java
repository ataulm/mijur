package uk.co.ataulm.mijur.core.api;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.DefaultApi10a;
import org.scribe.model.Token;
import org.scribe.oauth.OAuthService;

public class ImgurAuth extends DefaultApi10a {
    public static final String AUTHORISATION_URL = "https://api.imgur.com/oauth2/token";

    private static ImgurAuth imgurAuth;

    private final OAuthService service;

    private ImgurAuth(OAuthService service) {
        this.service = service;
    }

    public static ImgurAuth getInstance(OAuthService service) {
        if (imgurAuth == null) {
            imgurAuth = new ImgurAuth(service);
        }
        return imgurAuth;
    }

    @Override
    public String getRequestTokenEndpoint() {
        // TODO: add request token endpoint
        return null;
    }

    @Override
    public String getAccessTokenEndpoint() {
        // TODO: add access token endpoint
        return null;
    }

    @Override
    public String getAuthorizationUrl(Token requestToken) {
        // TODO: add auth url
        return null;
    }

    private Token getRequestToken() {
        return service.getRequestToken();
    }

    /**
     * Working out the authentication workflow for Imgur.
     *
     * @param args the command line arguments, ignored
     */
    public static void main(String... args) {
        OAuthService service = new ServiceBuilder()
                .apiKey(ApiConstants.API_CLIENT_ID)
                .apiSecret(ApiConstants.API_CLIENT_SECRET)
                .provider(ImgurAuth.class)
                .build();

        ImgurAuth api = ImgurAuth.getInstance(service);

        Token requestToken = api.getRequestToken();
        String authorisationUrl = api.getAuthorizationUrl(requestToken);

        // TODO: continue with the workflow
    }
}
