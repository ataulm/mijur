package com.ataulm.mijur.gallery;

import android.os.Bundle;

import com.ataulm.mijur.BuildConfig;
import com.ataulm.mijur.R;
import com.ataulm.mijur.base.android.MijurActivity;
import com.ataulm.mijur.data.Comment;
import com.ataulm.mijur.data.CommentsProvider;
import com.ataulm.mijur.data.GalleryItem;
import com.ataulm.mijur.data.GalleryProvider;
import com.ataulm.mijur.view.PostView;
import com.novoda.notils.caster.Views;
import com.novoda.notils.logger.simple.Log;

import java.util.List;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class PostActivity extends MijurActivity {

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

    private static class PostCommentsUpdater implements Observer<List<Comment>> {

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
        public void onNext(List<Comment> comments) {
            view.update(comments);
        }

    }

}
