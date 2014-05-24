package com.ataulm.mijur.data.parser;

import com.ataulm.mijur.data.GalleryItemCore;
import com.ataulm.mijur.data.Image;
import com.ataulm.mijur.data.Time;
import com.google.gson.Gson;

public class ImageParser {

    private final Gson gson;

    private ImageParser(Gson gson) {
        this.gson = gson;
    }

    public static ImageParser newInstance() {
        return new ImageParser(new Gson());
    }

    public Image parse(GsonImage gsonImage) {
        GalleryItemCore core = parseCore(gsonImage);
        boolean animated = gsonImage.animated;
        int width = gsonImage.width;
        int height = gsonImage.height;
        return new Image(core, animated, width, height);
    }

    private GalleryItemCore parseCore(GsonImage gsonImage) {
        return new GalleryItemCore(gsonImage.id,
                gsonImage.title,
                gsonImage.description,
                new Time(gsonImage.datetime),
                gsonImage.link);
    }

}
