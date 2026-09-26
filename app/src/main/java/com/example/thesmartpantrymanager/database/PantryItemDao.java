package com.example.thesmartpantrymanager.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PantryItemDao {
    @Insert
    void insert(PantryItem pantryItem);
    
    @Query("SELECT * FROM pantry_items ORDER BY name ASC")
    List<PantryItem> getAllItems();

    @Update
    void update(PantryItem pantryItem);

    @Delete
    void delete(PantryItem pantryItem);

    @Query("SELECT * FROM pantry_items WHERE id = :id LIMIT 1")
    PantryItem getItemById(int id);
}