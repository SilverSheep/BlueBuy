package pl.cba.marcinbaranowski.bluebuy.db;

import java.util.List;

import pl.cba.marcinbaranowski.bluebuy.config.DBConfig;

/**
 * Created by flipflap on 10.12.15.
 */
class Query {
    private static final String INSERT_TO_CATEGORY_TABLE_PREFIX = "INSERT INTO " + DBConfig.CATEGORY_TABLE_NAME +
            " (" + DBConfig.CATEGORY_COLUMN_NAME + ", " + DBConfig.CATEGORY_COLUMN_IS_BASKET + ") VALUES ";

    static final String CATEGORY_TABLE_CREATE =
            "CREATE TABLE " + DBConfig.CATEGORY_TABLE_NAME + " (" +
                    DBConfig.CATEGORY_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    DBConfig.CATEGORY_COLUMN_NAME + " TEXT NOT NULL, " +
                    DBConfig.CATEGORY_COLUMN_IS_BASKET + " INTEGER NOT NULL);";

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

//    static final String INSERT_TO_CATEGORY_TABLE = INSERT_TO_CATEGORY_TABLE_PREFIX + "rtv" + INSERT_SUFFIX;

    static final String SQL_DELETE_CATEGORIES = "DROP TABLE IF EXISTS " + DBConfig.CATEGORY_TABLE_NAME;
    static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DBConfig.ENTRY_TABLE_NAME;

    static String prepareCategoryInsertQuery(List<String> categories) {
        StringBuilder sb = new StringBuilder();
        sb.append(INSERT_TO_CATEGORY_TABLE_PREFIX);
        for (String categoryName :
                categories) {

            int isBasket = categoryName.equals("koszyk") ? 1 : 0;

            sb.append("('" + categoryName + "', " + isBasket + "),");
        }

        sb.deleteCharAt(sb.length() - 1);
        sb.append(";");

        return sb.toString();
    }
}
