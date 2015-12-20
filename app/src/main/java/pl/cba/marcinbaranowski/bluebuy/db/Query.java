package pl.cba.marcinbaranowski.bluebuy.db;

import java.util.List;

import pl.cba.marcinbaranowski.bluebuy.config.DBConfig;
import pl.cba.marcinbaranowski.bluebuy.model.CategoryType;

/**
 * Created by flipflap on 10.12.15.
 */
class Query {

    private static final String INSERT_TO_CATEGORY_TABLE_PREFIX = "INSERT INTO " + DBConfig.CATEGORY_TABLE_NAME +
            " (" + DBConfig.CATEGORY_COLUMN_NAME + ", " + DBConfig.CATEGORY_COLUMN_TYPE + ") ";

    static final String CATEGORY_TABLE_CREATE =
            "CREATE TABLE " + DBConfig.CATEGORY_TABLE_NAME + " (" +
                    DBConfig.CATEGORY_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    DBConfig.CATEGORY_COLUMN_NAME + " TEXT NOT NULL, " +
                    DBConfig.CATEGORY_COLUMN_TYPE + " INTEGER NOT NULL);";

    static final String ENTRY_TABLE_CREATE =
            "CREATE TABLE " + DBConfig.ENTRY_TABLE_NAME + " (" +
                    DBConfig.ENTRY_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    DBConfig.ENTRY_COLUMN_NAME + " TEXT, " +
                    DBConfig.ENTRY_COLUMN_COMMENT + " TEXT, " +
                    DBConfig.ENTRY_COLUMN_QUANTITY + " REAL, " +
                    DBConfig.ENTRY_COLUMN_CATEGORY_ID + " INTEGER, " +
                    DBConfig.ENTRY_COLUMN_RECENT_CATEGORY_ID + " INTEGER, " +
                    DBConfig.ENTRY_COLUMN_UNIT + " TEXT, " +
                    "FOREIGN KEY(" + DBConfig.ENTRY_COLUMN_CATEGORY_ID + ") REFERENCES " +
                    DBConfig.CATEGORY_TABLE_NAME + "(" + DBConfig.CATEGORY_COLUMN_ID + "), " +

                    "FOREIGN KEY(" + DBConfig.ENTRY_COLUMN_RECENT_CATEGORY_ID + ") REFERENCES " +
                    DBConfig.CATEGORY_TABLE_NAME + "(" + DBConfig.CATEGORY_COLUMN_ID + "));";

    static final String SQL_DELETE_CATEGORIES = "DROP TABLE IF EXISTS " + DBConfig.CATEGORY_TABLE_NAME;
    static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DBConfig.ENTRY_TABLE_NAME;

    static String prepareCategoryInsertQuery(List<String> categoryNames) {
        String SELECT = "SELECT '";
        String UNION = "UNION ALL\n";

        StringBuilder sb = new StringBuilder();

        sb.append(INSERT_TO_CATEGORY_TABLE_PREFIX);

        for (int i=0; i<categoryNames.size(); ++i) {
            String categoryName = categoryNames.get(i);

            int type = CategoryType.REGULAR.ordinal();

            switch (categoryName) {
                case "koszyk":
                    type = CategoryType.BIN.ordinal();
                    break;
                case "inne":
                    type = CategoryType.OTHERS.ordinal();
                    break;
            }

            sb.append(SELECT + categoryName + "', " + type);

            if (i < categoryNames.size()-1) {
                sb.append(" " + UNION);
            }
        }

        return sb.toString();
    }
}
