package uk.co.ataulm.mijur.post;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;

import java.util.List;

import uk.co.ataulm.mijur.model.GalleryItem;

public class PostPagerAdapter extends FragmentStatePagerAdapter {

    private final List<GalleryItem> items;

    public PostPagerAdapter(FragmentManager fragmentManager, List<GalleryItem> items) {
        super(fragmentManager);
        this.items = items;
    }

    @Override
    public Fragment getItem(int position) {
        return PostFragment.newInstance(items.get(position));
    }

    @Override
    public int getCount() {
        return items.size();
    }

}
