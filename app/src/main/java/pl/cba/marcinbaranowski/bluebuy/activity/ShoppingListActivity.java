package pl.cba.marcinbaranowski.bluebuy.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import pl.cba.marcinbaranowski.bluebuy.R;
import pl.cba.marcinbaranowski.bluebuy.adapter.CategoryListAdapter;
import pl.cba.marcinbaranowski.bluebuy.adapter.ShoppingListAdapter;
import pl.cba.marcinbaranowski.bluebuy.model.Category;
import pl.cba.marcinbaranowski.bluebuy.model.Entry;

//TODO: Prepare class - it's just copied from CategoryListActivity and renamed
public class ShoppingListActivity extends AppCompatActivity {

    private final CategoryListAdapter categoryListAdapter = new CategoryListAdapter(this);

    ShoppingListAdapter shoppingListAdapter;
    ExpandableListView expandableListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_list);

        initializeAddEntryLink();
        initializeList();
    }

    private void initializeAddEntryLink() {
        final TextView addEntryLink = (TextView) findViewById(R.id.add_entry_link);

        addEntryLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNewEntryDialog();
            }
        });
    }

    private void initializeList() {

        expandableListView = (ExpandableListView) findViewById(R.id.expandable_shopping_list);

        shoppingListAdapter = new ShoppingListAdapter(this);

        // setting list adapter
        expandableListView.setAdapter(shoppingListAdapter);

        // Listview Group click listener
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        // Listview Group expanded listener
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
//                Toast.makeText(getApplicationContext(),
//                        shoppingListAdapter.getGroup(groupPosition) + " Expanded",
//                        Toast.LENGTH_SHORT).show();
            }
        });

        // Listview Group collasped listener
        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
//                Toast.makeText(getApplicationContext(),
//                        shoppingListAdapter.getGroup(groupPosition) + " Collapsed",
//                        Toast.LENGTH_SHORT).show();

            }
        });

        // Listview on child click listener
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub
//                Toast.makeText(
//                        getApplicationContext(),
//                        listDataHeader.get(groupPosition)
//                                + " : "
//                                + listDataChild.get(
//                                listDataHeader.get(groupPosition)).get(
//                                childPosition), Toast.LENGTH_SHORT)
//                        .show();
                return false;
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

    private void showEditEntryDialog(final Entry entry) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Zmień nazwę produktu").setTitle("Edycja produktu");

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

                entry.setName(input.getText().toString());
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

    private void showNewEntryDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Nowy produkt").setTitle("Dodawanie produktu");

        final EditText entryNameInput = new EditText(this);
        final Spinner categorySelect = new Spinner(this);

        categorySelect.setAdapter(categoryListAdapter);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        entryNameInput.setLayoutParams(lp);
        categorySelect.setLayoutParams(lp);

        builder.setView(categorySelect);
        builder.setView(entryNameInput);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //TODO: ADD Entry name validation

                //addEntry(new Entry(entryNameInput.getText().toString()));
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

    private void showConfirmationDialog(final int entryPosition) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(R.string.product_delete_confirmation).setTitle(R.string.product_delete_title);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //removeEntry(entryPosition);
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
        Toast.makeText(this, "Produkt został dodany", Toast.LENGTH_SHORT).show();
    }

    private void showSuccessfullyEditedDialog() {
        Toast.makeText(this, "Produkt został zmieniony", Toast.LENGTH_SHORT).show();
    }

    private void showSuccessfullyRemovedDialog() {
        Toast.makeText(this, "Produkt został usunięty", Toast.LENGTH_SHORT).show();
    }
}
