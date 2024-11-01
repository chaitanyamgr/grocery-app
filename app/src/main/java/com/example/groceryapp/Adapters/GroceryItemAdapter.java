package com.example.groceryapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.groceryapp.Models.CartItem;
import com.example.groceryapp.Models.GroceryItem;
import com.example.groceryapp.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroceryItemAdapter extends BaseAdapter {

    private Context context;
    private List<GroceryItem> groceryItems;
    private Map<String, CartItem> cartItems = new HashMap<>(); // Cart storage
    private OnItemAddedToCartListener listener; // Listener for cart updates

    public interface OnItemAddedToCartListener {
        void onItemAdded(GroceryItem item);
    }

    public GroceryItemAdapter(Context context, List<GroceryItem> groceryItems, OnItemAddedToCartListener listener) {
        this.context = context;
        this.groceryItems = groceryItems;
        this.listener = listener; // Assign the listener
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        }

        GroceryItem item = groceryItems.get(position);

        ImageView itemImage = convertView.findViewById(R.id.item_image);
        TextView itemName = convertView.findViewById(R.id.item_name);
        TextView itemPrice = convertView.findViewById(R.id.item_price);
        Button addToCartButton = convertView.findViewById(R.id.add_to_cart_button);

        itemImage.setImageResource(item.getImageResId());
        itemName.setText(item.getName());
        itemPrice.setText("₹" + item.getPrice());

        addToCartButton.setOnClickListener(v -> addToCart(item));

        return convertView;
    }

    private void addToCart(GroceryItem item) {
        if (cartItems.containsKey(item.getName())) {
            CartItem cartItem = cartItems.get(item.getName());
            cartItem.setQuantity(cartItem.getQuantity() + 1);
        } else {
            cartItems.put(item.getName(), new CartItem(item.getImageResId(),1, item.getPrice(),item.getName()));
        }

        // Notify the activity to update the total amount
        if (listener != null) {
            listener.onItemAdded(item);
        }

        Toast.makeText(context, item.getName() + " added to cart!", Toast.LENGTH_SHORT).show();
    }

    public Map<String, CartItem> getCartItems() {
        return cartItems;
    }
}