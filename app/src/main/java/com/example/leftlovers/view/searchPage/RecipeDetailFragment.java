package com.example.leftlovers.view.searchPage;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.leftlovers.R;
import com.example.leftlovers.model.Ingredient;
import com.example.leftlovers.model.Recipe;
import com.example.leftlovers.util.FetchImg;
import com.google.android.material.floatingactionbutton.FloatingActionButton;



/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeDetailFragment extends Fragment {

    private boolean isBookmarked = false;
    private Recipe choosenRecipe;

    public RecipeDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe_detail, container, false);

        // get choosen recipe
        Recipe choosenRecipe = RecipeDetailFragmentArgs.fromBundle(getArguments()).getChoosenRecipe();
        TextView nameText = view.findViewById(R.id.recipe_name);
        nameText.setText(choosenRecipe.getName());

        // load image from url
        Log.i("fetching image from: ", choosenRecipe.getImgUrl());
        new FetchImg(choosenRecipe.getImgUrl(), view.findViewById(R.id.recipe_image)).start();

        // setup ingredients grid
        GridLayout ingredientGrid = view.findViewById(R.id.ingredients_grid);
        ingredientGrid.setColumnCount(choosenRecipe.getIngredients().size()); // TODO: responsive

        for (Ingredient ingredient : choosenRecipe.getIngredients()) {
            Log.i("ingredient:", ingredient.getName());
            // TODO: not working
            /* IngredientFragment ingFrag = IngredientFragment.newInstance(ingredient);
            View ingView = ingFrag.getView();
            ingredientGrid.addView(ingView); */
        }

        // setup bookmark function
        FloatingActionButton bookmarkButton = view.findViewById(R.id.bookmark_button);
        bookmarkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // toggle FloatingButton image
                int drawableResource = R.drawable.ic_baseline_bookmark_24;
                if(isBookmarked) {
                    drawableResource = R.drawable.ic_baseline_bookmark_border_24;
                    isBookmarked = false;
                } else {
                    isBookmarked = true;
                }
                bookmarkButton.setImageResource(drawableResource);
                // TODO: save/delete bookmark in Database
            }
        });

        return view;
    }
}