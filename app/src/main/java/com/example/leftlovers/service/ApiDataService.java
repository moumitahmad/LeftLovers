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

    // Get Recipe by Category/Balance/Diet
    public void getRecipesByCategory(String searchtext, String filterQuery, int startID, int endID,ApiConnection.ListVolleyResponseListener listVolleyResponseListener) {
        apiConnection.getListByCategory(searchtext, filterQuery, startID, endID, listVolleyResponseListener);
    }

    // Get Ingredient
    public void getIngredient(String searchText, ApiConnection.IngredientResponseListener ingredientResponseListener) {
        apiConnection.getIngredient(searchText, ingredientResponseListener);
    }

    // Get Recipe List
    public void getRecipeList(String searchtext, ApiConnection.ListVolleyResponseListener listVolleyResponseListener) {
        apiConnection.getRecipeList(searchtext, listVolleyResponseListener);
    }

    // Get Recipe by Identifier (URL)
    public void getRecipeByIdentifier(String recipeUrl, ApiConnection.VolleyResponseListener volleyResponseListener) throws UnsupportedEncodingException {
        // URL needs to be encoded to send it to Api
        String url = URLEncoder.encode(recipeUrl, "UTF-8");
        apiConnection.getRecipeByIdentifier(url, volleyResponseListener);
    }

    // Get Random Recipe List
    public void getRandomRecipes(ApiConnection.ListVolleyResponseListener listVolleyResponseListener) {
        int range = (20 - 2) + 1;
        int numberOfMinIngredients = (int)(Math.random() * range) + 2;
        apiConnection.getRandomRecipes(numberOfMinIngredients, listVolleyResponseListener);
    }

    // Get possible filters to sort recipe results
    public void getPossibleFiltersFromAPI(ApiConnection.FilterVolleyResponseListener filterVolleyResponseListener) {
        apiConnection.getPossibleFiltersFromAPI(filterVolleyResponseListener);
    }

    // Get suggestions to ingredient names
    public  void getSuggest(String searchText, ApiConnection.SuggestVolleyResponseListener suggestVolleyResponseListener) {
        apiConnection.getSuggest(searchText, suggestVolleyResponseListener);
    }
}
