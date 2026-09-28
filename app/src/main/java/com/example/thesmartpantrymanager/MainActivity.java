package com.example.thesmartpantrymanager;

import android.os.Bundle;
import android.content.Intent;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thesmartpantrymanager.adapter.PantryAdapter;
import com.example.thesmartpantrymanager.database.AppDataBase;
import com.example.thesmartpantrymanager.database.PantryItem;
import com.example.thesmartpantrymanager.database.RecipeSeeder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerPantry;
    private PantryAdapter pantryAdapter;
    private RecipeSeeder recipeSeeder;

    private AppDataBase database;

    private final ExecutorService databaseExecutor =
            Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerPantry = findViewById(R.id.recyclerPantry);

        recyclerPantry.setLayoutManager(
                new LinearLayoutManager(this)
        );

        pantryAdapter = new PantryAdapter(
                new ArrayList<>(),
                new PantryAdapter.OnPantryItemListener() {
                    @Override
                    public void onEdit(PantryItem pantryItem) {
                        editPantryItem(pantryItem);
                    }

                    @Override
                    public void onDelete(PantryItem pantryItem) {
                        deletePantryItem(pantryItem);
                    }
                }
        );

        recyclerPantry.setAdapter(pantryAdapter);

        database = AppDataBase.getInstance(this);
        
        recipeSeeder = new RecipeSeeder(this);
        recipeSeeder.seedRecipes();

        loadPantryItems();

        Button buttonAddIngredient =
                findViewById(R.id.buttonAddIngredient);

        buttonAddIngredient.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddEditIngredientActivity.class);
            startActivity(intent);
        });

        Button buttonSuggestedRecipes =
                findViewById(R.id.buttonSuggestedRecipes);

        buttonSuggestedRecipes.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SuggestedRecipesActivity.class);
            startActivity(intent);
        });

        Button buttonSettings =
                findViewById(R.id.buttonSettings);

        buttonSettings.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadPantryItems();
    }

    private void deletePantryItem(PantryItem pantryItem) {

        databaseExecutor.execute(() -> {

            database.pantryItemDao().delete(pantryItem);

            runOnUiThread(() -> {
                loadPantryItems();
            });
        });
    }

    private void editPantryItem(PantryItem pantryItem) {
        Intent intent = new Intent(this, AddEditIngredientActivity.class);
        intent.putExtra("INGREDIENT_ID", pantryItem.getId());
        startActivity(intent);
    }

    private void loadPantryItems() {

        databaseExecutor.execute(() -> {

            List<PantryItem> items =
                    database.pantryItemDao().getAll();

            runOnUiThread(() -> {
                pantryAdapter.updateItems(items);
            });
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        databaseExecutor.shutdown();
    }

}