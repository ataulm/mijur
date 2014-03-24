package com.ataulm.mijur.view;

public enum FontType {

    ROBOTO_SLAB_LIGHT("fonts/RobotoSlab-Light.ttf"),
    ROBOTO_SLAB_BOLD("fonts/RobotoSlab-Bold.ttf");

    public final String assetUrl;

    FontType(String assetUrl) {
        this.assetUrl = assetUrl;
    }

}
