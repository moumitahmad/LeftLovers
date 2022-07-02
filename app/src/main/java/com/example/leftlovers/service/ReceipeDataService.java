package com.example.leftlovers.service;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leftlovers.R;
import com.example.leftlovers.database.ApiConnection;
import com.example.leftlovers.model.Recipe;
import com.example.leftlovers.view.RecipeDetailFragment;

public class ReceipeDataService {

    ApiConnection apiConnection;
    Recipe choosenRecipe;

    public ReceipeDataService(Context context) {
        apiConnection = new ApiConnection(context);
    }

    // Get Single Recipe
    public Recipe getRecipe() {
        apiConnection.getList("Tomato", new ApiConnection.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Log.d("Error", "Sth went wrong");
            }

            @Override
            public void onResponse(Recipe recipeName) {
                //  Toast.makeText(getActivity(), "Returned URL" + recipeURL, Toast.LENGTH_SHORT).show();
                //choosenRecipe = new Recipe(recipeName, " ", null, " ", "www.google.com");
                choosenRecipe = recipeName;
                Log.d("Rezept übergeben ", choosenRecipe.getName());
            }
        });
        return choosenRecipe;
    }

    //Get Ingridient

    //Get Recipe URL

    //Get Recipe List
}
