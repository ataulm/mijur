package com.ataulm.mijur.dory;

import android.view.View;

import rx.Observable;

public interface Dory<T, U extends View> {

    /**
     * Returns a byte array of the content identified by the url.
     *
     * The url may be the content's location on the internet, or may
     * be a key to retrieve content from a disk or in-memory cache.
     *
     * @param url identifier to locate the content
     * @return byte array representing content
     */
    Observable<byte[]> fetch(String url);

    /**
     * Presents the content in the View.
     *
     * @param content the content to display
     * @param view the View in which to display the content
     * @return a reference to the View
     */
    Observable<U> display(T content, U view);

    /**
     * Presents the content in the View.
     *
     * No fuss, just load the content, nothing special.
     *
     * @param url identifier to locate the content
     * @param view the View in which to display the content
     * @return a reference to the View
     */
    Observable<U> load(String url, U view);

}
