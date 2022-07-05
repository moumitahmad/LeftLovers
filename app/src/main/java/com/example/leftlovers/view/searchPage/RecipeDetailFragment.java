package com.example.leftlovers.view.searchPage;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.leftlovers.R;
import com.example.leftlovers.database.ApiConnection;
import com.example.leftlovers.model.Ingredient;
import com.example.leftlovers.model.Recipe;
import com.example.leftlovers.service.ReceipeDataService;
import com.example.leftlovers.util.FetchImg;
import com.google.android.material.floatingactionbutton.FloatingActionButton;



/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeDetailFragment extends Fragment {

    private boolean isBookmarked = false;
    private Recipe choosenRecipe;
    ReceipeDataService receipeDataService;

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

        // setup UI
        // get choosen recipe
        choosenRecipe = RecipeDetailFragmentArgs.fromBundle(getArguments()).getChoosenRecipe();
        TextView nameText = view.findViewById(R.id.recipe_name);
        nameText.setText(choosenRecipe.getName());

        // load image from url
        new FetchImg(choosenRecipe.getImgUrl(), view.findViewById(R.id.recipe_image)).start();

        // setup ingredients grid
        //TODO: make grid responsive

        FragmentManager fm = getActivity().getSupportFragmentManager();
        if(choosenRecipe.getIngredients() != null && fm.findFragmentById(R.id.ingredients_grid) == null) {
            FragmentTransaction ft = fm.beginTransaction();
            for (Ingredient ingredient : choosenRecipe.getIngredients()) {
                IngredientFragment ingFrag = IngredientFragment.newInstance(ingredient);
                ft.add(R.id.ingredients_grid, ingFrag);
            }
            ft.commit();
        }


        // setup interactions
        setupBookmarkButton(view);

        return view;
    }

    private void setupBookmarkButton(View view) {
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
                // TODO: save/delete bookmark in local Database
            }
        });
    }
}