package uk.co.ataulm.mija.core;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.DefaultApi10a;
import org.scribe.model.Token;
import org.scribe.oauth.OAuthService;

import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Path;
import uk.co.ataulm.mija.core.api.ApiConstants;
import uk.co.ataulm.mija.core.datamodel.Image;

public class ImgurApi extends DefaultApi10a {



    @Override
    public String getRequestTokenEndpoint() {
        return "https://api.imgur.com/oauth2/request_token";
    }

    @Override
    public String getAccessTokenEndpoint() {
        return "https://api.imgur.com/oauth2/access_token";
    }

    @Override
    public String getAuthorizationUrl(Token requestToken) {
        return String.format("https://api.imgur.com/oauth2/authorize?oauth_token=%s", requestToken.getToken());
    }











    interface Api {
        static final String API_URL = "https://api.imgur.com/3/";
        static final String CLIENT_ID = ApiConstants.API_CLIENT_ID;
        static final String CLIENT_SECRET = ApiConstants.API_CLIENT_SECRET;

        @GET("/image/{id}")
        Image image(@Path("id") String id);
    }

    public static void main(String... args) {
        RestAdapter restAdapter = new RestAdapter.Builder().setServer(Api.API_URL).build();

        Api imgur = restAdapter.create(Api.class);
        Image returnedImage = imgur.image("CoSS30e");

        OAuthService service = new ServiceBuilder()
                .provider(ImgurApi.class)
                .apiKey(ImgurApi.Api.CLIENT_ID)
                .apiSecret(ImgurApi.Api.CLIENT_SECRET)
                .build();

        service.getRequestToken();

    }
}
