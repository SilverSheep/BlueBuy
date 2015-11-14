package pl.cba.marcinbaranowski.bluebuy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import pl.cba.marcinbaranowski.bluebuy.R;
import pl.cba.marcinbaranowski.bluebuy.model.Category;
import pl.cba.marcinbaranowski.bluebuy.provider.CategoryProvider;

/**
 * Created by flipflap on 26.10.15.
 */
//TODO: Prepare class - it's just copied from CategoryListAdapter and renamed
public class ShoppingListAdapter extends BaseAdapter {

    private CategoryProvider categoryProvider;
    private Context context;

    public ShoppingListAdapter(Context context) {
        this.context = context;
        categoryProvider = new CategoryProvider();
    }

    @Override
    public int getCount() {
        return categoryProvider.getCategoriesSize();
    }

    @Override
    public Category getItem(int i) {
        return categoryProvider.getCategory(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void addCategory(Category category) {
        categoryProvider.addCategory(category);
    }

    public void removeCategory(Category category) {
        categoryProvider.removeCategory(category);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View categoryView;

        if (view != null) {
            categoryView = view;
        } else {
            categoryView = LayoutInflater.from(context).inflate(R.layout.category_row, viewGroup, false);
        }

        bindCategoryToView(getItem(i), categoryView);

        return categoryView;
    }
    private void bindCategoryToView(Category category, View categoryView) {
        TextView categoryName = (TextView) categoryView.findViewById(R.id.category_name);
        categoryName.setText(category.getName());
    }
}
