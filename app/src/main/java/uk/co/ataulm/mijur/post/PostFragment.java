package uk.co.ataulm.mijur.post;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.novoda.notils.caster.Views;

import uk.co.ataulm.mijur.Matisse;
import uk.co.ataulm.mijur.R;
import uk.co.ataulm.mijur.base.MijurFragment;
import uk.co.ataulm.mijur.model.GalleryItem;

public class PostFragment extends MijurFragment {

    private ImageView imageView;
    private TextView captionView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_post, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageView = Views.findById(view, R.id.image);
        captionView = Views.findById(view, R.id.caption);

        PostUpdater postUpdater = new PostUpdater(imageView, captionView);
        String postId = getActivity().getIntent().getData().getLastPathSegment();
        PostLoaderCallbacks loaderCallbacks = new PostLoaderCallbacks(getActivity(), postId, postUpdater);

        if (savedInstanceState == null) {
            getLoaderManager().initLoader(R.id.loader_post_cursor, null, loaderCallbacks);
        } else {
            getLoaderManager().initLoader(R.id.loader_post_cursor, null, loaderCallbacks);
        }
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
