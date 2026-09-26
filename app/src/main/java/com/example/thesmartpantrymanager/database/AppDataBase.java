package com.example.thesmartpantrymanager.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {PantryItem.class}, version = 1, exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {
    public abstract PantryItemDao pantryItemDao();
}
