package com.example.leftlovers.service;

import android.content.Context;

import com.example.leftlovers.database.api.ApiConnection;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class ApiDataService {

    ApiConnection apiConnection;
    Context context;

    public ApiDataService(Context context) {
        this.context = context;
        apiConnection = new ApiConnection(context);
    }


    // Get Single Recipe

    public void getRecipe(String searchtext, ApiConnection.VolleyResponseListener volleyResponseListener) {
        apiConnection.getRecipe(searchtext, volleyResponseListener);
    }

    //Get Recipe by Category/Balance/Diet

    public void getByCategory(String searchtext, String category, ApiConnection.ListVolleyResponseListener listVolleyResponseListener) {
        apiConnection.getListByCategory(searchtext, category, listVolleyResponseListener);
    }

    //Get Ingridient
    public void getIngredient(String searchText, ApiConnection.IngredientResponseListener ingredientResponseListener) {
        apiConnection.getIngredient(searchText, ingredientResponseListener);
    }



    //Get Recipe List

    public void getRecipeList(String searchtext, ApiConnection.ListVolleyResponseListener listVolleyResponseListener) {
        apiConnection.getRecipeList(searchtext, listVolleyResponseListener);
    }

    //Get Recipe by Identifier (URL)

    public void getUrl(String recipeUrl, ApiConnection.VolleyResponseListener volleyResponseListener) throws UnsupportedEncodingException {
        // URL needs to be encoded to send it to Api
        // Url zum Debugen :
        //  recipeUrl = "http%3A%2F%2Fwww.edamam.com%2Fontologies%2Fedamam.owl%23recipe_1b6dfeaf0988f96b187c7c9bb69a14fa&app_id=23b2fea2";

        String url = URLEncoder.encode(recipeUrl, "UTF-8");
        apiConnection.getRecipeByIdentifier(recipeUrl, volleyResponseListener);
    }

    //Get Random Recipe List
    public void getRandom(ApiConnection.ListVolleyResponseListener listVolleyResponseListener) {
        int range = (20 - 2) + 1;
        int numberOfMinIngredients = (int)(Math.random() * range) + 2;
        apiConnection.getRandom(numberOfMinIngredients, listVolleyResponseListener);
    }

    //Get suggestions to ingredient names
    public  void getSuggest(String searchText, ApiConnection.SuggestVolleyResponseListener suggestVolleyResponseListener) {
        apiConnection.getSuggest(searchText, suggestVolleyResponseListener);
    }
}
