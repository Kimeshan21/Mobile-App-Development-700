package com.example.thesmartpantrymanager;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.thesmartpantrymanager.database.AppDataBase;
import com.example.thesmartpantrymanager.database.PantryItem;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AddEditIngredientActivity extends AppCompatActivity {

    private EditText editIngredientName;
    private EditText editQuantity;
    private EditText editUnit;
    private EditText editExpiryDate;

    private AppDataBase database;
    private int ingredientId = -1;
    private boolean isEditMode = false;

    private final ExecutorService databaseExecutor =
            Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_ingredient);

        editIngredientName = findViewById(R.id.editIngredientName);
        editQuantity = findViewById(R.id.editQuantity);
        editUnit = findViewById(R.id.editUnit);
        editExpiryDate = findViewById(R.id.editExpiryDate);

        Button buttonSaveIngredient =
                findViewById(R.id.buttonSaveIngredient);

        database = AppDataBase.getInstance(this);

        ingredientId = getIntent().getIntExtra("ingredient_ID", -1);
        if (ingredientId != -1) {
            isEditMode = true;
            loadIngredient();
        }

        buttonSaveIngredient.setOnClickListener(v -> saveIngredient());
    }

    private void loadIngredient() {

        databaseExecutor.execute(() -> {
            PantryItem pantryItem = database.pantryItemDao().getById(ingredientId);
            runOnUiThread(() -> {
                if (pantryItem != null) {
                    editIngredientName.setText(pantryItem.getName());
                    editQuantity.setText(String.valueOf(pantryItem.getQuantity()));
                    editUnit.setText(pantryItem.getUnit());
                    editExpiryDate.setText(pantryItem.getExpiryDate());
                }
            });
        });
    }

    private void saveIngredient() {

        String name = editIngredientName.getText()
                .toString()
                .trim();

        String quantityText = editQuantity.getText()
                .toString()
                .trim();

        String unit = editUnit.getText()
                .toString()
                .trim();

        String expiryDate = editExpiryDate.getText()
                .toString()
                .trim();

        // Validation
        if (name.isEmpty()) {
            editIngredientName.setError(
                    "Please enter an ingredient name"
            );
            editIngredientName.requestFocus();
            return;
        }

        if (quantityText.isEmpty()) {
            editQuantity.setError(
                    "Please enter a quantity"
            );
            editQuantity.requestFocus();
            return;
        }

        if (unit.isEmpty()) {
            editUnit.setError(
                    "Please enter a unit"
            );
            editUnit.requestFocus();
            return;
        }

        double quantity;

        try {
            quantity = Double.parseDouble(quantityText);
        } catch (NumberFormatException e) {
            editQuantity.setError(
                    "Please enter a valid quantity"
            );
            editQuantity.requestFocus();
            return;
        }

        if (quantity <= 0) {
            editQuantity.setError(
                    "Quantity must be greater than zero"
            );
            editQuantity.requestFocus();
            return;
        }

        if (isEditMode) {
            databaseExecutor.execute(() -> {
                PantryItem pantryItem = database.pantryItemDao().getById(ingredientId);
                if (pantryItem != null) {
                    pantryItem.setName(name);
                    pantryItem.setQuantity(quantity);
                    pantryItem.setUnit(unit);
                    pantryItem.setExpiryDate(expiryDate);

                    database.pantryItemDao().update(pantryItem);

                    runOnUiThread(() -> {
                        Toast.makeText(
                                this,
                                "Ingredient updated successfully",
                                Toast.LENGTH_SHORT
                        ).show();
                        finish();
                    });
                }
            });
        } else {
            PantryItem pantryItem = new PantryItem(
                    name,
                    quantity,
                    unit,
                    expiryDate
            );

            databaseExecutor.execute(() -> {
                database.pantryItemDao().insert(pantryItem);

                runOnUiThread(() -> {
                    Toast.makeText(
                            this,
                            "Ingredient added successfully",
                            Toast.LENGTH_SHORT
                    ).show();
                    finish();
                });
            });
        }  
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        databaseExecutor.shutdown();
    }
}