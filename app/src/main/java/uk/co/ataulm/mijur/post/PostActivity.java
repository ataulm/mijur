package uk.co.ataulm.mijur.post;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.novoda.notils.caster.Views;

import uk.co.ataulm.mijur.Matisse;
import uk.co.ataulm.mijur.R;
import uk.co.ataulm.mijur.base.MijurActivity;
import uk.co.ataulm.mijur.model.GalleryItem;

public class PostActivity extends MijurActivity {

    private ImageView imageView;
    private TextView captionView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        findViews();

        PostUpdater postUpdater = new PostUpdater(imageView, captionView);
        String postId = getIntent().getData().getLastPathSegment();
        PostLoaderCallbacks loaderCallbacks = new PostLoaderCallbacks(this, postId, postUpdater);

        if (savedInstanceState == null) {
            getLoaderManager().initLoader(R.id.loader_post_cursor, null, loaderCallbacks);
        } else {
            getLoaderManager().initLoader(R.id.loader_post_cursor, null, loaderCallbacks);
        }
    }

    private void findViews() {
        imageView = Views.findById(this, R.id.image);
        captionView = Views.findById(this, R.id.caption);
    }

    private static class PostUpdater implements GalleryItemLoadedCallback {

        private final ImageView imageView;
        private final TextView captionView;

        PostUpdater(ImageView imageView, TextView captionView) {
            this.imageView = imageView;
            this.captionView = captionView;
        }

        @Override
        public void onGalleryItemLoaded(GalleryItem galleryItem) {
            Matisse.load(galleryItem.link, imageView);
            captionView.setText(galleryItem.title);
        }
    }

}
