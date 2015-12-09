package pl.cba.marcinbaranowski.bluebuy.db;

/**
 * Created by flipflap on 07.12.15.
 */

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import pl.cba.marcinbaranowski.bluebuy.config.DBConfig;

public class EntryDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    // TODO: Sthng wrong with table_create - it works at first run of application, but late it says there's no db entry

    private static final String TABLE_CREATE =
            "CREATE TABLE " + DBConfig.ENTRY_TABLE_NAME + " (" +
                    DBConfig.ENTRY_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    DBConfig.ENTRY_COLUMN_NAME + " TEXT, " +
                    DBConfig.ENTRY_COLUMN_COMMENT + " TEXT, " +
                    DBConfig.ENTRY_COLUMN_QUANTITY + " INTEGER, " +
                    DBConfig.ENTRY_COLUMN_CATEGORY_ID + " INTEGER, " +
                    DBConfig.ENTRY_COLUMN_RECENT_CATEGORY_ID + " INTEGER, " +

                    "FOREIGN KEY(" + DBConfig.ENTRY_COLUMN_CATEGORY_ID + ") REFERENCES " +
                    DBConfig.CATEGORY_TABLE_NAME + "(" + DBConfig.CATEGORY_COLUMN_ID + "), " +

                    "FOREIGN KEY(" + DBConfig.ENTRY_COLUMN_RECENT_CATEGORY_ID + ") REFERENCES " +
                    DBConfig.CATEGORY_TABLE_NAME + "(" + DBConfig.CATEGORY_COLUMN_ID + "), " +

                    DBConfig.ENTRY_COLUMN_UNIT + " TEXT);";

    private static final String SELECT_TABLE_NAME = "SELECT name FROM sqlite_master WHERE type='table' AND name='" +
            DBConfig.ENTRY_TABLE_NAME  + "';";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DBConfig.ENTRY_TABLE_NAME;

    public EntryDbHelper(Context context) {
        super(context, DBConfig.DATABASE_NAME, null, DATABASE_VERSION);
    }

    public boolean doesTableExist() {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(SELECT_TABLE_NAME, null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }

    public int countEntries() {
        if (!doesTableExist()) {
            return 0;
        }
        SQLiteDatabase db = getReadableDatabase();
        return (int) DatabaseUtils.queryNumEntries(db, DBConfig.ENTRY_TABLE_NAME, null, null);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
    }

}
