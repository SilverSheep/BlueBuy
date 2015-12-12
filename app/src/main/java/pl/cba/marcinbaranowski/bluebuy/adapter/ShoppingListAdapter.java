package pl.cba.marcinbaranowski.bluebuy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import pl.cba.marcinbaranowski.bluebuy.R;
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
        int quantity = entry.getQuantity();
        String unit = entry.getUnit();

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.entry_row, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.entry);

        String entryText = entryName + " (" + quantity + " " + unit + ")";

        txtListChild.setText(entryText);

        CategoryWithEntries category = categoriesWithEntriesProvider.getCategory(groupPosition);

        final Boolean isBasket = category.getCategory().isBasket();

        final CheckBox addToBasketCheckBox = (CheckBox) convertView.findViewById(R.id.add_to_basket);

        // todo: why this doesn't work?
        addToBasketCheckBox.setSelected(isBasket);

        addToBasketCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isBasket) {
                    categoriesWithEntriesProvider.moveBackToOriginalCategory(groupPosition, entry);
                } else {
                    categoriesWithEntriesProvider.moveToBasket(groupPosition, entry);
                }
                notifyDataSetChanged();
            }
        });

        addToBasketCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
                    addToBasketCheckBox.setChecked(false);
                    // Code to display your message.
                }
            }
        });

        final TextView removeEntryTextView = (TextView) convertView.findViewById(R.id.remove);

        removeEntryTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoriesWithEntriesProvider.removeEntry(groupPosition, entry);
                notifyDataSetChanged();
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
        // categoryNameTextView.setTypeface(null, Typeface.BOLD);
        categoryNameTextView.setText(categoryName);

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
}