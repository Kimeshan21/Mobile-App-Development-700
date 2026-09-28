package com.example.thesmartpantrymanager;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.thesmartpantrymanager.database.AppDataBase;
import com.example.thesmartpantrymanager.database.Recipe;
import com.example.thesmartpantrymanager.database.RecipeIngredient;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RecipeDetailActivity extends AppCompatActivity {

    private TextView textRecipeName;
    private TextView textRecipeIngredients;
    private TextView textRecipeInstructions;

    private AppDataBase database;
    private final ExecutorService databaseExecutor =
            Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        textRecipeName = findViewById(R.id.textRecipeName);
        textRecipeIngredients = findViewById(R.id.textRecipeIngredients);
        textRecipeInstructions = findViewById(R.id.textRecipeInstructions);

        database = AppDataBase.getInstance(this);

        int recipeId = getIntent().getIntExtra("recipe_id", -1);
        if (recipeId != -1) {
            loadRecipeDetails(recipeId);
        } else {
            Toast.makeText(this, "Recipe not found", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void loadRecipeDetails(int recipeId) {
        databaseExecutor.execute(() -> {
            Recipe recipe = database.recipeDao().getRecipeById(recipeId);
            List<RecipeIngredient> ingredients =
                    database.recipeDao().getIngredientsForRecipe(recipeId);

            runOnUiThread(() -> {
                if (recipe != null) {
                    textRecipeName.setText(recipe.getName());
                    textRecipeInstructions.setText(recipe.getInstructions());

                    StringBuilder sb = new StringBuilder();
                    for (RecipeIngredient ingredient : ingredients) {
                        sb.append("• ")
                                .append(ingredient.getIngredientName())
                                .append(": ")
                                .append(ingredient.getQuantity())
                                .append(" ")
                                .append(ingredient.getUnit())
                                .append("\n");
                    }
                    textRecipeIngredients.setText(sb.toString().trim());
                } else {
                    Toast.makeText(RecipeDetailActivity.this,
                            "Recipe not found", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        databaseExecutor.shutdown();
    }
}
