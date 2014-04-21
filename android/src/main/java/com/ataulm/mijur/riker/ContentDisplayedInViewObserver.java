package com.ataulm.mijur.riker;

import android.view.View;

import com.novoda.notils.logger.simple.Log;
import com.novoda.notils.logger.toast.Toaster;

import rx.Observer;

public class ContentDisplayedInViewObserver implements Observer<View> {

    private final View view;

    public ContentDisplayedInViewObserver(View view) {
        this.view = view;
    }

    @Override
    public void onCompleted() {
    }

    @Override
    public void onError(Throwable e) {
        Log.e("Failed to display content in view", e);
        new Toaster(view.getContext()).popToast("well, poop");
    }

    @Override
    public void onNext(View view) {
        Log.v("Displayed content in view with ID: " + view.getId());
    }

}
