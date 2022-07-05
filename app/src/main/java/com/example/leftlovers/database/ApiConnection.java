package com.example.leftlovers.database;


import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.leftlovers.database.DataSingleton;
import com.example.leftlovers.model.Ingredient;
import com.example.leftlovers.model.Recipe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ApiConnection {
    public static final String QUERY_SEARCH_BY_INGRIDIENTS = "https://api.edamam.com/search?q=";
    public static final String QUERY_SEARCH_BY_URL = "https://api.edamam.com/search?r=";
    public static final String QUERY_VERIFICATION = "&app_id=23b2fea2&app_key=c922aee8d3ac99a52aad47208d2b476e";

    Context context;
 //   String nameRecipe;
//    String urlImgRecipe;

    public ApiConnection(Context context) {
        this.context = context;
    }

    // Response Listeners for Callbacks
    public interface VolleyResponseListener {
        void onError(String message);

        void onResponse(Recipe recipeURL);
    }

    public interface ListVolleyResponseListener {
        void onError(String message);

        void onResponse(List<Recipe> recipeList);
    }


    public void getRecipe(String searchText, VolleyResponseListener volleyResponseListener) {
        String url = QUERY_SEARCH_BY_INGRIDIENTS + searchText + QUERY_VERIFICATION;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONObject wholeResponse = response;

                String nameRecipe;
                String urlImgRecipe;
                Recipe recipe = new Recipe(searchText);

                try {
                    JSONArray allRecipes = wholeResponse.getJSONArray("hits");
                    JSONObject firstRecipe = allRecipes.getJSONObject(0).getJSONObject("recipe");
                    nameRecipe = firstRecipe.getString("label");
                    urlImgRecipe = firstRecipe.getString("image");
                    recipe.setName(nameRecipe);
                    recipe.setImgUrl(urlImgRecipe);
                    List<Ingredient> ingredientList = getIngredients(firstRecipe);
                    recipe.setIngredients(ingredientList);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                volleyResponseListener.onResponse(recipe);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               volleyResponseListener.onError("sth went wrong");
            }
        });
        DataSingleton.getInstance(context).addToRequestQueue(request);
    }

    private List<Ingredient> getIngredients(JSONObject firstRecipe) {
        List<Ingredient> ingredientList = new ArrayList<>();
        try {
            JSONArray ingredientsJsonArray = firstRecipe.getJSONArray("ingredients");
            for (int i=0; i<ingredientsJsonArray.length(); i++){
                String name = ingredientsJsonArray.getJSONObject(i).getString("food");
                String url = ingredientsJsonArray.getJSONObject(i).getString("image");
                String measureText = ingredientsJsonArray.getJSONObject(i).getString("text");
                Ingredient ingredient = new Ingredient(name, url);
                ingredient.setMeasureText(measureText);
                ingredientList.add(ingredient);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return ingredientList;
    }


    public void getList(String searchText, ListVolleyResponseListener listVolleyResponseListener) {
        String url = QUERY_SEARCH_BY_INGRIDIENTS + searchText + QUERY_VERIFICATION;
        List<Recipe> recipeList = new ArrayList<>();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONObject wholeResponse = response;

                String nameRecipe;
                String urlImgRecipe;


                try {

                    JSONArray allRecipes = wholeResponse.getJSONArray("hits");
                    for (int i = 0; i<allRecipes.length(); i++) {
                        JSONObject firstRecipe = allRecipes.getJSONObject(i).getJSONObject("recipe");
                        nameRecipe = firstRecipe.getString("label");
                        urlImgRecipe = firstRecipe.getString("image");
                        Recipe recipe = new Recipe(nameRecipe);
                        recipe.setImgUrl(urlImgRecipe);
                        List<Ingredient> ingredientList = getIngredients(firstRecipe);
                        recipe.setIngredients(ingredientList);
                        recipeList.add(recipe);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                listVolleyResponseListener.onResponse(recipeList);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listVolleyResponseListener.onError("sth went wrong");
            }
        });
        DataSingleton.getInstance(context).addToRequestQueue(request);
    }


    public void getRecipeByIdentifier(String recipeUrl, VolleyResponseListener volleyResponseListener) {
        String url = QUERY_SEARCH_BY_URL + recipeUrl + QUERY_VERIFICATION;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONArray wholeResponse = response;

                String nameRecipe;
                String urlImgRecipe;
                Recipe recipe = new Recipe(recipeUrl);

                try {
                    JSONObject recipeJson = wholeResponse.getJSONObject(0);

                    nameRecipe = recipeJson.getString("label");
                    urlImgRecipe = recipeJson.getString("image");
                    List<Ingredient> ingredientList = getIngredients(recipeJson);
                    recipe.setIngredients(ingredientList);
                    recipe.setName(nameRecipe);
                    recipe.setImgUrl(urlImgRecipe);
                    Toast.makeText(context, nameRecipe, Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                volleyResponseListener.onResponse(recipe);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", "Error in Api Connection");  // Hier später was anderes hin
                volleyResponseListener.onError("sth went wrong");
            }
        });

        DataSingleton.getInstance(context).addToRequestQueue(request);
    }


}

