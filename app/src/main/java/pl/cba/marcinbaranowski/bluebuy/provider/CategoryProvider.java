package pl.cba.marcinbaranowski.bluebuy.provider;

import java.util.ArrayList;
import java.util.List;

import pl.cba.marcinbaranowski.bluebuy.model.Category;

public class CategoryProvider {

    private static final List<Category> CATEGORIES = new ArrayList<>();

    //TODO: Sort alphabetically
     public CategoryProvider() {
        CATEGORIES.clear();
        CATEGORIES.add(new Category("Słodycze"));
        CATEGORIES.add(new Category("AGD i RTV"));
        CATEGORIES.add(new Category("Chemia"));
        CATEGORIES.add(new Category("Prezenty"));
        CATEGORIES.add(new Category("Warzywa"));
        CATEGORIES.add(new Category("Owoce"));
        CATEGORIES.add(new Category("Pieczywo"));
        CATEGORIES.add(new Category("Nabiał"));
        CATEGORIES.add(new Category("Napoje"));
    }

    public Category getCategory(int position) {
        return CATEGORIES.get(position);
    }

    public int getCategoriesSize() {
        return CATEGORIES.size();
    }

    public void addCategory(Category category) {
        CATEGORIES.add(category);
    }

    public void removeCategory(Category category) {
        CATEGORIES.remove(category);
    }

}
