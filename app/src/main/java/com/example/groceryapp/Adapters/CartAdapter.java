package com.example.groceryapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.groceryapp.Models.CartItem;
import com.example.groceryapp.R;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private final List<CartItem> cartItems;
    private final Context context;
    private TotalUpdateListener totalUpdateListener;

    public CartAdapter(Context context, List<CartItem> cartItems) {
        this.context = context;
        this.cartItems = cartItems;
        }

    public void setTotalUpdateListener(TotalUpdateListener listener) {
        this.totalUpdateListener = listener;
    }

    private void updateTotal() {
        int newTotal = 0;
        for (CartItem item : cartItems) {
            newTotal += item.getPrice() * item.getQuantity();
        }
        if (totalUpdateListener != null) {
            totalUpdateListener.onTotalUpdated(newTotal);
        }
    }

    // Inside CartAdapter
    public void calculateInitialTotal() {
        updateTotal();
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem cartItem = cartItems.get(position);
        holder.itemName.setText(cartItem.getName());
        String priceText = String.format("₹%d", cartItem.getPrice());
        holder.itemPrice.setText(priceText);
        holder.itemQuantity.setText(String.valueOf(cartItem.getQuantity()));

        // Add click listeners for the quantity buttons
        holder.plusButton.setOnClickListener(v -> {
            cartItem.setQuantity(cartItem.getQuantity() + 1);
            notifyItemChanged(position);
            updateTotal(); // Update total when quantity is increased
        });

        holder.minusButton.setOnClickListener(v -> {
            if (cartItem.getQuantity() > 1) {
                cartItem.setQuantity(cartItem.getQuantity() - 1);
                notifyItemChanged(position);
                updateTotal(); // Update total when quantity is decreased
            }
        });

        // Remove item from cart
        holder.removeButton.setOnClickListener(v -> {
            cartItems.remove(position);
            notifyItemRemoved(position);
            updateTotal(); // Update total when an item is removed
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {

        TextView itemName, itemPrice, itemQuantity;
        Button plusButton, minusButton;
        ImageView removeButton;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.item_name);
            itemPrice = itemView.findViewById(R.id.item_price);
            itemQuantity = itemView.findViewById(R.id.item_quantity);
            plusButton = itemView.findViewById(R.id.plus_button);
            minusButton = itemView.findViewById(R.id.minus_button);
            removeButton = itemView.findViewById(R.id.remove_button);
        }
    }

    public interface TotalUpdateListener {
        void onTotalUpdated(int newTotal);
    }
}

