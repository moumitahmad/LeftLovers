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

    public void loadRecipeList() {
        List<Recipe> recipeList = db.recipeDao().getAllRecipes();
        for (Recipe r:recipeList) {
            Log.i("IIIIIIIIIIIIICH BIN IN DER DB ", r.getName());
        }
    }

    public List<Recipe> searchRecipeByURL(String url) {
        List<Recipe> recipeList = db.recipeDao().searchRecipeByURL(url);
        return recipeList;
    }

    public int saveNewRecipe(Recipe recipe) {
       return (int) db.recipeDao().insertRecipe(recipe);
        // finish();
    }

    public void deleteRecipe(Recipe recipe) {
        db.recipeDao().delete(recipe);
    }

    public void removeAllRecipes() {
        db.recipeDao().removeAllRecipes();
    }

    public void loadIngredientList() {
        List<Ingredient> ingredientList = db.ingredientDao().getAllIngredients();
        for (Ingredient i:ingredientList) {
            Log.i("IIIIIIIIIIIIICH BIN IN DER DB ", i.getName());
        }
    }


    public int saveNewIngredient(Ingredient ingredient) {
        return (int) db.ingredientDao().insertIngredient(ingredient);
        // finish();
    }

    public void deleteIngredient(Ingredient ingredient) {
        db.ingredientDao().delete(ingredient);
    }

    public void removeAllIngredients() {
        db.ingredientDao().removeAllIngredients();
    }
}
