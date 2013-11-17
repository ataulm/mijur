package uk.co.ataulm.mijur.core.api;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.DefaultApi10a;
import org.scribe.model.Token;
import org.scribe.oauth.OAuthService;

public class ImgurApi extends DefaultApi10a {
    public static final String AUTHORISATION_URL = "https://api.imgur.com/oauth2/token";

    private static ImgurApi api;

    private final OAuthService service;

    private ImgurApi(OAuthService service) {
        this.service = service;
    }

    public static ImgurApi getInstance(OAuthService service) {
        if (api != null) {
            return api;
        }
        return new ImgurApi(service);
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
        // requestToken.getToken();
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
                .provider(ImgurApi.class)
                .build();

        ImgurApi api = ImgurApi.getInstance(service);

        Token requestToken = api.getRequestToken();
        String authorisationUrl = api.getAuthorizationUrl(requestToken);
    }
}
