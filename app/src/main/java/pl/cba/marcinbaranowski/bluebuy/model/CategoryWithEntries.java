package pl.cba.marcinbaranowski.bluebuy.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by flipflap on 07.12.15.
 */
public class CategoryWithEntries extends Category {
    private List<Entry> entries = new ArrayList<>();

    public CategoryWithEntries(String name, Boolean isBasket) {
        super(name, isBasket);
    }

    public CategoryWithEntries(int id, String name, Boolean isBasket) {
        super(id, name, isBasket);
    }

    public CategoryWithEntries(String name, Boolean isBasket, List<Entry> entries) {
        super(name, isBasket);
        this.entries = entries;
    }

    public List<Entry> getEntries() {
        return entries;
    }

    public void setEntries(List<Entry> entries) {
        this.entries = entries;
    }
}
