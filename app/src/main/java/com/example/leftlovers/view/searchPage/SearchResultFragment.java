package com.example.leftlovers.view.searchPage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.leftlovers.R;
import com.example.leftlovers.database.ApiConnection;
import com.example.leftlovers.model.Recipe;
import com.example.leftlovers.service.ReceipeDataService;
import com.example.leftlovers.util.FetchImg;


import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchResultFragment extends Fragment {

    private ReceipeDataService receipeDataService;
    private String searchText;
    private GridView recipeGrid;
    private final List<Recipe> recipes = new ArrayList<>();

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
        // get chosen recipe
        searchText = SearchResultFragmentArgs.fromBundle(getArguments()).getSearchText();

        receipeDataService.getRecipe(searchText, new ApiConnection.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Log.d("Api Connection Error", message);
            }

            @Override
            public void onResponse(Recipe recipe) {

                recipeGrid = view.findViewById(R.id.recipe_card_grid);

                for(int i=0; i<20; i++) { // TODO: change to for each
                    recipes.add(recipe);
                }

                RecipeGridAdapter rga = new RecipeGridAdapter(recipes, requireActivity().getLayoutInflater());
                recipeGrid.setAdapter(rga);
            }
        });

        return view;
    }

    public class RecipeGridAdapter extends BaseAdapter {
        private List<Recipe> recipes;
        private final LayoutInflater layoutInflater;

        public RecipeGridAdapter(List<Recipe> recipes, LayoutInflater layoutInflater) {
            this.recipes = recipes;
            this.layoutInflater = layoutInflater;
        }

        @Override
        public int getCount() {
            return recipes.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if(convertView == null) {
                convertView = layoutInflater.inflate(R.layout.fragment_recipe_card, parent, false);
            }

            // setup UI
            TextView name = convertView.findViewById(R.id.recipe_name);
            name.setText(recipes.get(position).getName());
            ImageView img = convertView.findViewById(R.id.recipe_image);
            new FetchImg(recipes.get(position).getImgUrl(), img).start();

            // setup navigation
            convertView.findViewById(R.id.recipe_card).setOnClickListener(view1 -> {
                NavDirections action = SearchResultFragmentDirections.actionSearchResultFragmentToRecipeDetailFragment(recipes.get(position));
                Navigation.findNavController(view1).navigate(action);
            });

            return convertView;
        }
    }
}