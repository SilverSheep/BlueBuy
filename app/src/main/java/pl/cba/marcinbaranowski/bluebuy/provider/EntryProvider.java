package pl.cba.marcinbaranowski.bluebuy.provider;

import java.util.ArrayList;
import java.util.List;

import pl.cba.marcinbaranowski.bluebuy.model.Entry;

public class EntryProvider {

    private static final List<Entry> ENTRIES = new ArrayList<>();

    //TODO: Sort by categories alphabetically
    public EntryProvider() {
        ENTRIES.clear();
        ENTRIES.add(new Entry("mąka"));
        ENTRIES.add(new Entry("proszek do prania"));
        ENTRIES.add(new Entry("gra"));
    }

    public Entry getEntry(int position) {
        return ENTRIES.get(position);
    }

    public int getEntriesSize() {
        return ENTRIES.size();
    }

    public void addEntry(Entry entry) {
        ENTRIES.add(entry);
    }

    public void removeEntry(Entry entry) {
        ENTRIES.remove(entry);
    }

}
