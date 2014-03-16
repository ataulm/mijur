package uk.co.ataulm.mijur.base;

import android.widget.BaseAdapter;

import java.io.Closeable;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public abstract class MijurAdapter<T> extends BaseAdapter {

    private List<T> items;

    protected MijurAdapter() {
        this.items = Collections.emptyList();
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public T getItem(int position) {
        return items.get(position);
    }

    public void swapList(List<T> items) {
        closeListIfNecessary();
        this.items = items;
        notifyDataSetChanged();
    }

    public void reset() {
        swapList(Collections.<T>emptyList());
    }

    private void closeListIfNecessary() {
        if (items instanceof Closeable) {
            try {
                ((Closeable) items).close();
            } catch (IOException ignored) {
                ignored.printStackTrace();
            }

        }
    }

}
