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

    public void moveAllToBasket() {

        EntryProvider entryProvider = new EntryProvider(context);
        List<CategoryWithEntries> categoriesExcludingBasket = new ArrayList<>(CATEGORIES_WITH_ENTRIES);

        int basketPosition = -1;
        for (int i = 0; i < categoriesExcludingBasket.size(); ++i) {
            if (categoriesExcludingBasket.get(i).getCategory().isBasket()) {
                basketPosition = i;
            }
        }

        categoriesExcludingBasket.remove(categoriesExcludingBasket.get(basketPosition));

        for (CategoryWithEntries categoryWithEntries : categoriesExcludingBasket) {

            List<Entry> entries = categoryWithEntries.getEntries();

            for (int i = 0; i < entries.size(); ++i) {
                Entry entry = entries.get(i);
                entry.setRecentCategory(entry.getCategory());
                CategoryWithEntries newCategory = getCategory(basketPosition);
                entry.setCategory(newCategory.getCategory());
                newCategory.getEntries().add(entry);
                entryProvider.updateEntry(entry);
            }
        }
        for (CategoryWithEntries categoryWithEntries : categoriesExcludingBasket) {
            categoryWithEntries.getEntries().clear();
        }
    }

    public void moveToBasket(int oldCategoryPosition, Entry entry) {
        for (int i = 0; i < CATEGORIES_WITH_ENTRIES.size(); ++i) {
            if (CATEGORIES_WITH_ENTRIES.get(i).getCategory().isBasket()) {
                moveEntryToOtherCategory(oldCategoryPosition, i, entry);
            }
        }
    }

    public void moveBackToOriginalCategory(int basketCategoryPosition, Entry entry) {
        boolean isOriginalCategoryEmpty = true;
        for (int i = 0; i < CATEGORIES_WITH_ENTRIES.size(); ++i) {
            String categoryName = CATEGORIES_WITH_ENTRIES.get(i).getCategory().getName();
            String entryCategoryName = entry.getRecentCategory().getName();
            if (categoryName.equals(entryCategoryName)) {
                moveEntryToOtherCategory(basketCategoryPosition, i, entry);
                isOriginalCategoryEmpty = false;
            }
        }
        if (isOriginalCategoryEmpty) {
            moveEntryToEmptyCategory(basketCategoryPosition, entry.getRecentCategory(), entry);
        }
    }

    private void moveEntryToEmptyCategory(int oldCategoryPosition, Category emptyCategory, Entry entry) {
        CategoryWithEntries oldCategory = getCategory(oldCategoryPosition);

        if (!oldCategory.getCategory().isBasket()) {
            entry.setRecentCategory(entry.getCategory());
        }

        oldCategory.getEntries().remove(entry);

        EntryProvider entryProvider = new EntryProvider(context);

        entry.setCategory(emptyCategory);
        prepareCategoriesWithEntries();
        entryProvider.updateEntry(entry);
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

    private void prepareCategoriesWithEntries() {
        CategoryProvider categoryProvider = new CategoryProvider(context);
        EntryProvider entryProvider = new EntryProvider(context);
        List<Entry> entries = entryProvider.getEntries();

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
            if (!categoryEntries.isEmpty() || category.isBasket()) {
                addCategory(new CategoryWithEntries(category, categoryEntries));
                entries.removeAll(categoryEntries);
            }
        }
    }
}
