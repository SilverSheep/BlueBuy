package pl.cba.marcinbaranowski.bluebuy.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import pl.cba.marcinbaranowski.bluebuy.R;
import pl.cba.marcinbaranowski.bluebuy.activity.EntryActivity;
import pl.cba.marcinbaranowski.bluebuy.activity.ShoppingListActivity;
import pl.cba.marcinbaranowski.bluebuy.model.CategoryWithEntries;
import pl.cba.marcinbaranowski.bluebuy.model.Entry;
import pl.cba.marcinbaranowski.bluebuy.provider.CategoriesWithEntriesProvider;

/**
 * Created by flipflap on 26.10.15.
 */
//TODO: Prepare class - it's just copied from CategoryListAdapter and renamed
public class ShoppingListAdapter extends BaseExpandableListAdapter {

    private CategoriesWithEntriesProvider categoriesWithEntriesProvider;
    private Context context;

    public ShoppingListAdapter(Context context) {
        this.context = context;
        categoriesWithEntriesProvider = new CategoriesWithEntriesProvider(context);
    }

    @Override
    public Object getChild(int categoryPosition, int entryPositionWithinCategory) {
        return categoriesWithEntriesProvider.getCategory(categoryPosition).getEntries().get(entryPositionWithinCategory);
    }

    public void refreshList() {
        categoriesWithEntriesProvider.refreshList();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final Entry entry = (Entry) getChild(groupPosition, childPosition);
        final String entryName = entry.getName();
        float quantity = entry.getQuantity();
        String unit = entry.getUnit();

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.entry_row, null);
        }

        TextView entryTextView = (TextView) convertView
                .findViewById(R.id.entry);

        String entryText = entryName + " (";
        if (quantity % 1 == 0) {        // if float has no decimals
            entryText += Math.round(quantity);
        } else {
            entryText += quantity;
        }
        if (unit == null || unit.isEmpty()) {
            entryText += ")";
        } else {
            entryText += " " + unit + ")";
        }
        entryTextView.setText(entryText);

        CategoryWithEntries category = categoriesWithEntriesProvider.getCategory(groupPosition);

        final Boolean isBasket = category.getCategory().isBasket();

        final CheckBox addToBasketCheckBox = (CheckBox) convertView.findViewById(R.id.add_to_basket);

        // todo: why this doesn't work?
        addToBasketCheckBox.setSelected(isBasket);

        addToBasketCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isBasket) {
                    categoriesWithEntriesProvider.moveBackToOriginalCategory(entry);
                } else {
                    categoriesWithEntriesProvider.moveToBasket(entry);
                }
                refreshList();
                notifyDataSetChanged();
            }
        });

        addToBasketCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                addToBasketCheckBox.setChecked(!isChecked);
            }
        });

        final ImageView editEntryImageView = (ImageView) convertView.findViewById(R.id.edit_icon);
        final ImageView removeEntryImageView = (ImageView) convertView.findViewById(R.id.remove_icon);

        editEntryImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EntryActivity.class);
                intent.putExtra(ShoppingListActivity.ENTRY, entry);
                intent.putExtra(ShoppingListActivity.REQUEST_CODE, ShoppingListActivity.EDIT_ENTRY);
                intent.putExtra(ShoppingListActivity.CATEGORY_POSITION, groupPosition);
                ((Activity) context).startActivityForResult(intent, ShoppingListActivity.EDIT_ENTRY);
            }
        });

        removeEntryImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmEntryDeletionDialog(groupPosition, entry);
            }
        });

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return categoriesWithEntriesProvider.getCategory(groupPosition).getEntries().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return categoriesWithEntriesProvider.getCategory(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return categoriesWithEntriesProvider.getCategoriesSize();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        CategoryWithEntries category = (CategoryWithEntries) getGroup(groupPosition);
        String categoryName = category.getCategory().getName();

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.category_row, null);
        }

        TextView categoryNameTextView = (TextView) convertView
                .findViewById(R.id.category_name);

        String categoryText = categoryName + " (" + getChildrenCount(groupPosition) + ")";
        categoryNameTextView.setText(categoryText);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private void showConfirmEntryDeletionDialog(final int groupPosition, final Entry entry) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setMessage("Usunąć produkt z listy?").setTitle("Jesteś pewna?");

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                categoriesWithEntriesProvider.removeEntry(groupPosition, entry);
                refreshList();
                notifyDataSetChanged();
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

    public void moveAllToBasket() {
        categoriesWithEntriesProvider.moveAllToBasket();
        refreshList();
        notifyDataSetChanged();
    }

    public void resetList() {
        categoriesWithEntriesProvider.resetList();
        refreshList();
        notifyDataSetChanged();
    }
}