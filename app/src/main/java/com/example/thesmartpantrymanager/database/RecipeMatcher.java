package com.example.thesmartpantrymanager.database;

import java.util.List;
import java.util.Locale;

public class RecipeMatcher {

    private final AppDataBase database;

    public RecipeMatcher(AppDataBase database) {
        this.database = database;
    }

    /**
     * Returns true only if the pantry contains
     * every ingredient required by the recipe
     * in a sufficient quantity.
     */
    public boolean isRecipeMatch(Recipe recipe, List<PantryItem> pantryItems) {

        List<RecipeIngredient> requiredIngredients =
                database.recipeDao().getIngredientsForRecipe(recipe.getId());

        // Check every required ingredient
        for (RecipeIngredient required : requiredIngredients) {

            boolean ingredientMatched = false;

            for (PantryItem pantryItem : pantryItems) {

                // Check whether the ingredient names match
                if (!normalizeName(pantryItem.getName())
                        .equals(normalizeName(required.getIngredientName()))) {
                    continue;
                }

                // Convert pantry quantity into the recipe's required unit
                Double pantryQuantity =
                        convertQuantity(
                                pantryItem.getQuantity(),
                                pantryItem.getUnit(),
                                required.getUnit()
                        );

                if (pantryQuantity != null
                        && pantryQuantity >= required.getQuantity()) {

                    ingredientMatched = true;
                    break;
                }
            }

            // If even ONE ingredient is missing or insufficient,
            // the recipe does not match.
            if (!ingredientMatched) {
                return false;
            }
        }

        return true;
    }

    /**
     * Normalises ingredient names so that simple
     * singular/plural differences do not prevent matching.
     */
    private String normalizeName(String name) {

        if (name == null) {
            return "";
        }

        String normalized = name.trim().toLowerCase(Locale.ROOT);

        if (normalized.endsWith("ies")) {
            normalized = normalized.substring(
                    0, normalized.length() - 3
            ) + "y";
        } else if (normalized.endsWith("es")) {
            normalized = normalized.substring(
                    0, normalized.length() - 2
            );
        } else if (normalized.endsWith("s")) {
            normalized = normalized.substring(
                    0, normalized.length() - 1
            );
        }

        return normalized;
    }

    /**
     * Converts compatible units into the recipe's unit.
     *
     * Returns null when the units are incompatible.
     */
    private Double convertQuantity(
            double quantity,
            String pantryUnit,
            String requiredUnit) {

        String pantry = normalizeUnit(pantryUnit);
        String required = normalizeUnit(requiredUnit);

        // Same unit
        if (pantry.equals(required)) {
            return quantity;
        }

        // Grams -> kilograms
        if (pantry.equals("g") && required.equals("kg")) {
            return quantity / 1000.0;
        }

        // Kilograms -> grams
        if (pantry.equals("kg") && required.equals("g")) {
            return quantity * 1000.0;
        }

        // Millilitres -> litres
        if (pantry.equals("ml") && required.equals("l")) {
            return quantity / 1000.0;
        }

        // Litres -> millilitres
        if (pantry.equals("l") && required.equals("ml")) {
            return quantity * 1000.0;
        }

        // Incompatible units
        return null;
    }

    /**
     * Converts common unit variations into one standard form.
     */
    private String normalizeUnit(String unit) {

        if (unit == null) {
            return "";
        }

        String normalized = unit.trim().toLowerCase(Locale.ROOT);

        switch (normalized) {

            case "gram":
            case "grams":
            case "g":
                return "g";

            case "kilogram":
            case "kilograms":
            case "kg":
                return "kg";

            case "millilitre":
            case "millilitres":
            case "milliliter":
            case "milliliters":
            case "ml":
                return "ml";

            case "litre":
            case "litres":
            case "liter":
            case "liters":
            case "l":
                return "l";

            case "cup":
            case "cups":
                return "cup";

            case "tablespoon":
            case "tablespoons":
            case "tbsp":
                return "tbsp";

            case "teaspoon":
            case "teaspoons":
            case "tsp":
                return "tsp";

            case "item":
            case "items":
                return "item";

            case "slice":
            case "slices":
                return "slice";

            case "can":
            case "cans":
                return "can";

            case "clove":
            case "cloves":
                return "clove";

            default:
                return normalized;
        }
    }
}
