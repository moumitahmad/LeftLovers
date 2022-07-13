package com.example.leftlovers.view.searchPage;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.leftlovers.R;
import com.example.leftlovers.database.api.ApiConnection;
import com.example.leftlovers.model.Recipe;
import com.example.leftlovers.service.ApiDataService;
import com.example.leftlovers.util.ExpandableHeightGridView;
import com.example.leftlovers.util.FetchImg;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchResultFragment extends Fragment {

    private ApiDataService apiDataService;
    private @Nullable String searchText;
    private @Nullable String[] filters;
    private @Nullable String[] chosenIngredients;
    private ExpandableHeightGridView recipeGrid;
    private static final int MAX_INGREDIENTS_LOAD = 15;
    private int startID = 0;
    private int endID = MAX_INGREDIENTS_LOAD;

    public SearchResultFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiDataService = new ApiDataService(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_result, container, false);

        // setup UI
        // get chosen recipe
        searchText = SearchResultFragmentArgs.fromBundle(getArguments()).getSearchText();
        filters = SearchResultFragmentArgs.fromBundle(getArguments()).getFilters();
        chosenIngredients = SearchResultFragmentArgs.fromBundle(getArguments()).getChosenIngredients();

        StringBuilder filterQuery = new StringBuilder();
        for(int i=0; i<filters.length; i++) {
            if(filters[i] != null) {
                filterQuery.append("&").append(getCategoryByID(i)).append("=").append(filters[i]);
            }
        }

        for(String ingredient: chosenIngredients) {
            searchText += ", " + ingredient;
        }

        TextView errorText = view.findViewById(R.id.result_error_text);

        apiDataService.getRecipesByCategory(searchText, filterQuery.toString(), startID, endID, new ApiConnection.ListVolleyResponseListener() {
            @Override
            public void onError(String message) {
                Log.d("Api Connection Error", message);
                errorText.setText(R.string.api_connection_error_text);
                errorText.setVisibility(View.VISIBLE);
                view.findViewById(R.id.loading_animation).setVisibility(View.INVISIBLE);
            }

            @Override
            public void onResponse(List<Recipe> recipeList) {
                errorText.setVisibility(View.INVISIBLE);

                if(recipeList.isEmpty()) {
                    errorText.setText(R.string.result_error_text);
                    errorText.setVisibility(View.VISIBLE);
                    return;
                }

                recipeGrid = view.findViewById(R.id.recipe_card_grid);

                RecipeGridAdapter rga = new RecipeGridAdapter(recipeList, requireActivity().getLayoutInflater());
                recipeGrid.setAdapter(rga);
                recipeGrid.setExpanded(true);

                // hide progress bar
                view.findViewById(R.id.loading_animation).setVisibility(View.INVISIBLE);
                if(recipeList.size() == MAX_INGREDIENTS_LOAD)
                    view.findViewById(R.id.load_more_button).setVisibility(View.VISIBLE);

                // update start/end-ID
                startID = endID;
                endID += MAX_INGREDIENTS_LOAD;
            }
        });

        Button moreButton = view.findViewById(R.id.load_more_button);
        moreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // show progress bar
                view.findViewById(R.id.loading_animation).setVisibility(View.VISIBLE);

                apiDataService.getRecipesByCategory(searchText, filterQuery.toString(), startID, endID, new ApiConnection.ListVolleyResponseListener() {
                    @Override
                    public void onError(String message) {
                        Log.d("Api Connection Error", message);
                        errorText.setText(R.string.api_connection_error_text);
                        errorText.setVisibility(View.VISIBLE);
                        view.findViewById(R.id.loading_animation).setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onResponse(List<Recipe> recipeList) {
                        errorText.setVisibility(View.INVISIBLE);

                        ExpandableHeightGridView newGrid = new ExpandableHeightGridView(getContext());
                        newGrid.setGravity(Gravity.CENTER);
                        newGrid.setNumColumns(3);
                        if(getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
                            newGrid.setNumColumns(5);

                        RecipeGridAdapter rga = new RecipeGridAdapter(recipeList, requireActivity().getLayoutInflater());
                        newGrid.setAdapter(rga);
                        newGrid.setExpanded(true);

                        LinearLayout gridLayout = view.findViewById(R.id.result_grid);
                        gridLayout.addView(newGrid);

                        // hide progress bar
                        view.findViewById(R.id.loading_animation).setVisibility(View.INVISIBLE);
                        if(recipeList.size() == MAX_INGREDIENTS_LOAD)
                            view.findViewById(R.id.load_more_button).setVisibility(View.VISIBLE);

                        // update start/end-ID
                        startID = endID;
                        endID += MAX_INGREDIENTS_LOAD;
                    }
                });
            }
        });

        return view;
    }

    public static class RecipeGridAdapter extends BaseAdapter {
        private final List<Recipe> recipes;
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
                NavDirections action = (NavDirections) SearchFragmentDirections.actionSearchFragment2ToRecipeDetailFragment(recipes.get(position));
                Navigation.findNavController(view1).navigate(action);
            });

            return convertView;
        }
    }

    private String getCategoryByID(int id) {
        switch(id) {
            case 0: return "dishType";
            case 1: return "mealType";
            case 2: return "cuisineType";
            case 3: return "health";
            case 4: return "diet";
        }
        return null;
    }
}