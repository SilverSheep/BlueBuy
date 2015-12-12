package pl.cba.marcinbaranowski.bluebuy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import pl.cba.marcinbaranowski.bluebuy.R;
import pl.cba.marcinbaranowski.bluebuy.adapter.CategoryListAdapter;
import pl.cba.marcinbaranowski.bluebuy.model.Category;
import pl.cba.marcinbaranowski.bluebuy.model.Entry;

/**
 * Created by flipflap on 10.12.15.
 */
public class EntryActivity extends AppCompatActivity {

    private CategoryListAdapter categoryListAdapter = new CategoryListAdapter(this);

    private Spinner categorySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entry);

        categorySpinner = (Spinner) findViewById(R.id.categories_spinner);

        categorySpinner.setAdapter(categoryListAdapter);

        final Intent i = getIntent();

        final int requestCode = (int) i.getExtras().getSerializable("requestCode");

        Button okButton = (Button) findViewById(R.id.ok_button);
        Button cancelButton = (Button) findViewById(R.id.cancel_button);

        if (requestCode == ShoppingListActivity.NEW_ENTRY) {
            okButton.setText("Dodaj");
        } else if (requestCode == ShoppingListActivity.EDIT_ENTRY) {
            okButton.setText("Uaktualnij");
        }

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Entry entry = null;
                if (requestCode == ShoppingListActivity.NEW_ENTRY) {
                    entry = addEntry();
                } else if (requestCode == ShoppingListActivity.EDIT_ENTRY) {
                    editEntry();
                }
                setResult(RESULT_OK, i.putExtra(ShoppingListActivity.ENTRY, entry));
                finish();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }

    private Entry addEntry() {
        Category category = (Category) categorySpinner.getSelectedItem();
        EditText entryNameEditText = (EditText) findViewById(R.id.entry_edit_text);
        String entryName = entryNameEditText.getText().toString();

        EditText quantityEditText = (EditText) findViewById(R.id.quantity);
        int quantity = Integer.valueOf(quantityEditText.getText().toString());

        EditText unitEditText = (EditText) findViewById(R.id.unit);

        String unit = unitEditText.getText().toString();

        Entry entry = new Entry(category, entryName, quantity, unit, "");
        return entry;
    }

    private void editEntry() {
        //  entryListAdapter
    }

}
