package com.ataulm.mijur.gallery;

import android.content.Intent;
import android.os.Bundle;

import com.ataulm.mijur.BuildConfig;
import com.ataulm.mijur.R;
import com.ataulm.mijur.base.android.MijurActivity;
import com.ataulm.mijur.data.*;
import com.ataulm.mijur.view.PostView;
import com.novoda.notils.caster.Views;
import com.novoda.notils.logger.simple.Log;
import com.novoda.notils.logger.toast.Toaster;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class PostActivity extends MijurActivity implements PostView.ClickListener {

    public static final String EXTRA_POST_ID = BuildConfig.PACKAGE_NAME + ".EXTRA_POST_ID";

    private Subscription gallerySubscription;
    private Subscription commentsSubscription;
    private PostContentUpdater postContentUpdater;
    private PostCommentsUpdater postCommentsUpdater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        PostView post = Views.findById(this, R.id.post);
        post.setPostViewClickListener(this);
        postContentUpdater = new PostContentUpdater(post);
        postCommentsUpdater = new PostCommentsUpdater(post);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String postId = getIntent().getStringExtra(EXTRA_POST_ID);

        gallerySubscription = GalleryProvider.instance().getGalleryItem(postId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(postContentUpdater);

        commentsSubscription = CommentsProvider.instance().getTopLevelCommentsForPost(postId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(postCommentsUpdater);
    }

    @Override
    protected void onPause() {
        super.onPause();
        gallerySubscription.unsubscribe();
        commentsSubscription.unsubscribe();
    }

    @Override
    public void onImageAlbumClick(Album album, Image image) {
        new Toaster(this).popToast(album.getId() + ":" + image.getId());
    }

    @Override
    public void onImageClick(Image image) {
        Intent intent = new Intent(this, ImmersivePostActivity.class);
        intent.putExtra(ImmersivePostActivity.EXTRA_POST_ID, image.getId());
        startActivity(intent);
    }

    private static class PostContentUpdater implements Observer<GalleryItem> {

        private final PostView view;

        private PostContentUpdater(PostView view) {
            this.view = view;
        }

        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            Log.e("PostContentUpdater", e);
        }

        @Override
        public void onNext(GalleryItem galleryItem) {
            view.update(galleryItem);
        }

    }

    private static class PostCommentsUpdater implements Observer<Comments> {

        private final PostView view;

        private PostCommentsUpdater(PostView view) {
            this.view = view;
        }

        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            Log.e("PostCommentUpdater", e);
        }

        @Override
        public void onNext(Comments comments) {
            view.update(comments);
        }

    }

}
