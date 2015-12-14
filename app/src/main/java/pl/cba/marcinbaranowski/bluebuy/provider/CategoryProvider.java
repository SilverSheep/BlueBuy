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
import pl.cba.marcinbaranowski.bluebuy.model.CategoryType;

public class CategoryProvider {

    private DbHelper dbHelper;

    private static final String WHERE_ID_EQUALS = DBConfig.CATEGORY_COLUMN_ID + " =?";

    private List<Category> regularCategories;

    public CategoryProvider(Context context) {
        dbHelper = new DbHelper(context);
    }

    public Category getCategory(int position) {
        return getCategory(position, null, null);
    }

    public Category getRegularCategory(int position) {
        return getCategory(position, "type=?", new String[]{String.valueOf(CategoryType.REGULAR.ordinal())});
    }

    public int getCategoriesSize() {
        return dbHelper.countCategories();
    }

    public int getRegularCategoriesSize() {
        return dbHelper.countRegularCategories();
    }

    public List<Category> getCategories() {
        List<Category> categories = new ArrayList<>();

        for (int i = 0; i < getCategoriesSize(); i++) {
            categories.add(getCategory(i));
        }

        return categories;
    }

    public void addCategory(Category category) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBConfig.CATEGORY_COLUMN_NAME, category.getName());
        values.put(DBConfig.CATEGORY_COLUMN_TYPE, false);

        db.insert(DBConfig.CATEGORY_TABLE_NAME, null, values);
    }

    public void removeCategory(Category category) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete(DBConfig.CATEGORY_TABLE_NAME, WHERE_ID_EQUALS, new String[]{category.getId() + ""});
    }

    public void updateCategory(Category category) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBConfig.CATEGORY_COLUMN_NAME, category.getName());
        values.put(DBConfig.CATEGORY_COLUMN_TYPE, category.getType().ordinal());
        db.update(DBConfig.CATEGORY_TABLE_NAME, values, DBConfig.CATEGORY_COLUMN_ID + "=" + category.getId(), null);
    }

    private Category getCategory(int position, String whereCondition, String[] columnsForWhereCondition) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                DBConfig.CATEGORY_COLUMN_ID, DBConfig.CATEGORY_COLUMN_NAME, DBConfig.CATEGORY_COLUMN_TYPE
        };

        String sortOrder = DBConfig.CATEGORY_COLUMN_NAME + " DESC";

        Cursor cursor = db.query(
                DBConfig.CATEGORY_TABLE_NAME,  // The table to query
                projection,                    // The columns to return
                whereCondition,                // The columns for the WHERE clause
                columnsForWhereCondition,      // The values for the WHERE clause
                null,                          // don't group the rows
                null,                          // don't filter by row groups
                sortOrder                      // The sort order
        );

        cursor.moveToPosition(position);

        int id = cursor.getInt(0);
        String name = cursor.getString(1);
        CategoryType categoryType = CategoryType.values()[cursor.getInt(2)];

        return new Category(id, name, categoryType);
    }
}
