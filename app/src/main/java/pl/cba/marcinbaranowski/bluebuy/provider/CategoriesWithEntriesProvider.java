package pl.cba.marcinbaranowski.bluebuy.provider;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import pl.cba.marcinbaranowski.bluebuy.model.Category;
import pl.cba.marcinbaranowski.bluebuy.model.CategoryType;
import pl.cba.marcinbaranowski.bluebuy.model.CategoryWithEntries;
import pl.cba.marcinbaranowski.bluebuy.model.Entry;

/**
 * Created by flipflap on 06.12.15.
 */
public class CategoriesWithEntriesProvider {

    private static final List<CategoryWithEntries> CATEGORIES_WITH_ENTRIES = new ArrayList<>();

    private Context context;
    CategoryProvider categoryProvider;
    EntryProvider entryProvider;

    public CategoriesWithEntriesProvider(Context context) {
        this.context = context;
        categoryProvider = new CategoryProvider(context);
        entryProvider = new EntryProvider(context);
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

    public void removeCategory(Category category) {
        for (int i = 0; i < CATEGORIES_WITH_ENTRIES.size(); ++i) {
            CategoryWithEntries categoryWithEntries = CATEGORIES_WITH_ENTRIES.get(i);
            if (categoryWithEntries.getCategory().getName().equals(category.getName())) {
                moveEntriesToOthers(categoryWithEntries.getEntries());
                break;
            }
        }
        categoryProvider.removeCategory(category);
    }

    private void moveEntriesToOthers(List<Entry> entries) {
        for (Entry entry :
                entries) {
            moveEntryToOthers(entry);
        }
    }

    private void moveEntryToOthers(Entry entry) {
        List<Category> categories = categoryProvider.getCategories();
        for (int i=0; i < categories.size(); ++i) {
            Category category = categories.get(i);
            if (category.getName().equals("inne")) {
                entry.setCategory(category);
                entry.setRecentCategory(category);
                entryProvider.updateEntry(entry);
            }
        }
    }

    public void removeEntry(int categoryPosition, Entry entry) {
        CATEGORIES_WITH_ENTRIES.get(categoryPosition).getEntries().remove(entry);

        entryProvider.removeEntry(entry);
    }

    public void refreshList() {
        prepareCategoriesWithEntries();
    }

    public void resetList() {
        for (int i = 0; i < CATEGORIES_WITH_ENTRIES.size(); ++i) {
            List<Entry> entries = CATEGORIES_WITH_ENTRIES.get(i).getEntries();
            for (int j = 0; j < entries.size(); ++j) {
                Entry entry = entries.get(j);
                moveBackToOriginalCategory(entry);
            }
        }
        refreshList();
    }

    public void moveAllToBasket() {
        for (int i = 0; i < CATEGORIES_WITH_ENTRIES.size(); ++i) {
            List<Entry> entries = CATEGORIES_WITH_ENTRIES.get(i).getEntries();
            for (int j = 0; j < entries.size(); ++j) {
                Entry entry = entries.get(j);
                moveToBasket(entry);
            }
        }
        refreshList();
    }

    public void moveToBasket(Entry entry) {
        entry.setRecentCategory(entry.getCategory());
        entry.setCategory(getBasket());
        entryProvider.updateEntry(entry);
    }

    public void moveBackToOriginalCategory(Entry entry) {
        if (entry.getRecentCategory() != null) {
            entry.setCategory(entry.getRecentCategory());
            entryProvider.updateEntry(entry);
        }
    }

    private Category getBasket() {
        for (int i = 0; i < CATEGORIES_WITH_ENTRIES.size(); ++i) {
            CategoryWithEntries categoryWithEntries = CATEGORIES_WITH_ENTRIES.get(i);

            Category category = categoryWithEntries.getCategory();
            if (category.getType().equals(CategoryType.BASKET)) {
                return category;
            }
        }

        return null;
    }

    private void prepareCategoriesWithEntries() {
        List<Entry> entries = entryProvider.getEntries();

        CATEGORIES_WITH_ENTRIES.clear();

        for (Category category :
                categoryProvider.getCategories()) {

            List<Entry> categoryEntries = new ArrayList<>();

            for (Entry entry :
                    entries) {
                Category entryCategory = entry.getCategory();

                if (category.getName().equals(entryCategory.getName())) {
                    categoryEntries.add(entry);
                }
            }
            if (!categoryEntries.isEmpty() || category.getType().equals(CategoryType.BASKET)) {
                addCategory(new CategoryWithEntries(category, categoryEntries));
                entries.removeAll(categoryEntries);
            }
        }
    }
}
