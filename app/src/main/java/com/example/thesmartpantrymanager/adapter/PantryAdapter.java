package com.example.thesmartpantrymanager.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thesmartpantrymanager.R;
import com.example.thesmartpantrymanager.database.PantryItem;

import java.util.List;

public class PantryAdapter
        extends RecyclerView.Adapter<PantryAdapter.PantryViewHolder> {

    private List<PantryItem> pantryItems;

    private final OnPantryItemListener listener;

    public interface OnPantryItemListener {

        void onEdit(PantryItem pantryItem);

        void onDelete(PantryItem pantryItem);
    }

    public PantryAdapter(
            List<PantryItem> pantryItems,
            OnPantryItemListener listener) {

        this.pantryItems = pantryItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PantryViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pantry, parent, false);

        return new PantryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull PantryViewHolder holder,
            int position) {

        PantryItem item = pantryItems.get(position);

        holder.textIngredientName.setText(item.getName());

        String quantityText =
                "Quantity: "
                        + item.getQuantity()
                        + " "
                        + item.getUnit();

        holder.textIngredientQuantity.setText(quantityText);

        if (item.getExpiryDate() != null
                && !item.getExpiryDate().isEmpty()) {

            holder.textIngredientExpiry.setText(
                    "Expiry: " + item.getExpiryDate()
            );

        } else {

            holder.textIngredientExpiry.setText(
                    "Expiry: Not specified"
            );
        }

        holder.buttonEdit.setOnClickListener(v ->
                listener.onEdit(item)
        );

        holder.buttonDelete.setOnClickListener(v ->
                listener.onDelete(item)
        );
    }

    @Override
    public int getItemCount() {
        return pantryItems.size();
    }

    public void updateItems(List<PantryItem> newItems) {

        this.pantryItems = newItems;

        notifyDataSetChanged();
    }

    public static class PantryViewHolder
            extends RecyclerView.ViewHolder {

        TextView textIngredientName;
        TextView textIngredientQuantity;
        TextView textIngredientExpiry;

        Button buttonEdit;
        Button buttonDelete;

        public PantryViewHolder(@NonNull View itemView) {

            super(itemView);

            textIngredientName =
                    itemView.findViewById(
                            R.id.textIngredientName
                    );

            textIngredientQuantity =
                    itemView.findViewById(
                            R.id.textIngredientQuantity
                    );

            textIngredientExpiry =
                    itemView.findViewById(
                            R.id.textIngredientExpiry
                    );

            buttonEdit =
                    itemView.findViewById(
                            R.id.buttonEdit
                    );

            buttonDelete =
                    itemView.findViewById(
                            R.id.buttonDelete
                    );
        }
    }
}
