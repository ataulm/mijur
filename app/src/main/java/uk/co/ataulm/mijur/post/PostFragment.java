package uk.co.ataulm.mijur.post;

import android.os.Bundle;
import android.text.TextUtils;
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
    private TextView descView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_post, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageView = Views.findById(view, R.id.image);
        captionView = Views.findById(view, R.id.caption);
        descView = Views.findById(view, R.id.description);

        PostUpdater postUpdater = new PostUpdater(imageView, captionView, descView);
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
        private final TextView descView;

        PostUpdater(ImageView imageView, TextView captionView, TextView descView) {
            this.imageView = imageView;
            this.captionView = captionView;
            this.descView = descView;
        }

        @Override
        public void onGalleryItemLoaded(GalleryItem galleryItem) {
            Matisse.load(galleryItem.link, imageView);
            captionView.setText(galleryItem.title);

            if (!TextUtils.isEmpty(galleryItem.description)) {
                descView.setText(galleryItem.description);
                descView.setVisibility(View.VISIBLE);
            }
        }

    }

}
