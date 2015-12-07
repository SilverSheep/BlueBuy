package pl.cba.marcinbaranowski.bluebuy.adapter;

import android.widget.BaseAdapter;

/**
 * Created by flipflap on 23.11.15.
 */
public abstract class MyListAdapter<T> extends BaseAdapter {

    public abstract void add(T item);

    public abstract void remove(T item);
}
