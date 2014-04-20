package com.ataulm.mijur.dory;

import android.view.View;

public interface Displayer<T, U extends View> {

    /**
     * Displays the content in the View.
     */
    void display(T content, U view);

}
