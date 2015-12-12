package pl.cba.marcinbaranowski.bluebuy.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by flipflap on 07.12.15.
 */
public class CategoryWithEntries {
    private List<Entry> entries = new ArrayList<>();
    private Category category;

    public CategoryWithEntries(Category category) {
        this.category = category;
    }

    public CategoryWithEntries(Category category, List<Entry> entries) {
        this.category = category;
        this.entries = entries;
    }


    public List<Entry> getEntries() {
        return entries;
    }

    public void setEntries(List<Entry> entries) {
        this.entries = entries;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
