package pl.cba.marcinbaranowski.bluebuy.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import pl.cba.marcinbaranowski.bluebuy.R;
import pl.cba.marcinbaranowski.bluebuy.activity.CategoryListActivity;
import pl.cba.marcinbaranowski.bluebuy.activity.EntryActivity;
import pl.cba.marcinbaranowski.bluebuy.model.Category;
import pl.cba.marcinbaranowski.bluebuy.provider.CategoriesWithEntriesProvider;
import pl.cba.marcinbaranowski.bluebuy.provider.CategoryProvider;

/**
 * Created by flipflap on 26.10.15.
 */

public class CategoryListAdapter extends BaseAdapter {

    private CategoryProvider categoryProvider;
    private Context context;

    public CategoryListAdapter(Context context) {
        this.context = context;
        categoryProvider = new CategoryProvider(context);
    }

    @Override
    public int getCount() {
        return categoryProvider.getRegularCategoriesSize();  // excluding bin
    }

    @Override
    public Category getItem(int i) {
        return categoryProvider.getRegularCategory(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void addCategory(Category category) {
        categoryProvider.addCategory(category);
    }

    public void update(Category category) {
        categoryProvider.updateCategory(category);
    }

    public void removeCategory(Category category) {
        CategoriesWithEntriesProvider categoriesWithEntriesProvider = new CategoriesWithEntriesProvider(context);

        categoriesWithEntriesProvider.removeCategory(category);
        notifyDataSetChanged();
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

    private void bindCategoryToView(final Category category, View categoryView) {
        TextView categoryName = (TextView) categoryView.findViewById(R.id.category_name);
        categoryName.setText(category.getName());

        final ImageView editIcon = (ImageView) categoryView.findViewById(R.id.edit_category_icon);
        final ImageView removeIcon = (ImageView) categoryView.findViewById(R.id.remove_category_icon);

        if ((context instanceof EntryActivity)) {
            editIcon.setVisibility(View.INVISIBLE);
            removeIcon.setVisibility(View.INVISIBLE);
        } else {
            editIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: Dodać typ IRREMOVABLE (albo primary)
//                    if (!category.getType().equals(CategoryType.IRREMOVABLE)) {
//                        Toast.makeText(context, "Nie można edytować tej kategorii", Toast.LENGTH_LONG).show();
//                    }
                    showEditCategoryDialog(category);
                }
            });

            removeIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: Dodać typ IRREMOVABLE (albo primary)
                   // if (!category.getType().equals(CategoryType.IRREMOVABLE)) {
                     //   Toast.makeText(context, "Nie można usunąć tej kategorii", Toast.LENGTH_LONG).show();
                    //}
                    showConfirmCategoryDeletionDialog(category);
                }
            });
        }
    }

    private void showEditCategoryDialog(final Category category) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setMessage("Zmień nazwę kategorii").setTitle("Edycja kategorii");

        View dialogView = LayoutInflater.from(context).inflate(R.layout.category_dialog, null, false);
        builder.setView(dialogView);

        final EditText categoryNameInput = (EditText) dialogView.findViewById(R.id.category_edit_text);
        categoryNameInput.setText(category.getName());

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //TODO: ADD Category name validation
                category.setName(categoryNameInput.getText().toString());
                update(category);
                ((CategoryListActivity) context).setNothingHasChanged(false);
            }
        });

        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // do nothing
            }
        });

        AlertDialog dialog = builder.create();

        dialog.show();
    }

    private void showConfirmCategoryDeletionDialog(final Category category) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setMessage("Usunąć kategorię?").setTitle("Jesteś pewna?");

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                removeCategory(category);
                ((CategoryListActivity) context).setNothingHasChanged(false);
            }
        });

        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // do nothing
            }
        });

        AlertDialog dialog = builder.create();

        dialog.show();
    }
}
