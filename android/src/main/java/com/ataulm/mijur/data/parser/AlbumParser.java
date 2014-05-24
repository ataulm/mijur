package com.ataulm.mijur.data.parser;

import com.ataulm.mijur.data.*;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class AlbumParser {

    private final Gson gson;
    private final ImageParser imageParser;

    private AlbumParser(Gson gson, ImageParser imageParser) {
        this.gson = gson;
        this.imageParser = imageParser;
    }

    public static AlbumParser newInstance() {
        return new AlbumParser(new Gson(), ImageParser.newInstance());
    }

    public Album parse(InputStream stream) {
        JsonReader reader = new JsonReader(new InputStreamReader(stream));
        GsonAlbumResponse gsonAlbumResponse = gson.fromJson(reader, GsonAlbumResponse.class);
        return parse(gsonAlbumResponse);
    }

    private Album parse(GsonAlbumResponse gsonAlbumResponse) {
        if (gsonAlbumResponse.success) {
            GalleryItemCommon core = parseGalleryItemCore(gsonAlbumResponse.data);
            Album.Cover cover = Album.Cover.none();
            List<Image> images = parseImages(gsonAlbumResponse);
            return Album.newInstance(core, cover, new Images(images));
        } else {
            return Album.empty();
        }
    }

    private GalleryItemCommon parseGalleryItemCore(GsonAlbum gsonAlbum) {
        return new GalleryItemCommon(gsonAlbum.id,
                gsonAlbum.title,
                gsonAlbum.description,
                new Time(gsonAlbum.images.get(0).datetime),
                "");
    }

    private List<Image> parseImages(GsonAlbumResponse gsonAlbumResponse) {
        List<Image> images = new ArrayList<Image>(gsonAlbumResponse.data.images.size());
        for (GsonImage gsonImage : gsonAlbumResponse.data.images) {
            Image image = imageParser.parse(gsonImage);
            images.add(image);
        }
        return images;
    }

}
