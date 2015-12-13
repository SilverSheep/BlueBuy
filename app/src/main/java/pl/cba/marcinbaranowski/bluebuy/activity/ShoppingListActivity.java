package pl.cba.marcinbaranowski.bluebuy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import pl.cba.marcinbaranowski.bluebuy.R;
import pl.cba.marcinbaranowski.bluebuy.adapter.CategoryListAdapter;
import pl.cba.marcinbaranowski.bluebuy.adapter.EntryListAdapter;
import pl.cba.marcinbaranowski.bluebuy.adapter.ShoppingListAdapter;
import pl.cba.marcinbaranowski.bluebuy.model.Entry;

public class ShoppingListActivity extends AppCompatActivity {

    public static final int NEW_ENTRY = 1;
    public static final int EDIT_ENTRY = 2;
    public static final int CATEGORY_LIST = 3;
    public static final String REQUEST_CODE = "requestCode";
    public static final String ENTRY = "entry";
    public static final String CATEGORY_POSITION = "categoryPosition";
    public static final String LIST_NEEDS_REFRESH = "listNeedsRefresh";

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_all_to_basket:
                shoppingListAdapter.moveAllToBasket();
                return true;
            case R.id.action_reset_list:
                shoppingListAdapter.resetList();
                return true;
            case R.id.action_categories:
                showCategoryList();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showCategoryList() {
        Intent intent = new Intent(this, CategoryListActivity.class);
        intent.putExtra(REQUEST_CODE, CATEGORY_LIST);
        startActivityForResult(intent, CATEGORY_LIST);
    }

    private void initializeAddLinks() {
        final TextView addEntryLink = (TextView) findViewById(R.id.add_entry_link);

        addEntryLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newEntry();
            }
        });
    }

    private void initializeList() {
        expandableListView = (ExpandableListView) findViewById(R.id.expandable_shopping_list);
        shoppingListAdapter = new ShoppingListAdapter(this);
        expandableListView.setAdapter(shoppingListAdapter);
    }

    private void addEntry(Entry entry) {
        entryListAdapter.addEntry(entry);

        //todo: find out what is really needed
        categoryListAdapter.notifyDataSetChanged();
        entryListAdapter.notifyDataSetChanged();
        shoppingListAdapter.refreshList();
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
            if (requestCode == NEW_ENTRY) {
                Entry entry = (Entry) data.getExtras().getSerializable(ENTRY);
                addEntry(entry);
            }
            else if (requestCode == EDIT_ENTRY) {
                Entry entry = (Entry) data.getExtras().getSerializable(ENTRY);
                updateEntry(entry);
            }
            else if (requestCode == CATEGORY_LIST) {
                boolean listNeedsRefresh = data.getExtras().getBoolean(LIST_NEEDS_REFRESH);

                if (listNeedsRefresh) {
                    shoppingListAdapter.refreshList();
                }
            }
        }
    }
}
