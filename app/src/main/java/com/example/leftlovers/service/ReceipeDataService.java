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
    public void getRecipe(String searchtext, ApiConnection.VolleyResponseListener volleyResponseListener) {
        apiConnection.getRecipe("Tomato", volleyResponseListener);
    }

    //Get Ingridient

    //Get Recipe URL

    //Get Recipe List
}
