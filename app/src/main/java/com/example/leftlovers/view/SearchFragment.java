package com.example.leftlovers.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.leftlovers.R;
import com.example.leftlovers.model.Ingredient;
import com.example.leftlovers.model.Recipe;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    private String IMAGE_EXAMPLE = "https://www.vegini.at/wp-content/uploads/2017/10/Rezeptbild-Spaghetti.jpg";
    private String RECIPE_LINK = "https://www.gutekueche.at/spaggetti-bolognese-rezept-22388";

    public SearchFragment() {
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
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        // TODO: change to infos from api
        Ingredient i1 = new Ingredient("Tomatos", "");
        Ingredient i2 = new Ingredient("Beef", "");
        List myIngredients = new ArrayList();
        myIngredients.add(i1);
        myIngredients.add(i2);
        Recipe testRecipe = new Recipe("Spaghetti Bolonese", IMAGE_EXAMPLE, myIngredients, "test Description", RECIPE_LINK);


        Button recipeButton = view.findViewById(R.id.recipePlaceholder1);
        recipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: save args
                // NavDirections action = RecipeDetailFragmentDirection
                // NavDirections action = RecipeDetailFragment.actionSearchFragmentToRecipeDetailFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable("recipe", testRecipe);
                Navigation.findNavController(view).navigate(R.id.action_searchFragment_to_recipeDetailFragment, bundle);
            }
        });

        return view;
    }
}