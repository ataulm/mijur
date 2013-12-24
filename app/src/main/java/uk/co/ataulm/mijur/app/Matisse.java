package uk.co.ataulm.mijur.app;

import android.content.Context;
import android.widget.ImageView;

import com.novoda.imageloader.core.ImageManager;
import com.novoda.imageloader.core.LoaderSettings;
import com.novoda.imageloader.core.model.ImageTag;
import com.novoda.imageloader.core.model.ImageTagFactory;

public class Matisse {

    private static Matisse matisse;

    private ImageManager imageManager;
    private Context context;

    private Matisse(Context context) {
        this.context = context;
    }

    /**
     * Loads the image at the given URL into the specified ImageView.
     *
     * This is all I care about.
     *
     * @param url
     * @param view
     */
    public static void load(String url, ImageView view) {
        with(view.getContext()).loadUrlInView(url, view);
    }

    /**
     * Gets the ImageLoader with the Context passed.
     *
     * @param context the Context from the caller
     * @return matisse the ImageLoader with the Context set
     */
    private static Matisse with(Context context) {
        if (matisse == null) {
            matisse = new Matisse(context);
        }
        return matisse;
    }

    /**
     * Loads the bitmap at the url into the ImageView.
     *
     * @param url  the url where the image is
     * @param view the ImageView into which the image should be loaded
     */
    private void loadUrlInView(String url, ImageView view) {
        setupImageManager();

        ImageTagFactory factory = ImageTagFactory.newInstance();
        factory.setWidth(500);
        factory.setHeight(500);
        ImageTag tag = factory.build(url, context);
        view.setTag(tag);

        imageManager.getLoader().load(view);
    }

    private void setupImageManager() {
        if (imageManager == null) {
            imageManager = new ImageManager(context, getDefaultLoaderSettings());
        }
    }

    private LoaderSettings getDefaultLoaderSettings() {
        return new LoaderSettings.SettingsBuilder().build(context);
    }
}
