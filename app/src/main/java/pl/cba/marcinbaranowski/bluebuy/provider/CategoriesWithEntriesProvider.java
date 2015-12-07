package pl.cba.marcinbaranowski.bluebuy.provider;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import pl.cba.marcinbaranowski.bluebuy.model.Category;
import pl.cba.marcinbaranowski.bluebuy.model.CategoryWithEntries;
import pl.cba.marcinbaranowski.bluebuy.model.Entry;
import pl.cba.marcinbaranowski.bluebuy.model.Unit;

/**
 * Created by flipflap on 06.12.15.
 */
public class CategoriesWithEntriesProvider {

    private static final List<CategoryWithEntries> CATEGORIES_WITH_ENTRIES = new ArrayList<>();

    private Context context;

    public CategoriesWithEntriesProvider(Context context) {
        this.context = context;
        prepareCategoriesWithEntries();
    }

    public CategoryWithEntries getCategory(int position) {
        return CATEGORIES_WITH_ENTRIES.get(position);
    }

    public int getCategoriesSize() {
        return CATEGORIES_WITH_ENTRIES.size();
    }

    public void addCategory(CategoryWithEntries category) {
        CATEGORIES_WITH_ENTRIES.add(category);
    }

    public void removeCategory(CategoryWithEntries category) {
        CATEGORIES_WITH_ENTRIES.remove(category);
    }

    private void prepareCategoriesWithEntries() {
        CategoryProvider categoryProvider = new CategoryProvider(context);
        EntryProvider entryProvider = new EntryProvider();

        for (Category category:
                categoryProvider.getCategories()) {

            List<Entry> entries = new ArrayList<>();

            for (Entry entry :
                    entryProvider.getEntries()) {

                Category entryCategory = entry.getCategory();

                if (category.getName().equals(entryCategory.getName())) {
                    entries.add(entry);
                }
            }
            addCategory(new CategoryWithEntries(category.getName(), entries));
        }
    }

    private class EntryProvider {

        private final List<Entry> ENTRIES = new ArrayList<>();

        //TODO: Sort by categories alphabetically
        public EntryProvider() {
            ENTRIES.clear();
            ENTRIES.add(new Entry(new Category("Pieczywo"), "mąka"));
            ENTRIES.add(new Entry(new Category("Chemia"), "proszek do prania"));
            ENTRIES.add(new Entry(new Category("AGD i RTV"), "gra"));
        }

        public Entry getEntry(int position) {
            return ENTRIES.get(position);
        }

        public List<Entry> getEntries() {
            return ENTRIES;
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


    private static class UnitProvider {

        private static final List<Unit> UNITS = new ArrayList<>();

        //TODO: Sort by categories alphabetically
        public UnitProvider() {
            UNITS.clear();
            UNITS.add(new Unit("szt."));
            UNITS.add(new Unit("kg"));
            UNITS.add(new Unit("l"));
        }

        public Unit getUnit(int position) {
            return UNITS.get(position);
        }

        public int getUnitsSize() {
            return UNITS.size();
        }

    }
}
