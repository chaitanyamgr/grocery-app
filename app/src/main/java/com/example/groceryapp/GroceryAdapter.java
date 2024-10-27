package com.example.groceryapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class GroceryAdapter extends BaseAdapter {

    private final Context context;
    private final List<GroceryItem> groceryItems;

    public GroceryAdapter(Context context, List<GroceryItem> groceryItems) {
        this.context = context;
        this.groceryItems = groceryItems;
    }

    @Override
    public int getCount() {
        return groceryItems.size();
    }

    @Override
    public Object getItem(int position) {
        return groceryItems.get(position);
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

        GroceryItem item = groceryItems.get(position);

        ImageView imageView = convertView.findViewById(R.id.item_image);
        TextView nameTextView = convertView.findViewById(R.id.item_name);
        TextView priceTextView = convertView.findViewById(R.id.item_price);

        nameTextView.setText(item.getName());
        priceTextView.setText("₹" + item.getPrice());
        imageView.setImageResource(item.getImageResId());

        return convertView;
    }
}