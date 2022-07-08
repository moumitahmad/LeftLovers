package com.example.leftlovers.service;

import android.content.Context;
import android.util.Log;

import com.example.leftlovers.database.localDB.AppDatabase;
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

    public void saveNewRecipe(Recipe recipe) {
        db.recipeDao().insertRecipe(recipe);

        // finish();
    }

    public void deleteRecipe(Recipe recipe) {
        db.recipeDao().delete(recipe);
    }
}
