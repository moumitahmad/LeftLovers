package com.example.leftlovers.database.localDB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.leftlovers.model.Ingredient;
import com.example.leftlovers.model.Recipe;

@Database(entities = { Recipe.class, Ingredient.class }, version  = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract RecipeDao recipeDao();
    public abstract IngredientDao ingredientDao();

    private static AppDatabase INSTANCE;

    public static AppDatabase getDbInstance(Context context) {

        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "DB_RECIPE")
                    .allowMainThreadQueries()
                    .build();

        }
        return INSTANCE;
    }
}