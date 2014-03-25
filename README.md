# mĭjur
An Android client for Imgur. I'm using this as a project to learn about REST, custom UI components in Android, as well as UX/UI design for the Android and mobile platform in general.

I expect pull requests will be welcome after 1.0, but comments and crit are welcome and appreciated at any time.

## Imgur
[Imgur][imgur] is the go-to destination for viral images. Backed by a friendly and funny community, Imgur is the best place to spend your free time online. 

Users can interact with images by commenting and voting, or submitting their own image. The uploader makes sharing images with the Internet simple. You can use Imgur to share an image or album with a friend or post on a message board, blog or social networking site. You can manipulate the image to your liking and automatically share it with your social network of choice. Best of all, Imgur is completely free.

Read the rest of the [FAQ][imgur-faq].

[imgur]: http://imgur.com
[imgur-faq]: http://imgur.com/help

## Building
Should build out of the box now (`./gradlew clean build`). If you want to test using the real API, add `gradle.properties` in the root directory, and set your Imgur client ID:

    // gradle.properties
    IMGUR_CLIENT_ID=YOUR_CLIENT_ID

## Resources
Uses open-source libraries, with thanks:

- Novoda's [ImageLoader][link-imageloader]
- Novoda's [NoTils][link-notils]
- Netflix's [RxJava (alpha)][link-rxjava]
- Square's [Retrofit][link-retrofit]

[link-imageloader]: https://github.com/novoda/ImageLoader
[link-notils]: https://github.com/novoda/NoTils
[link-rxjava]: https://github.com/Netflix/RxJava
[link-retrofit]: http://square.github.io/retrofit/
