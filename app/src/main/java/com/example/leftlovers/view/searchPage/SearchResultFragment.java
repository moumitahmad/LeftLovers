package com.example.leftlovers.view.searchPage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

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
    private final List<String> recipeNames = new ArrayList<>();
    private final List<String> recipeUrls = new ArrayList<>();

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

                for(int i=0; i<20; i++) {
                    recipeNames.add(recipe.getName());
                    recipeUrls.add(recipe.getImgUrl());
                }

                RecipeGridAdapter rga = new RecipeGridAdapter(recipeNames, recipeUrls, requireActivity().getLayoutInflater());
                recipeGrid.setAdapter(rga);

                /*FragmentManager fm = getActivity().getSupportFragmentManager();
                if(recipe != null && fm.findFragmentById(R.id.recipe_card_grid) == null) { // TODO: change to for each
                    FragmentTransaction ft = fm.beginTransaction();
                    for(int i=0; i<2; i++) {
                        RecipeCardFragment card = RecipeCardFragment.newInstance(recipe);
                        ft.add(R.id.recipe_card_grid, card);
                    }
                    ft.commit();
                }*/
            }
        });

        return view;
    }

    public class RecipeGridAdapter extends BaseAdapter {
        private final List<String> imgNames;
        private final List<String> imgUrl;
        private final LayoutInflater layoutInflater;

        public RecipeGridAdapter(List<String> imgNames, List<String> imgUrl, LayoutInflater layoutInflater) {
            this.imgNames = imgNames;
            this.imgUrl = imgUrl;
            this.layoutInflater = layoutInflater;
        }

        @Override
        public int getCount() {
            return imgUrl.size();
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

            TextView name = convertView.findViewById(R.id.recipe_name);
            name.setText(imgNames.get(position));
            ImageView img = convertView.findViewById(R.id.recipe_image);
            new FetchImg(imgUrl.get(position), img).start();

            return convertView;
        }
    }
}