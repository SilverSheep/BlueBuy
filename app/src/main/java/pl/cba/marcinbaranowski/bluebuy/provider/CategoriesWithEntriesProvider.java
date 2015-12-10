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

    public void refreshList() {
        CATEGORIES_WITH_ENTRIES.clear();
        prepareCategoriesWithEntries();
    }

    public void moveToBasket(int oldCategoryPosition, int entryPosition) {
        for (int i=0; i<CATEGORIES_WITH_ENTRIES.size(); ++i) {
            if (CATEGORIES_WITH_ENTRIES.get(i).getName().equals("koszyk")) {
                moveEntryToOtherCategory(oldCategoryPosition, i, entryPosition);
            }
        }
    }

    private void moveEntryToOtherCategory(int oldCategoryPosition, int newCategoryPosition, int entryPosition) {
        CategoryWithEntries oldCategory = getCategory(oldCategoryPosition);
        Entry entry = oldCategory.getEntries().get(entryPosition);

        oldCategory.getEntries().remove(entryPosition);

        CategoryWithEntries newCategory = getCategory(newCategoryPosition);
        newCategory.getEntries().add(entry);
    }

    private void prepareCategoriesWithEntries() {
        CategoryProvider categoryProvider = new CategoryProvider(context);
        EntryProvider entryProvider = new EntryProvider(context);

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


}
