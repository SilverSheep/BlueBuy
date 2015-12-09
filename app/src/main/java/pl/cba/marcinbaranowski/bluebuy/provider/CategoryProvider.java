package pl.cba.marcinbaranowski.bluebuy.provider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import pl.cba.marcinbaranowski.bluebuy.config.DBConfig;
import pl.cba.marcinbaranowski.bluebuy.db.CategoryDbHelper;
import pl.cba.marcinbaranowski.bluebuy.model.Category;

public class CategoryProvider {

    private CategoryDbHelper categoryDbHelper;

    private static final String WHERE_ID_EQUALS = DBConfig.CATEGORY_COLUMN_ID + " =?";

    public CategoryProvider(Context context) {
        categoryDbHelper = new CategoryDbHelper(context);
    }

    public Category getCategory(int position) {
        SQLiteDatabase db = categoryDbHelper.getReadableDatabase();

        String[] projection = {
                DBConfig.CATEGORY_COLUMN_ID, DBConfig.CATEGORY_COLUMN_NAME
        };

        String sortOrder = DBConfig.CATEGORY_COLUMN_NAME + " DESC";

        Cursor cursor = db.query(
                DBConfig.CATEGORY_TABLE_NAME,  // The table to query
                projection,                    // The columns to return
                null,                          // The columns for the WHERE clause
                null,                          // The values for the WHERE clause
                null,                          // don't group the rows
                null,                          // don't filter by row groups
                sortOrder                      // The sort order
        );

        cursor.moveToPosition(position);

        int id = cursor.getInt(0);
        String name = cursor.getString(1);

        return new Category(id, name);
    }

    public int getCategoriesSize() {
        return categoryDbHelper.countEntries();
    }

    public List<Category> getCategories() {
        List<Category> categories = new ArrayList<>();

        for (int i = 0; i < getCategoriesSize(); i++) {
            categories.add(getCategory(i));
        }

        return categories;
    }

    public void addCategory(Category category) {
        SQLiteDatabase db = categoryDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBConfig.CATEGORY_COLUMN_NAME, category.getName());

        db.insert(DBConfig.CATEGORY_TABLE_NAME, null, values);
    }

    public void removeCategory(Category category) {
        SQLiteDatabase db = categoryDbHelper.getWritableDatabase();

        db.delete(DBConfig.CATEGORY_TABLE_NAME, WHERE_ID_EQUALS, new String[]{category.getId() + ""});
    }

}
