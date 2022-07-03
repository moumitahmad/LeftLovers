package com.example.leftlovers.service;

import android.content.Context;
import com.example.leftlovers.database.ApiConnection;
import com.example.leftlovers.model.Recipe;

public class ReceipeDataService {

    ApiConnection apiConnection;

    public ReceipeDataService(Context context) {
        apiConnection = new ApiConnection(context);
    }


    // Get Single Recipe

    public void getRecipe(String searchtext, ApiConnection.VolleyResponseListener volleyResponseListener) {
        apiConnection.getRecipe(searchtext, volleyResponseListener);
    }

    //Get Recipe by Category/Balance/Diet usw was es da noch so gibt
    //überlegen ob man alle categorien in eine funktion rein bekommt
    // und ob man getrennte funktionen braucht wenn "searchtext" leer usw

    //Get Ingridient
    //
    public void getIngredient(String searchText, ApiConnection.VolleyResponseListener volleyResponseListener) {
        apiConnection.getIngredient(searchText, volleyResponseListener);
    }



    //Get Recipe List

    public void getList(String searchtext, ApiConnection.VolleyResponseListener volleyResponseListener) {
        apiConnection.getRecipe("Tomato", volleyResponseListener);
    }

    //Get Recipe by Identifier (URL)
    // Hier wird ein Encoder für urls benoetigt

    public void getUrl(String recipeUrl, ApiConnection.VolleyResponseListener volleyResponseListener) {
        // URL ENCODER !!!
        recipeUrl = "http%3A%2F%2Fwww.edamam.com%2Fontologies%2Fedamam.owl%23recipe_1b6dfeaf0988f96b187c7c9bb69a14fa&app_id=23b2fea2";
        apiConnection.getRecipeByIdentifier(recipeUrl, volleyResponseListener);
    }
}
