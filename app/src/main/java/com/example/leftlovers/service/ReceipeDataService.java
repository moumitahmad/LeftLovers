package com.example.leftlovers.service;


import android.content.Context;

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

public class ReceipeDataService {
    public static final String QUERY_SEARCH_BY_INGRIDIENTS = "https://api.edamam.com/search?q=";
    public static final String QUERY_SEARCH_BY_URL = "https://api.edamam.com/search?r=";
    public static final String QUERY_VERIFICATION = "&app_id=23b2fea2&app_key=c922aee8d3ac99a52aad47208d2b476e";

    Context context;
    String nameRecipe;
    String urlImgRecipe;

    public ReceipeDataService(Context context) {
        this.context = context;
    }

    public interface VolleyResponseListener {
        void onError(String message);

        void onResponse(Recipe recipeURL);

     //   void onResponse2(String recipeURL);
    }


    public  void getURL(String searchText, VolleyResponseListener volleyResponseListener) {
        // Instantiate the RequestQueue.
        //   RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        String url = QUERY_SEARCH_BY_INGRIDIENTS + searchText + QUERY_VERIFICATION;

        // Request a string response from the provided URL.
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONObject obj = response;
                nameRecipe = "";
                String nameRecipe1 = "";
                String nameRecipe2 = "";
                try {
                    JSONArray arrRecipes = obj.getJSONArray("hits");
                    JSONObject obj1 = arrRecipes.getJSONObject(0);
                    JSONObject obj2 = obj1.getJSONObject("recipe");
                    nameRecipe = obj2.getString("label");
                    // nameRecipe1 = obj2.getString("image");
                    // nameRecipe2 = obj2.getString("url");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // Toast.makeText(context, nameRecipe, Toast.LENGTH_SHORT).show();
                // Toast.makeText(context, nameRecipe1, Toast.LENGTH_SHORT).show();
                // Toast.makeText(context, nameRecipe2, Toast.LENGTH_SHORT).show();

             //   volleyResponseListener.onResponse2(nameRecipe);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //   Toast.makeText(context, "EEEror", Toast.LENGTH_SHORT).show();
                volleyResponseListener.onError("sth went wrong");
            }
        });

        DataSingleton.getInstance(context).addToRequestQueue(request);

    }

    public void getList(String searchText, VolleyResponseListener volleyResponseListener) {
        List<String> ingridientsList = new ArrayList<>();
        String url = QUERY_SEARCH_BY_INGRIDIENTS + searchText + QUERY_VERIFICATION;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONObject obj = response;
/*
                for (:
                     ) {

                }*/
                nameRecipe = "";
                urlImgRecipe = "";
                Recipe test = new Recipe(searchText);
                try {
                    JSONArray arrRecipes = obj.getJSONArray("hits");
                    JSONObject obj1 = arrRecipes.getJSONObject(0);
                    JSONObject obj2 = obj1.getJSONObject("recipe");
                    nameRecipe = obj2.getString("label");
                    urlImgRecipe = obj2.getString("image");
                    test.setName(nameRecipe);
                    test.setImgUrl(urlImgRecipe);

                 //   ingridientsList

                  //  this.ingredients = ingredients;
                   // this.description = description;
                   // this.link = link;

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // Toast.makeText(context, nameRecipe, Toast.LENGTH_SHORT).show();
                // Toast.makeText(context, nameRecipe1, Toast.LENGTH_SHORT).show();
                // Toast.makeText(context, nameRecipe2, Toast.LENGTH_SHORT).show();

                volleyResponseListener.onResponse(test);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //   Toast.makeText(context, "EEEror", Toast.LENGTH_SHORT).show();
                volleyResponseListener.onError("sth went wrong");
            }
        });

        DataSingleton.getInstance(context).addToRequestQueue(request);

    }
}

