package com.example.thesmartpantrymanager.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thesmartpantrymanager.R;
import com.example.thesmartpantrymanager.database.Recipe;

import java.util.List;

public class SuggestedRecipeAdapter
        extends RecyclerView.Adapter<SuggestedRecipeAdapter.RecipeViewHolder> {

    private List<Recipe> recipes;
    private final OnRecipeClickListener listener;

    public interface OnRecipeClickListener {
        void onRecipeClick(Recipe recipe);
    }

    public SuggestedRecipeAdapter(
            List<Recipe> recipes,
            OnRecipeClickListener listener) {

        this.recipes = recipes;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ingredient_suggested_recipe, parent, false);

        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull RecipeViewHolder holder,
            int position) {

        Recipe recipe = recipes.get(position);

        holder.textRecipeName.setText(recipe.getName());

        holder.itemView.setOnClickListener(v ->
                listener.onRecipeClick(recipe));
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public void updateRecipes(List<Recipe> newRecipes) {
        this.recipes = newRecipes;
        notifyDataSetChanged();
    }

    public static class RecipeViewHolder
            extends RecyclerView.ViewHolder {

        TextView textRecipeName;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);

            textRecipeName =
                    itemView.findViewById(R.id.textRecipeName);
        }
    }
}