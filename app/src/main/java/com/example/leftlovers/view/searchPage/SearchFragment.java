package com.example.leftlovers.view.searchPage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.leftlovers.R;
import com.example.leftlovers.database.ApiConnection;
import com.example.leftlovers.model.Recipe;
import com.example.leftlovers.service.ReceipeDataService;

import java.lang.reflect.Array;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    private ReceipeDataService receipeDataService;
    private List recipeListTest;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        receipeDataService = new ReceipeDataService(getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        receipeDataService.getRecipeList("egg", new ApiConnection.ListVolleyResponseListener() {
                    @Override
                    public void onError(String message) {
                        Log.d("Api Connection Error", message);
                    }

                    @Override
                    public void onResponse(List<Recipe> recipeList) {
                        recipeListTest = recipeList;
                        Log.d("List", recipeListTest.toString());
                    }
                }
        );

        return view;
    }
}