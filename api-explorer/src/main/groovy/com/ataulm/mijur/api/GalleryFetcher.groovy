package com.ataulm.mijur.api

import com.ataulm.mijur.data.Album
import com.ataulm.mijur.data.Gallery
import com.ataulm.mijur.data.GalleryItem
import com.ataulm.mijur.data.parser.GalleryParser
import groovy.json.JsonBuilder
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method

public class GalleryFetcher {

    private static final String CLIENT_ID = '4ecc93234f517fd'

    public static void main(String... args) {
        GalleryFetcher fetcher = new GalleryFetcher()
        Gallery gallery = fetcher.fetchGallery()

        if (gallery != Gallery.empty()) {
            fetcher.processGalleryItems(gallery)
        }
    }

    private static Gallery fetchGallery() {
        HTTPBuilder http = new HTTPBuilder('https://api.imgur.com/3/gallery.json')
        http.request(Method.GET) {
            headers['Authorization'] = 'Client-ID ' + CLIENT_ID
            response.success = { resp, json ->
                def formattedJson = prettifyJson(json)
                writeJsonToFile('gallery', formattedJson)
                return parseGallery(formattedJson)
            }

            response.failure = { resp, json ->
                return Gallery.empty()
            }
        }
    }

    private static void fetchAlbum(String id) {
        HTTPBuilder http = new HTTPBuilder('https://api.imgur.com/3/album/' + id)
        http.request(Method.GET) {
            headers['Authorization'] = 'Client-ID ' + CLIENT_ID
            response.success = { resp, json ->
                def formattedJson = prettifyJson(json)
                writeJsonToFile(id, formattedJson)
            }

            response.failure = { resp, json ->
                println(resp, "FAILURE")
            }
        }
    }

    private static void fetchCommentsForPost(String id) {
        HTTPBuilder http = new HTTPBuilder('https://api.imgur.com/3/gallery/' + id + '/comments')
        http.request(Method.GET) {
            headers['Authorization'] = 'Client-ID ' + CLIENT_ID
            response.success = { resp, json ->
                def formattedJson = prettifyJson(json)
                writeJsonToFile(id + '_comments', formattedJson)
            }

            response.failure = { resp, json ->
                println(resp, "FAILURE")
            }
        }
    }

    private static Gallery parseGallery(String json) {
        GalleryParser parser = GalleryParser.newInstance()
        parser.parse(json.toString())
    }

    private static void processGalleryItems(Gallery gallery) {
        for (GalleryItem item : gallery) {
            fetchCommentsForPost(item.id)
            if (item instanceof Album) {
                fetchAlbum(item.id)
            }
        }
    }

    private static void writeJsonToFile(String fileId, String json) {
        new File("${System.getProperty('user.dir')}/android/src/main/assets/mocks/" + fileId + ".json").write(json)
    }

    private static String prettifyJson(json) {
        new JsonBuilder(json).toPrettyString()
    }

}
