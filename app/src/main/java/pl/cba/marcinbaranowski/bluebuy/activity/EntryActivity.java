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
    private EditText entryNameEditText;
    private EditText quantityEditText;
    private EditText unitEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entry);

        categorySpinner = (Spinner) findViewById(R.id.categories_spinner);
        entryNameEditText = (EditText) findViewById(R.id.entry_edit_text);
        quantityEditText = (EditText) findViewById(R.id.quantity);
        unitEditText = (EditText) findViewById(R.id.unit);


        categorySpinner.setAdapter(categoryListAdapter);

        final Intent i = getIntent();

        final int requestCode = i.getExtras().getInt(ShoppingListActivity.REQUEST_CODE);
        final Entry entryForEdition = (Entry) i.getExtras().getSerializable(ShoppingListActivity.ENTRY);
        int categoryPosition = i.getExtras().getInt(ShoppingListActivity.CATEGORY_POSITION);

        Button okButton = (Button) findViewById(R.id.ok_button);
        Button cancelButton = (Button) findViewById(R.id.cancel_button);

        if (requestCode == ShoppingListActivity.NEW_ENTRY) {
            okButton.setText("Dodaj");
        } else if (requestCode == ShoppingListActivity.EDIT_ENTRY) {
            loadEntry(entryForEdition, categoryPosition);
            okButton.setText("Uaktualnij");
        }

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Entry entry = null;
                if (requestCode == ShoppingListActivity.NEW_ENTRY) {
                    entry = prepareEntry();
                } else if (requestCode == ShoppingListActivity.EDIT_ENTRY) {
                    Entry entryForEdition = (Entry) i.getExtras().getSerializable(ShoppingListActivity.ENTRY);
                    entry = prepareEntry(entryForEdition);
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

    private void loadEntry(Entry entryForEdition, int categoryPosition) {
        categorySpinner.setSelection(categoryPosition);
        entryNameEditText.setText(entryForEdition.getName());
        quantityEditText.setText(String.valueOf(entryForEdition.getQuantity()));
        unitEditText.setText(entryForEdition.getUnit());
    }

    private Entry prepareEntry() {
        Category category = (Category) categorySpinner.getSelectedItem();
        String entryName = entryNameEditText.getText().toString();
        int quantity = Integer.valueOf(quantityEditText.getText().toString());

        String unit = unitEditText.getText().toString();

        Entry entry = new Entry(category, entryName, quantity, unit, "");
        return entry;
    }

    private Entry prepareEntry(Entry entryForEdition) {
        Entry entry = prepareEntry();
        entry.setId(entryForEdition.getId());
        entry.setRecentCategory(entryForEdition.getCategory());
        return entry;
    }

}
