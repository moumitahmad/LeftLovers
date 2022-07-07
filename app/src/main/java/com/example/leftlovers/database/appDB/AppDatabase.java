package com.example.leftlovers.database.appDB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.leftlovers.model.Recipe;

@Database(entities = {Recipe.class}, version  = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract RecipeDao recipeDao();

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