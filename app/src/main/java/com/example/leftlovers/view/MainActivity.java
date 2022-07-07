package com.example.leftlovers.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.util.Log;

import com.example.leftlovers.R;
import com.example.leftlovers.database.appDB.AppDatabase;
import com.example.leftlovers.model.Recipe;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // hide top app bar
        /*requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();*/

        setContentView(R.layout.activity_main);

        saveNewRecipe("Tomato Gravy", "http://www.edamam.com/ontologies/edamam.owl#recipe_1155648f37e539dc36b847ddbf7f53f7");
        loadRecipeList();

        // setup bottom app bar
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        NavController navController = navHostFragment.getNavController();
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        NavigationUI.setupWithNavController(bottomNav, navController);


    }

    private void loadRecipeList() {
        AppDatabase db = AppDatabase.getDbInstance(this.getApplicationContext());
        List<Recipe> recipeList = db.recipeDao().getAllRecipes();
        Log.d("USER 1", recipeList.get(0).getName());
        Log.d("USER 1", recipeList.get(1).getName());
    }

    private void saveNewRecipe(String recipeName, String recipeLink) {
        AppDatabase db  = AppDatabase.getDbInstance(this.getApplicationContext());

        Recipe recipe = new Recipe(recipeName);
        recipe.setLink(recipeLink);
        db.recipeDao().insertRecipe(recipe);

        // finish();

    }
}