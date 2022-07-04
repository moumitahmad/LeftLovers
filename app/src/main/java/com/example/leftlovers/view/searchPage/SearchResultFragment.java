package com.example.leftlovers.view.searchPage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.leftlovers.R;
import com.example.leftlovers.database.ApiConnection;
import com.example.leftlovers.model.Recipe;
import com.example.leftlovers.service.ReceipeDataService;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchResultFragment extends Fragment {

    private ReceipeDataService receipeDataService;
    private String searchText;

    public SearchResultFragment() {
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
        View view = inflater.inflate(R.layout.fragment_search_result, container, false);

        // setup UI
        // get choosen recipe
        searchText = SearchResultFragmentArgs.fromBundle(getArguments()).getSearchText();

        receipeDataService.getRecipe(searchText, new ApiConnection.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Log.d("Api Connection Error", message);
            }

            @Override
            public void onResponse(Recipe recipe) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                if(recipe != null && fm.findFragmentById(R.id.recipe_card_grid) == null) { // TODO: change to for each
                    FragmentTransaction ft = fm.beginTransaction();
                    for(int i=0; i<2; i++) {
                        RecipeCardFragment card = RecipeCardFragment.newInstance(recipe);
                        ft.add(R.id.recipe_card_grid, card);
                    }
                    ft.commit();
                }
            }
        });

        return view;
    }
}