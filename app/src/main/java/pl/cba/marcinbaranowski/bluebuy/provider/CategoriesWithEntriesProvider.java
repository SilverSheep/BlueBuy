package pl.cba.marcinbaranowski.bluebuy.provider;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import pl.cba.marcinbaranowski.bluebuy.model.Category;
import pl.cba.marcinbaranowski.bluebuy.model.CategoryWithEntries;
import pl.cba.marcinbaranowski.bluebuy.model.Entry;

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

    public void removeEntry(int categoryPosition, Entry entry) {
        EntryProvider entryProvider = new EntryProvider(context);
        CATEGORIES_WITH_ENTRIES.get(categoryPosition).getEntries().remove(entry);

        entryProvider.removeEntry(entry);
    }

    public void refreshList() {
        CATEGORIES_WITH_ENTRIES.clear();
        prepareCategoriesWithEntries();
    }

    public void moveToBasket(int oldCategoryPosition, Entry entry) {
        for (int i = 0; i < CATEGORIES_WITH_ENTRIES.size(); ++i) {
            if (CATEGORIES_WITH_ENTRIES.get(i).getCategory().isBasket()) {
                moveEntryToOtherCategory(oldCategoryPosition, i, entry);
            }
        }
    }

    public void moveBackToOriginalCategory(int basketCategoryPosition, Entry entry) {
        for (int i = 0; i < CATEGORIES_WITH_ENTRIES.size(); ++i) {
            if (CATEGORIES_WITH_ENTRIES.get(i).getCategory().getName().equals(entry.getRecentCategory().getName())) {
                moveEntryToOtherCategory(basketCategoryPosition, i, entry);
            }
        }
    }

    private void moveEntryToOtherCategory(int oldCategoryPosition, int newCategoryPosition, Entry entry) {
        CategoryWithEntries oldCategory = getCategory(oldCategoryPosition);

        if (!oldCategory.getCategory().isBasket()) {
            entry.setRecentCategory(entry.getCategory());
        }

        oldCategory.getEntries().remove(entry);
        EntryProvider entryProvider = new EntryProvider(context);

        CategoryWithEntries newCategory = getCategory(newCategoryPosition);
        entry.setCategory(newCategory.getCategory());
        newCategory.getEntries().add(entry);
        entryProvider.updateEntry(entry);
    }

    // todo: optimize - swap loops
    private void prepareCategoriesWithEntries() {
        CategoryProvider categoryProvider = new CategoryProvider(context);
        EntryProvider entryProvider = new EntryProvider(context);

        for (Category category :
                categoryProvider.getCategories()) {

            List<Entry> entries = new ArrayList<>();

            for (Entry entry :
                    entryProvider.getEntries()) {

                Category entryCategory = entry.getCategory();

                if (category.getName().equals(entryCategory.getName())) {
                    entries.add(entry);
                    break;
                }
            }
            if (!entries.isEmpty() || category.isBasket()) {
                addCategory(new CategoryWithEntries(category, entries));
            }
        }
    }


}
