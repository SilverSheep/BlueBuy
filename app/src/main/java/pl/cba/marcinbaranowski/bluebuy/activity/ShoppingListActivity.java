package pl.cba.marcinbaranowski.bluebuy.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import pl.cba.marcinbaranowski.bluebuy.R;
import pl.cba.marcinbaranowski.bluebuy.adapter.CategoryListAdapter;
import pl.cba.marcinbaranowski.bluebuy.adapter.EntryListAdapter;
import pl.cba.marcinbaranowski.bluebuy.adapter.ShoppingListAdapter;
import pl.cba.marcinbaranowski.bluebuy.model.Category;
import pl.cba.marcinbaranowski.bluebuy.model.Entry;

//TODO: Prepare class - it's just copied from CategoryListActivity and renamed
public class ShoppingListActivity extends AppCompatActivity {

    public static final int NEW_ENTRY = 1;
    public static final int EDIT_ENTRY = 2;
    public static final String REQUEST_CODE = "requestCode";
    public static final String ENTRY = "entry";
    public static final String CATEGORY_POSITION = "categoryPosition";

    private final CategoryListAdapter categoryListAdapter = new CategoryListAdapter(this);
    private final EntryListAdapter entryListAdapter = new EntryListAdapter(this);

    private ShoppingListAdapter shoppingListAdapter;
    private ExpandableListView expandableListView;

    public CategoryListAdapter getCategoryListAdapter() {
        return categoryListAdapter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_list);

        initializeAddLinks();
        initializeList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    private void initializeAddLinks() {
        final TextView addEntryLink = (TextView) findViewById(R.id.add_entry_link);
        final TextView addCategoryLink = (TextView) findViewById(R.id.add_category_link);

        addEntryLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newEntry();
            }
        });
        addCategoryLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNewCategoryDialog();
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
        entryListAdapter.notifyDataSetChanged();
        shoppingListAdapter.refreshList();
        shoppingListAdapter.notifyDataSetChanged();
    }

    private void removeCategory(int categoryPosition) {
        categoryListAdapter.removeCategory(categoryListAdapter.getItem(categoryPosition));
        shoppingListAdapter.notifyDataSetChanged();
    }

    private void addEntry(Entry entry) {
        entryListAdapter.addEntry(entry);

        //todo: find out what is really needed
        categoryListAdapter.notifyDataSetChanged();
        entryListAdapter.notifyDataSetChanged();
        shoppingListAdapter.refreshList();
        shoppingListAdapter.notifyDataSetChanged();
    }

    private void removeEntry(int entryPosition) {
        entryListAdapter.addEntry(entryListAdapter.getItem(entryPosition));
        shoppingListAdapter.notifyDataSetChanged();
    }

    private void newEntry() {
        Intent intent = new Intent(this, EntryActivity.class);
        intent.putExtra(REQUEST_CODE, NEW_ENTRY);
        startActivityForResult(intent, NEW_ENTRY);
    }

    private void updateEntry(Entry entry) {
        entryListAdapter.updateEntry(entry);

        categoryListAdapter.notifyDataSetChanged();
        entryListAdapter.notifyDataSetChanged();
        shoppingListAdapter.refreshList();
        shoppingListAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Entry entry = (Entry) data.getExtras().getSerializable(ENTRY);
            if (requestCode == NEW_ENTRY) {
                addEntry(entry);
            }
            else if (requestCode == EDIT_ENTRY) {
                updateEntry(entry);
            }
        }
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
