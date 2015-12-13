package pl.cba.marcinbaranowski.bluebuy.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import pl.cba.marcinbaranowski.bluebuy.R;
import pl.cba.marcinbaranowski.bluebuy.adapter.CategoryListAdapter;
import pl.cba.marcinbaranowski.bluebuy.model.Category;

public class CategoryListActivity extends AppCompatActivity {

    private final CategoryListAdapter categoryListAdapter = new CategoryListAdapter(this);

    private boolean nothingHasChanged = true;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_list);

        intent = getIntent();

        initializeAddCategoryLink();
        initializeList();
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK, intent.putExtra(ShoppingListActivity.LIST_NEEDS_REFRESH, !nothingHasChanged));
        finish();
    }

    public void setNothingHasChanged(boolean nothingHasChanged) {
        this.nothingHasChanged = nothingHasChanged;
    }

    private void initializeAddCategoryLink() {
        final TextView addCategoryLink = (TextView) findViewById(R.id.add_category_link);

        addCategoryLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNewCategoryDialog();
            }
        });
    }

    private void initializeList() {
        final ListView listView = (ListView) findViewById(R.id.category_list);

        listView.setAdapter(categoryListAdapter);
    }

    private void addCategory(Category category) {
        categoryListAdapter.addCategory(category);
        categoryListAdapter.notifyDataSetChanged();
    }

    // TODO: Move everything connected with dialogs to seperate classes

    private void showNewCategoryDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Daj nazwę nowej kategorii").setTitle("Dodawanie kategorii");

        View dialogView = LayoutInflater.from(this).inflate(R.layout.category_dialog, null, false);
        builder.setView(dialogView);

        final EditText categoryNameInput = (EditText) dialogView.findViewById(R.id.category_edit_text);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //TODO: ADD Category name validation

                addCategory(new Category(categoryNameInput.getText().toString(), false));
                nothingHasChanged = false;
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
