package com.example.thesmartpantrymanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thesmartpantrymanager.adapter.SuggestedRecipeAdapter;
import com.example.thesmartpantrymanager.database.AppDataBase;
import com.example.thesmartpantrymanager.database.PantryItem;
import com.example.thesmartpantrymanager.database.Recipe;
import com.example.thesmartpantrymanager.database.RecipeMatcher;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SuggestedRecipesActivity extends AppCompatActivity {

    private RecyclerView recyclerSuggestedRecipes;
    private TextView textEmptyRecipes;

    private SuggestedRecipeAdapter adapter;

    private AppDataBase database;
    private RecipeMatcher recipeMatcher;

    private ExecutorService databaseExecutor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_suggested_recipes);

        recyclerSuggestedRecipes =
                findViewById(R.id.recyclerSuggestedRecipes);

        textEmptyRecipes =
                findViewById(R.id.textEmptyRecipes);

        recyclerSuggestedRecipes.setLayoutManager(
                new LinearLayoutManager(this)
        );

        adapter = new SuggestedRecipeAdapter(
                new ArrayList<>(),
                recipe -> openRecipeDetails(recipe)
        );

        recyclerSuggestedRecipes.setAdapter(adapter);

        database = AppDataBase.getInstance(this);

        recipeMatcher = new RecipeMatcher(database);

        databaseExecutor = Executors.newSingleThreadExecutor();

        loadSuggestedRecipes();
    }

    private void loadSuggestedRecipes() {

        databaseExecutor.execute(() -> {

            List<PantryItem> pantryItems =
                    database.pantryItemDao().getAll();

            List<Recipe> allRecipes =
                    database.recipeDao().getAllRecipes();

            List<Recipe> matchingRecipes =
                    new ArrayList<>();

            for (Recipe recipe : allRecipes) {

                if (recipeMatcher.isRecipeMatch(
                        recipe,
                        pantryItems)) {

                    matchingRecipes.add(recipe);
                }
            }

            runOnUiThread(() -> {

                adapter.updateRecipes(matchingRecipes);

                if (matchingRecipes.isEmpty()) {

                    recyclerSuggestedRecipes
                            .setVisibility(View.GONE);

                    textEmptyRecipes
                            .setVisibility(View.VISIBLE);

                } else {

                    recyclerSuggestedRecipes
                            .setVisibility(View.VISIBLE);

                    textEmptyRecipes
                            .setVisibility(View.GONE);
                }
            });
        });
    }

    private void openRecipeDetails(Recipe recipe) {

        Intent intent = new Intent(
                SuggestedRecipesActivity.this,
                RecipeDetailActivity.class
        );

        intent.putExtra("recipe_id", recipe.getId());

        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (databaseExecutor != null) {
            loadSuggestedRecipes();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (databaseExecutor != null) {
            databaseExecutor.shutdown();
        }
    }
}