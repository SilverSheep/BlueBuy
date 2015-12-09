package pl.cba.marcinbaranowski.bluebuy.db;

/**
 * Created by flipflap on 07.12.15.
 */

import android.content.Context;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import pl.cba.marcinbaranowski.bluebuy.config.DBConfig;

public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String CATEGORY_TABLE_CREATE =
            "CREATE TABLE " + DBConfig.CATEGORY_TABLE_NAME + " (" +
                    DBConfig.CATEGORY_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    DBConfig.CATEGORY_COLUMN_NAME + " TEXT NOT NULL);";

    private static final String ENTRY_TABLE_CREATE =
            "CREATE TABLE " + DBConfig.ENTRY_TABLE_NAME + " (" +
                    DBConfig.ENTRY_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    DBConfig.ENTRY_COLUMN_NAME + " TEXT, " +
                    DBConfig.ENTRY_COLUMN_COMMENT + " TEXT, " +
                    DBConfig.ENTRY_COLUMN_QUANTITY + " INTEGER, " +
                    DBConfig.ENTRY_COLUMN_CATEGORY_ID + " INTEGER, " +
                    DBConfig.ENTRY_COLUMN_RECENT_CATEGORY_ID + " INTEGER, " +
                    DBConfig.ENTRY_COLUMN_UNIT + " TEXT, " +
                    "FOREIGN KEY(" + DBConfig.ENTRY_COLUMN_CATEGORY_ID + ") REFERENCES " +
                    DBConfig.CATEGORY_TABLE_NAME + "(" + DBConfig.CATEGORY_COLUMN_ID + "), " +

                    "FOREIGN KEY(" + DBConfig.ENTRY_COLUMN_RECENT_CATEGORY_ID + ") REFERENCES " +
                    DBConfig.CATEGORY_TABLE_NAME + "(" + DBConfig.CATEGORY_COLUMN_ID + "));";


    private static final String INSERT_TO_CATEGORY_TABLE = "INSERT INTO " + DBConfig.CATEGORY_TABLE_NAME +
            " (" + DBConfig.CATEGORY_COLUMN_NAME + ") VALUES ('rtv');";

    private static final String SQL_DELETE_CATEGORIES = "DROP TABLE IF EXISTS " + DBConfig.CATEGORY_TABLE_NAME;
    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DBConfig.ENTRY_TABLE_NAME;

    public DbHelper(Context context) {
        super(context, DBConfig.DATABASE_NAME, null, DATABASE_VERSION);
    }

    public int countCategories() {
        SQLiteDatabase db = getReadableDatabase();
        return (int) DatabaseUtils.queryNumEntries(db, DBConfig.CATEGORY_TABLE_NAME, null, null);
    }

    public int countEntries() {
        SQLiteDatabase db = getReadableDatabase();
        return (int) DatabaseUtils.queryNumEntries(db, DBConfig.ENTRY_TABLE_NAME, null, null);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CATEGORY_TABLE_CREATE);
        db.execSQL(ENTRY_TABLE_CREATE);
        db.execSQL(INSERT_TO_CATEGORY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        db.execSQL(SQL_DELETE_CATEGORIES);
    }

}
