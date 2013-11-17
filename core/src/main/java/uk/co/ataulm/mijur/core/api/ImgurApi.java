package uk.co.ataulm.mijur.core.api;

import retrofit.RestAdapter;

public class ImgurApi {
    private static final String API_ENDPOINT = "https://api.imgur.com/oauth2/";
    private final RestAdapter restAdapter;

    public ImgurApi() {
        // Create a very simple REST adapter which points the GitHub API endpoint.
        restAdapter = new RestAdapter.Builder()
                .setServer(API_ENDPOINT)
                .build();

//        // Create an instance of our GitHub API interface.
//        GitHub github = restAdapter.create(GitHub.class);
//
//        // Fetch and print a list of the contributors to this library.
//        List<Contributor> contributors = github.contributors("square", "retrofit");
//        for (Contributor contributor : contributors) {
//            System.out.println(contributor.login + " (" + contributor.contributions + ")");
//        }
    }
}
