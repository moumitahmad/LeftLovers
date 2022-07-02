package com.example.leftlovers.database;


import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.leftlovers.database.DataSingleton;
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

    public interface VolleyResponseListener {
        void onError(String message);

        void onResponse(Recipe recipeURL);
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
                    JSONObject firstCell = allRecipes.getJSONObject(0);   // später probieren .getJSONObject("recipe");
                    JSONObject firstRecipe = firstCell.getJSONObject("recipe");
                    nameRecipe = firstRecipe.getString("label");
                    urlImgRecipe = firstRecipe.getString("image");
                    recipe.setName(nameRecipe);
                    recipe.setImgUrl(urlImgRecipe);

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

