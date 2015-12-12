package pl.cba.marcinbaranowski.bluebuy.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import pl.cba.marcinbaranowski.bluebuy.model.Entry;
import pl.cba.marcinbaranowski.bluebuy.provider.EntryProvider;

/**
 * Created by flipflap on 26.10.15.
 */

public class EntryListAdapter extends BaseAdapter {

    private EntryProvider entryProvider;
    private Context context;

    public EntryListAdapter(Context context) {
        this.context = context;
        entryProvider = new EntryProvider(context);
    }

    @Override
    public int getCount() {
        return entryProvider.getEntriesSize();
    }

    @Override
    public Entry getItem(int i) {
        return entryProvider.getEntry(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void addEntry(Entry entry) {
        entryProvider.addEntry(entry);
    }

    public void updateEntry(Entry entry) {
        entryProvider.updateEntry(entry);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }

}
