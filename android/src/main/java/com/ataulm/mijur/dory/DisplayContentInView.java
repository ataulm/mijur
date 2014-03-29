package com.ataulm.mijur.dory;

import android.view.View;

import rx.functions.Func1;

class DisplayContentInView<T, U extends View> implements Func1<T, U> {

    private final Displayer<T, U> displayer;
    private final U view;

    public DisplayContentInView(Displayer<T, U> displayer, U view) {
        this.displayer = displayer;
        this.view = view;
    }

    @Override
    public U call(T content) {
        displayer.display(content, view);
        return view;
    }

}
