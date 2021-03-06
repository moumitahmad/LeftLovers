package com.example.leftlovers.service;

import android.content.Context;
import android.util.Log;

import com.example.leftlovers.database.localDB.AppDatabase;
import com.example.leftlovers.model.Ingredient;
import com.example.leftlovers.model.Recipe;

import java.util.List;

public class DatabaseService {

    Context context;
    AppDatabase db;

    public DatabaseService(Context context) {
        this.context = context;
        db = AppDatabase.getDbInstance(context);
    }

    public List<Recipe> loadRecipeList() {
        return db.recipeDao().getAllRecipes();
    }

    public List<Recipe> searchRecipeByURL(String url) {
        List<Recipe> recipeList = db.recipeDao().searchRecipeByURL(url);
        return recipeList;
    }

    public int saveNewRecipe(Recipe recipe) {
       return (int) db.recipeDao().insertRecipe(recipe);
    }

    public void deleteRecipe(Recipe recipe) {
        db.recipeDao().delete(recipe);
    }

    // for debugging
    public void removeAllRecipes() {
        db.recipeDao().removeAllRecipes();
    }


    public List<Ingredient> loadIngredientList() {
        return db.ingredientDao().getAllIngredients();
    }

    public int saveNewIngredient(Ingredient ingredient) {
        return (int) db.ingredientDao().insertIngredient(ingredient);
    }

    public void updateIngredient(Ingredient ingredient) {
        db.ingredientDao().updateIngredient(ingredient);
    }

    public void deleteIngredient(Ingredient ingredient) {
        db.ingredientDao().deleteIngredient(ingredient);
    }

    // for debugging
    public void removeAllIngredients() {
        db.ingredientDao().removeAllIngredients();
    }
}
