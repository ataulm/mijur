package uk.co.ataulm.mijur.app;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.Collections;
import java.util.List;

public abstract class MijurListAdapter<T> extends BaseAdapter {

    private List<T> data = Collections.emptyList();

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public T getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public abstract View getView(int position, View convertView, ViewGroup parent);

    public void swapData(List<T> data) {
        this.data = data;
        notifyDataSetChanged();
    }

}
