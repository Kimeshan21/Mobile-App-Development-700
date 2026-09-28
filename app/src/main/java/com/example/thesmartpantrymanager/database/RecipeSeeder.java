package com.example.thesmartpantrymanager.database;

import android.content.Context;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RecipeSeeder {

    private final AppDataBase database;
    private final ExecutorService executorService =
            Executors.newSingleThreadExecutor();

    public RecipeSeeder(Context context) {
        database = AppDataBase.getInstance(context);
    }

    public void seedRecipes() {

        executorService.execute(() -> {

            List<Recipe> existingRecipes =
                    database.recipeDao().getAllRecipes();

            // Only seed recipes if the database is empty
            if (!existingRecipes.isEmpty()) {
                return;
            }

            addRecipe(
                    "Chicken Stir Fry",
                    "Cook the chicken, add vegetables and stir fry with soy sauce.",
                    new String[][]{
                            {"Chicken", "500", "g"},
                            {"Rice", "2", "cup"},
                            {"Carrot", "2", "item"},
                            {"Soy Sauce", "2", "tbsp"}
                    }
            );

            addRecipe(
                    "Vegetable Fried Rice",
                    "Fry the vegetables, add cooked rice and season to taste.",
                    new String[][]{
                            {"Rice", "2", "cup"},
                            {"Carrot", "1", "item"},
                            {"Peas", "1", "cup"},
                            {"Egg", "2", "item"}
                    }
            );

            addRecipe(
                    "Spaghetti Bolognese",
                    "Cook the beef with tomato sauce and serve with spaghetti.",
                    new String[][]{
                            {"Spaghetti", "250", "g"},
                            {"Beef Mince", "500", "g"},
                            {"Tomato Sauce", "1", "cup"},
                            {"Onion", "1", "item"}
                    }
            );

            addRecipe(
                    "Chicken Pasta",
                    "Cook pasta and chicken, combine with sauce and serve.",
                    new String[][]{
                            {"Pasta", "250", "g"},
                            {"Chicken", "300", "g"},
                            {"Tomato Sauce", "1", "cup"},
                            {"Cheese", "100", "g"}
                    }
            );

            addRecipe(
                    "Tomato Pasta",
                    "Cook pasta and combine with tomato sauce and onion.",
                    new String[][]{
                            {"Pasta", "250", "g"},
                            {"Tomato Sauce", "1", "cup"},
                            {"Onion", "1", "item"},
                            {"Garlic", "2", "clove"}
                    }
            );

            addRecipe(
                    "Vegetable Omelette",
                    "Beat the eggs, add vegetables and cook until set.",
                    new String[][]{
                            {"Egg", "3", "item"},
                            {"Tomato", "1", "item"},
                            {"Onion", "1", "item"},
                            {"Cheese", "50", "g"}
                    }
            );

            addRecipe(
                    "Chicken Curry",
                    "Cook chicken with onion and curry spices, then simmer.",
                    new String[][]{
                            {"Chicken", "500", "g"},
                            {"Onion", "1", "item"},
                            {"Tomato", "2", "item"},
                            {"Curry Powder", "2", "tbsp"}
                    }
            );

            addRecipe(
                    "Beef Stew",
                    "Brown the beef, add vegetables and simmer until tender.",
                    new String[][]{
                            {"Beef", "500", "g"},
                            {"Potato", "3", "item"},
                            {"Carrot", "2", "item"},
                            {"Onion", "1", "item"}
                    }
            );

            addRecipe(
                    "Tuna Sandwich",
                    "Mix tuna with mayonnaise and serve between bread slices.",
                    new String[][]{
                            {"Bread", "2", "slice"},
                            {"Tuna", "1", "can"},
                            {"Mayonnaise", "2", "tbsp"}
                    }
            );

            addRecipe(
                    "Grilled Cheese Sandwich",
                    "Place cheese between bread and grill until golden.",
                    new String[][]{
                            {"Bread", "2", "slice"},
                            {"Cheese", "100", "g"},
                            {"Butter", "1", "tbsp"}
                    }
            );

            addRecipe(
                    "Vegetable Soup",
                    "Cook all vegetables in stock until soft.",
                    new String[][]{
                            {"Potato", "2", "item"},
                            {"Carrot", "2", "item"},
                            {"Onion", "1", "item"},
                            {"Vegetable Stock", "2", "cup"}
                    }
            );

            addRecipe(
                    "Chicken Soup",
                    "Cook chicken and vegetables in stock until fully cooked.",
                    new String[][]{
                            {"Chicken", "300", "g"},
                            {"Carrot", "2", "item"},
                            {"Onion", "1", "item"},
                            {"Chicken Stock", "2", "cup"}
                    }
            );

            addRecipe(
                    "Pancakes",
                    "Mix the ingredients into a batter and cook in a pan.",
                    new String[][]{
                            {"Flour", "2", "cup"},
                            {"Milk", "1.5", "cup"},
                            {"Egg", "2", "item"},
                            {"Sugar", "2", "tbsp"}
                    }
            );

            addRecipe(
                    "French Toast",
                    "Dip bread in the egg mixture and fry until golden.",
                    new String[][]{
                            {"Bread", "4", "slice"},
                            {"Egg", "2", "item"},
                            {"Milk", "0.5", "cup"},
                            {"Sugar", "1", "tbsp"}
                    }
            );

            addRecipe(
                    "Rice and Beans",
                    "Cook the beans and combine with cooked rice.",
                    new String[][]{
                            {"Rice", "2", "cup"},
                            {"Beans", "1", "can"},
                            {"Onion", "1", "item"},
                            {"Tomato", "1", "item"}
                    }
            );

            addRecipe(
                    "Beef Burger",
                    "Cook the beef patty and assemble the burger.",
                    new String[][]{
                            {"Beef Mince", "250", "g"},
                            {"Burger Bun", "2", "item"},
                            {"Cheese", "50", "g"},
                            {"Tomato", "1", "item"}
                    }
            );

            addRecipe(
                    "Chicken Wrap",
                    "Cook chicken and assemble with vegetables in a wrap.",
                    new String[][]{
                            {"Chicken", "250", "g"},
                            {"Tortilla Wrap", "2", "item"},
                            {"Lettuce", "1", "cup"},
                            {"Tomato", "1", "item"}
                    }
            );

            addRecipe(
                    "Vegetable Stir Fry",
                    "Stir fry the vegetables and serve with cooked rice.",
                    new String[][]{
                            {"Rice", "2", "cup"},
                            {"Carrot", "2", "item"},
                            {"Broccoli", "1", "cup"},
                            {"Soy Sauce", "2", "tbsp"}
                    }
            );
        });
    }

    private void addRecipe(
            String recipeName,
            String instructions,
            String[][] ingredients) {

        Recipe recipe = new Recipe(
                recipeName,
                instructions
        );

        long recipeId =
                database.recipeDao().insertRecipe(recipe);

        for (String[] ingredient : ingredients) {

            RecipeIngredient recipeIngredient =
                    new RecipeIngredient(
                            (int) recipeId,
                            ingredient[0],
                            Double.parseDouble(ingredient[1]),
                            ingredient[2]
                    );

            database.recipeDao().insertIngredient(
                    recipeIngredient
            );
        }
    }
}