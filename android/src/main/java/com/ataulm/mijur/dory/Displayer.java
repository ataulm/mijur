package com.ataulm.mijur.dory;

import android.view.View;

interface Displayer<T, U extends View> {

    void display(T content, U view);

}
