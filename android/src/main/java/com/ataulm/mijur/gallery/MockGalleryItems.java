package com.ataulm.mijur.gallery;

import java.util.ArrayList;

/**
 * List of 10 GalleryItems with urls to 160px thumbs.
 */
public class MockGalleryItems extends ArrayList<GalleryItem> {

    private static MockGalleryItems instance;

    private MockGalleryItems() {
        super(10);
        // 0
        add(GalleryItem.from("\"Ohana means family\"",
                "http://www.disneyfineart.com/artists.php?aID=575theurer&p=3",
                "http://i.imgur.com/qXLPdZ1t.jpg"));

        // 1
        add(GalleryItem.from("Meet Pretzel, my sister's new dog.",
                "http://i.imgur.com/DL2D2tDt.jpg"));

        // 2
        add(GalleryItem.from("I took this 911 call last night. I felt kind of bad for the kid.",
                "http://i.imgur.com/6UAaYXMt.jpg"));

        // 3
        add(GalleryItem.from("What my childhood was missing!",
                "http://i.imgur.com/PRZrFG9t.jpg"));

        // 4
        add(GalleryItem.from("I regret nothing",
                "His wife called me a few days later telling me that she filed for a divorce and thanking me, aaaaaand I got fired from work.\n" +
                        "\n" +
                        "===========\n" +
                        "\n" +
                        "First page with this post, are you fucking kidding me?\n" +
                        "\n" +
                        "Thanks for those who warned me that I should sue him ASAP or I will not get anything, I am looking for a lawyer now.\n" +
                        "\n" +
                        "And for those wondering why I did it this way, I wanted the wife to investigate by herself rather than getting involved deeply with this which I think will happen if I told her directly",
                "http://i.imgur.com/tBG20BSt.png"
        ));

        // 5
        add(GalleryItem.from("I would like to thank all of the power strip makers who do this.",
                "http://i.imgur.com/lP8ml3yt.jpg"));

        // 6
        add(GalleryItem.from("James Franco posted this on FB yesterday.",
                "http://i.imgur.com/tBGMk00t.jpg"));

        // 7
        add(GalleryItem.from("The Best Part of Pokemon Battle.",
                "http://i.imgur.com/wIOIPCvt.jpg"));

        // 8
        add(GalleryItem.from("Hipster VHS",
                "http://tooyoungtoremembervhs.com",
                "http://i.imgur.com/eteftugt.png"));

        // 9
        add(GalleryItem.from("This is why I can't sleep in on weekends.",
                "http://i.imgur.com/VFbKXIlt.jpg"));
    }

    public static MockGalleryItems getInstance() {
        if (instance == null) {
            instance = new MockGalleryItems();
        }
        return instance;
    }

}
