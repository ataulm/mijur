package uk.co.ataulm.mijur.post;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.novoda.notils.caster.Classes;
import com.novoda.notils.caster.Views;

import java.util.List;

import uk.co.ataulm.mijur.R;
import uk.co.ataulm.mijur.base.MijurActivity;
import uk.co.ataulm.mijur.model.GalleryItem;

public class PostActivity extends MijurActivity {

    public static final String EXTRA_GALLERY_ITEMS = "uk.co.ataulm.mijur.extra.GALLERY_ITEMS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        List<GalleryItem> galleryItems = Classes.from(getIntent().getParcelableArrayListExtra(EXTRA_GALLERY_ITEMS));
        PostPagerAdapter adapter = new PostPagerAdapter(getFragmentManager(), galleryItems);
        String postId = getIntent().getData().getLastPathSegment();

        ViewPager posts = Views.findById(this, R.id.posts);
        posts.setAdapter(adapter);

        posts.setCurrentItem(0);
    }

}
