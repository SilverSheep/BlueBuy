package pl.cba.marcinbaranowski.bluebuy.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import pl.cba.marcinbaranowski.bluebuy.adapter.CategoryListAdapter;
import pl.cba.marcinbaranowski.bluebuy.R;
import pl.cba.marcinbaranowski.bluebuy.model.Category;

public class CategoryListActivity extends AppCompatActivity {

    private final CategoryListAdapter categoryListAdapter = new CategoryListAdapter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_list);
        initializeAddCategoryLink();
        initializeList();
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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                showEditCategoryDialog(categoryListAdapter.getItem(i));
            }
        });
    }

    private void addCategory(Category category) {
        categoryListAdapter.addCategory(category);
        categoryListAdapter.notifyDataSetChanged();
    }

    private void removeCategory(int categoryPosition) {
        categoryListAdapter.removeCategory(categoryListAdapter.getItem(categoryPosition));
        categoryListAdapter.notifyDataSetChanged();
    }

    private void showEditCategoryDialog(final Category category) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Zmień nazwę kategorii").setTitle("Edycja kategorii");

        final EditText input = new EditText(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        builder.setView(input);


        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //TODO: ADD Category name validation

                category.setName(input.getText().toString());
                showSuccessfullyEditedDialog();
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

    private void showNewCategoryDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Daj nazwę nowej kategorii").setTitle("Dodawanie kategorii");

        final EditText input = new EditText(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        builder.setView(input);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //TODO: ADD Category name validation

                addCategory(new Category(input.getText().toString()));
                showSuccessfullyAddedDialog();
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

    private void showConfirmationDialog(final int categoryPosition) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(R.string.delete_confirmation).setTitle(R.string.delete_title);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                removeCategory(categoryPosition);
                showSuccessfullyRemovedDialog();
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

    private void showSuccessfullyAddedDialog() {
        Toast.makeText(this, "Kategoria została dodana", Toast.LENGTH_SHORT).show();
    }

    private void showSuccessfullyEditedDialog() {
        Toast.makeText(this, "Kategoria została zmieniona", Toast.LENGTH_SHORT).show();
    }

    private void showSuccessfullyRemovedDialog() {
        Toast.makeText(this, "Kategoria została usunięta", Toast.LENGTH_SHORT).show();
    }
}
