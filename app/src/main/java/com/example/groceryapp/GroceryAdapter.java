package com.example.groceryapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GroceryAdapter extends BaseAdapter {

    private Context context;
    private String[] items;
    private int[] prices;

    public GroceryAdapter(Context context, String[] items, int[] prices) {
        this.context = context;
        this.items = items;
        this.prices = prices;
    }

    @Override
    public int getCount() {
        return items.length;
    }

    @Override
    public Object getItem(int position) {
        return items[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.grid_item, parent, false);
        }

        // Get references to the TextViews in the grid_item layout
        TextView itemName = convertView.findViewById(R.id.item_name);
        TextView itemPrice = convertView.findViewById(R.id.item_price);

        // Set item name and price
        itemName.setText(items[position]);
        itemPrice.setText("₹" + prices[position]);

        return convertView;
    }
}
