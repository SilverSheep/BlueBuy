package pl.cba.marcinbaranowski.bluebuy.db;

/**
 * Created by flipflap on 07.12.15.
 */

import android.content.Context;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Arrays;
import java.util.List;

import pl.cba.marcinbaranowski.bluebuy.config.DBConfig;
import pl.cba.marcinbaranowski.bluebuy.model.CategoryType;

public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final List<String> categoryNames = Arrays.asList(
            "pieczywo",
            "warzywa",
            "owoce",
            "nabiał",
            "mrożonki",
            "napoje",
            "środki czystości",
            "kosmetyki",
            "rtv & agd",
            "przyprawy",
            "mięso",
            "inne",
            "dania gotowe",
            "sypkie",
            "słodycze",
            "ozdoby",
            "papiernicze",
            "kot",
            "leki",
            "koszyk");

    public DbHelper(Context context) {
        super(context, DBConfig.DATABASE_NAME, null, DATABASE_VERSION);
    }

    public int countCategories() {
        SQLiteDatabase db = getWritableDatabase();
        return (int) DatabaseUtils.queryNumEntries(db, DBConfig.CATEGORY_TABLE_NAME, null, null);
    }

    public int countRegularCategories() {
        SQLiteDatabase db = getWritableDatabase();
        return (int) DatabaseUtils.queryNumEntries(db, DBConfig.CATEGORY_TABLE_NAME,
                "type=" + CategoryType.REGULAR.ordinal(), null);
    }

    public int countEntries() {
        SQLiteDatabase db = getWritableDatabase();
        return (int) DatabaseUtils.queryNumEntries(db, DBConfig.ENTRY_TABLE_NAME, null, null);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Query.CATEGORY_TABLE_CREATE);
        db.execSQL(Query.ENTRY_TABLE_CREATE);
        db.execSQL(Query.prepareCategoryInsertQuery(categoryNames));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Query.SQL_DELETE_ENTRIES);
        db.execSQL(Query.SQL_DELETE_CATEGORIES);
    }

}
