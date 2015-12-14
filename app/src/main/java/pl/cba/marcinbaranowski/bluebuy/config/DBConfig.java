package pl.cba.marcinbaranowski.bluebuy.config;

/**
 * Created by flipflap on 07.12.15.
 */
public class DBConfig {

    public static final String DATABASE_NAME = "blue_buy";
    public static final String CATEGORY_TABLE_NAME = "category";
    public static final String ENTRY_TABLE_NAME = "entry";

    public static final String CATEGORY_COLUMN_ID = "category_id";
    public static final String CATEGORY_COLUMN_NAME = "name";
    public static final String CATEGORY_COLUMN_TYPE = "type";

    public static final String ENTRY_COLUMN_ID = "entry_id";
    public static final String ENTRY_COLUMN_NAME = "name";
    public static final String ENTRY_COLUMN_CATEGORY_ID = "category_id";
    public static final String ENTRY_COLUMN_RECENT_CATEGORY_ID = "recent_category_id";
    public static final String ENTRY_COLUMN_QUANTITY = "quantity";
    public static final String ENTRY_COLUMN_UNIT = "unit";
    public static final String ENTRY_COLUMN_COMMENT = "comment";
}
