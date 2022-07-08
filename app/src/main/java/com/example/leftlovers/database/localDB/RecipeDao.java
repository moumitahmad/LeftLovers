package com.example.leftlovers.database.localDB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.leftlovers.model.Recipe;

import java.util.List;

@Dao
public interface RecipeDao {

    @Query("SELECT * FROM recipe")
    List<Recipe> getAllRecipes();


    @Insert
    void insertRecipe(Recipe... recipe);

    @Delete
    void delete(Recipe recipe);
}
