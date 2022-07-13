package com.example.leftlovers.view.searchPage;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.leftlovers.R;
import com.example.leftlovers.model.Ingredient;
import com.example.leftlovers.model.Recipe;
import com.example.leftlovers.service.DatabaseService;
import com.example.leftlovers.util.ExpandableHeightGridView;
import com.example.leftlovers.util.FetchImg;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeDetailFragment extends Fragment {

    private boolean isBookmarked = false;
    private Recipe chosenRecipe;
    private DatabaseService databaseService;


    public RecipeDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseService = new DatabaseService(getActivity());
     //   databaseService.removeAllRecipes();
        // get chosen recipe
        chosenRecipe = RecipeDetailFragmentArgs.fromBundle(getArguments()).getChoosenRecipe();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe_detail, container, false);

        // setup UI
        TextView nameText = view.findViewById(R.id.recipe_name);
        nameText.setText(chosenRecipe.getName());

        // load image from url
        new FetchImg(chosenRecipe.getImgUrl(), view.findViewById(R.id.recipe_image)).start();

        // setup ingredients grid
        ExpandableHeightGridView ingredientsGrid = view.findViewById(R.id.ingredients_grid);
        IngredientGridAdapter iga = new IngredientGridAdapter(chosenRecipe.getIngredients(), requireActivity().getLayoutInflater());
        ingredientsGrid.setAdapter(iga);
        ingredientsGrid.setExpanded(true);


        // setup interactions
        setupBookmarkButton(view);

        Button linkButton = view.findViewById(R.id.link_button);
        linkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(chosenRecipe.getInstructionsUrl())));
            }
        });

        return view;
    }

    private void setupBookmarkButton(View view) {
        FloatingActionButton bookmarkButton = view.findViewById(R.id.bookmark_button);
        isBookmarked(bookmarkButton);

        bookmarkButton.setOnClickListener(view1 -> {
            // toggle FloatingButton image
            int drawableResource = R.drawable.ic_baseline_bookmark_24;
            if(isBookmarked) {
                drawableResource = R.drawable.ic_baseline_bookmark_border_24;
                isBookmarked = false;
                databaseService.deleteRecipe(chosenRecipe);
            } else {
                isBookmarked = true;
                int recipeID = databaseService.saveNewRecipe(chosenRecipe);
                chosenRecipe.setRecipeId(recipeID);

            }
            bookmarkButton.setImageResource(drawableResource);
            // TODO: save/delete bookmark in local Database

        });
    }

    private void isBookmarked(FloatingActionButton bookmarkButton) {
        List<Recipe> recipeFound = databaseService.searchRecipeByURL(chosenRecipe.getIdentifingUri());
        if(recipeFound.size()>0) {
            isBookmarked = true;
            int idOfFound = recipeFound.get(0).getRecipeId();
            chosenRecipe.setRecipeId(idOfFound);
            bookmarkButton.setImageResource(R.drawable.ic_baseline_bookmark_24);
        }
    }

    public static class IngredientGridAdapter extends BaseAdapter {
        private final List<Ingredient> ingredients;
        private final LayoutInflater layoutInflater;

        public IngredientGridAdapter(List<Ingredient> ingredients, LayoutInflater layoutInflater) {
            this.ingredients = ingredients;
            this.layoutInflater = layoutInflater;
        }

        @Override
        public int getCount() {
            return ingredients.size();
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
                convertView = layoutInflater.inflate(R.layout.fragment_ingredient_card, parent, false);
            }

            // setup UI
            TextView name = convertView.findViewById(R.id.ingredient_name);
            name.setText(ingredients.get(position).getMeasureText());
            String ingredientImgUrl = ingredients.get(position).getImgUrl();
            ImageView img = convertView.findViewById(R.id.ingredient_image);
            if(ingredientImgUrl != null) { // TODO: still not placeholder
                new FetchImg(ingredients.get(position).getImgUrl(), img).start();
            }

            return convertView;
        }
    }
}