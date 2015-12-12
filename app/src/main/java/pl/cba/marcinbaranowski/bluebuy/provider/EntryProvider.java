package pl.cba.marcinbaranowski.bluebuy.provider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import pl.cba.marcinbaranowski.bluebuy.config.DBConfig;
import pl.cba.marcinbaranowski.bluebuy.db.DbHelper;
import pl.cba.marcinbaranowski.bluebuy.model.Category;
import pl.cba.marcinbaranowski.bluebuy.model.Entry;

/**
 * Created by flipflap on 07.12.15.
 */
public class EntryProvider {

    private DbHelper dbHelper;

    private static final String WHERE_ID_EQUALS = DBConfig.ENTRY_COLUMN_ID + " =?";
    private static final String CATEGORY_NAME_WITH_PREFIX = "cat.name";
    private static final String RECENT_CATEGORY_NAME_PREFIX = "rcat.name";
    private static final String CATEGORY_IS_BASKET_WITH_PREFIX = "cat.is_basket";

    public EntryProvider(Context context) {
        dbHelper = new DbHelper(context);
    }

    public Entry getEntry(int position) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // TODO: Sort entries
        String query = "SELECT " + DBConfig.ENTRY_COLUMN_ID + ",ent."
                + DBConfig.ENTRY_COLUMN_NAME + ","
                + DBConfig.ENTRY_COLUMN_QUANTITY + ","
                + DBConfig.ENTRY_COLUMN_UNIT + ","
                + DBConfig.ENTRY_COLUMN_COMMENT + ",ent."
                + DBConfig.ENTRY_COLUMN_CATEGORY_ID + ","
                + DBConfig.ENTRY_COLUMN_RECENT_CATEGORY_ID + ","
                + CATEGORY_NAME_WITH_PREFIX + ","
                + CATEGORY_IS_BASKET_WITH_PREFIX + ","
                + RECENT_CATEGORY_NAME_PREFIX + " FROM "
                + DBConfig.ENTRY_TABLE_NAME + " ent, "
                + DBConfig.CATEGORY_TABLE_NAME + " cat, "
                + DBConfig.CATEGORY_TABLE_NAME + " rcat WHERE ent."
                + DBConfig.ENTRY_COLUMN_CATEGORY_ID + " = cat."
                + DBConfig.CATEGORY_COLUMN_ID + " AND "
                + DBConfig.ENTRY_COLUMN_RECENT_CATEGORY_ID + " = rcat."
                + DBConfig.CATEGORY_COLUMN_ID;

        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null && cursor.moveToPosition(position)) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            int quantity = cursor.getInt(2);
            String unit = cursor.getString(3);
            String comment = cursor.getString(4);

            int categoryId = cursor.getInt(5);
            int recentCategoryId = cursor.getInt(6);
            String categoryName = cursor.getString(7);
            Boolean isBasket = cursor.getInt(8) == 1;

          String recentCategoryName = cursor.getString(9);

            cursor.close();

            Category category = new Category(categoryId, categoryName, isBasket);

            if (categoryId == recentCategoryId) {
                return new Entry(id, category, name, quantity, unit, comment);
            }
            else {
                Category recentCategory = new Category(recentCategoryId, recentCategoryName, false);

                return new Entry(id, category, recentCategory, name, quantity, unit, comment);
            }
        }

        return null;
    }

    // TODO: Optimize
    public List<Entry> getEntries() {
        List<Entry> entries = new ArrayList<>();

        for (int i = 0; i < getEntriesSize(); i++) {
            entries.add(getEntry(i));
        }

        return entries;
    }

    public int getEntriesSize() {
        return dbHelper.countEntries();
    }

    public void addEntry(Entry entry) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBConfig.ENTRY_COLUMN_NAME, entry.getName());
        values.put(DBConfig.ENTRY_COLUMN_QUANTITY, entry.getQuantity());
        values.put(DBConfig.ENTRY_COLUMN_UNIT, entry.getUnit());
        values.put(DBConfig.ENTRY_COLUMN_COMMENT, entry.getComment());
        values.put(DBConfig.ENTRY_COLUMN_CATEGORY_ID, entry.getCategory().getId());
        values.put(DBConfig.ENTRY_COLUMN_RECENT_CATEGORY_ID, entry.getCategory().getId());

        db.insert(DBConfig.ENTRY_TABLE_NAME, null, values);
    }

    public void removeEntry(Entry entry) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete(DBConfig.ENTRY_TABLE_NAME, WHERE_ID_EQUALS, new String[]{entry.getId() + ""});
    }

    public void updateEntry(Entry entry) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBConfig.ENTRY_COLUMN_NAME, entry.getName());
        values.put(DBConfig.ENTRY_COLUMN_QUANTITY, entry.getQuantity());
        values.put(DBConfig.ENTRY_COLUMN_UNIT, entry.getUnit());
        values.put(DBConfig.ENTRY_COLUMN_COMMENT, entry.getComment());
        values.put(DBConfig.ENTRY_COLUMN_CATEGORY_ID, entry.getCategory().getId());
        values.put(DBConfig.ENTRY_COLUMN_RECENT_CATEGORY_ID, entry.getRecentCategory().getId());

        db.update(DBConfig.ENTRY_TABLE_NAME, values, DBConfig.ENTRY_COLUMN_ID + "=" + entry.getId(), null);
    }
}
