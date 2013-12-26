CREATE TABLE IF NOT EXISTS 'GALLERY_ITEM'
    (_id TEXT PRIMARY KEY UNIQUE,
    title TEXT,
    description TEXT,
    datetime TEXT,
    views INTEGER,
    link TEXT,
    account_url TEXT,
    ups INTEGER,
    downs INTEGER,
    score INTEGER,
    is_album INTEGER,
    cover TEXT,
    layout TEXT,
    images_count INTEGER,
    type TEXT,
    animated INTEGER,
    width INTEGER,
    height INTEGER,
    size INTEGER,
    bandwidth INTEGER,
    deletehash TEXT,
    lastSynced TEXT);



--        id,                     // String, id of the image/album (unique primary key)
--        title,                  // String, caption
--        description,            // String, description
--        datetime,               // long, time submitted to gallery (epoch)
--        views,                  // int, number of views
--        link,                   // String, url to image (direct), or album (webpage)
--        account_url,            // String, username of uploader's account
--        ups,                    // int, upvotes
--        downs,                  // int, downvotes
--        score,                  // int, net votes
--        is_album,               // boolean
--        cover,                  // String, id of the cover image if this is album (else null)
--        layout,                 // String, view layout of album (if album)
--        images_count,           // int, number of images in album (if album)
--        type,                   // String, image mime type
--        animated,               // boolean
--        width,                  // int, pixels
--        height,                 // int, pixels
--        size,                   // long, bytes
--        bandwidth,              // long, bytes
--        deletehash,             // String, if you're logged in as image owner