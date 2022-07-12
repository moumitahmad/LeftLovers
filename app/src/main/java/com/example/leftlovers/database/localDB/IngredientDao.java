package com.example.leftlovers.database.localDB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.leftlovers.model.Ingredient;
import com.example.leftlovers.model.Recipe;

import java.util.List;

@Dao
public interface IngredientDao {

    @Query("SELECT * FROM ingredient")
    List<Ingredient> getAllIngredients();

   /* @Query("SELECT * FROM ingredient WHERE recipe_link=:URL")
    List<Recipe> searchRecipeByURL(String URL); */

    @Insert
    long insertIngredient(Ingredient ingredient);

    @Delete
    void delete(Ingredient ingredient);

    @Query("DELETE FROM recipe")
    void removeAllIngredients();
}
