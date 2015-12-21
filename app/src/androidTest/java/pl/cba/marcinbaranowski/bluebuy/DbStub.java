package pl.cba.marcinbaranowski.bluebuy;

import pl.cba.marcinbaranowski.bluebuy.model.Category;
import pl.cba.marcinbaranowski.bluebuy.model.Entry;

/**
 * Created by flipflap on 21.12.15.
 */
public class DbStub {
    public final static String EXISTING_CATEGORY_NAME = "existing_category";
    public final static String EXISTING_ENTRY_NAME = "existing_entry";
    
    /**
     * @param category to be saved in DB Stub
     * @return has category been successfully saved
     */
    public boolean saveCategory(Category category) {
        return !category.getName().equals(EXISTING_CATEGORY_NAME);
    }

    /**
     * @param entry to be saved in DB Stub
     * @return has entry been successfully saved
     */
    public boolean saveEntry(Entry entry) {
        return !entry.getName().equals(EXISTING_ENTRY_NAME);
    }
}
