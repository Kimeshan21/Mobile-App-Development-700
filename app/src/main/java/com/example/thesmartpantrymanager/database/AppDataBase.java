package com.example.thesmartpantrymanager.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(
        entities = {PantryItem.class, Recipe.class, RecipeIngredient.class},
        version = 1,
        exportSchema = false
)
public abstract class AppDataBase extends RoomDatabase {

    public abstract PantryItemDao pantryItemDao();

    public abstract RecipeDao recipeDao();

    private static volatile AppDataBase INSTANCE;

    public static AppDataBase getInstance(Context context) {

        if (INSTANCE == null) {

            synchronized (AppDataBase.class) {

                if (INSTANCE == null) {

                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDataBase.class,
                            "smart_pantry_database"
                    )
                    .fallbackToDestructiveMigration()
                    .build();
                }
            }
        }

        return INSTANCE;
    }
}