package com.example.leftlovers.service;

import android.content.Context;
import android.widget.Toast;

import com.example.leftlovers.database.ApiConnection;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class ReceipeDataService {

    ApiConnection apiConnection;
    Context context;

    public ReceipeDataService(Context context) {
        this.context = context;
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
    //Weitere api einbinden
    public void getIngredient(String searchText, ApiConnection.VolleyResponseListener volleyResponseListener) {
     //   apiConnection.getIngredient(searchText, volleyResponseListener);
    }



    //Get Recipe List

    public void getList(String searchtext, ApiConnection.ListVolleyResponseListener listVolleyResponseListener) {
        apiConnection.getList("Tomato", listVolleyResponseListener);
    }

    //Get Recipe by Identifier (URL)

    public void getUrl(String recipeUrl, ApiConnection.VolleyResponseListener volleyResponseListener) throws UnsupportedEncodingException {
        // URL needs to be encoded to send it to Api
        // Url zum Debugen :
        //  recipeUrl = "http%3A%2F%2Fwww.edamam.com%2Fontologies%2Fedamam.owl%23recipe_1b6dfeaf0988f96b187c7c9bb69a14fa&app_id=23b2fea2";

        String url = URLEncoder.encode(recipeUrl, "UTF-8");
        apiConnection.getRecipeByIdentifier(recipeUrl, volleyResponseListener);
    }
}
