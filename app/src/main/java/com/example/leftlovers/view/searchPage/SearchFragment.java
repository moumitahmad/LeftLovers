package com.example.leftlovers.view.searchPage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.leftlovers.R;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    // private String IMAGE_EXAMPLE = "https://www.vegini.at/wp-content/uploads/2017/10/Rezeptbild-Spaghetti.jpg";
    // private String RECIPE_LINK = "https://www.gutekueche.at/spaggetti-bolognese-rezept-22388";

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

        /*Ingredient i1 = new Ingredient("Tomatoes", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS6s2oKgQVysceJyLpvwWRGBd2zj-b7c_LiTA&usqp=CAU");
        Ingredient i2 = new Ingredient("Beef", "https://www.gourmetfleisch.de/shop/images/products/main/thumb/14254.jpg");
        List myIngredients = new ArrayList();
        myIngredients.add(i1);
        myIngredients.add(i2);
        Recipe testRecipe = new Recipe("Spaghetti Bolognese", IMAGE_EXAMPLE, myIngredients, "test Description", RECIPE_LINK);*/


        Button recipeButton = view.findViewById(R.id.recipePlaceholder1);
        recipeButton.setOnClickListener(view1 -> {
            NavDirections action = SearchFragmentDirections.actionSearchFragmentToSearchResultFragment("tomato");
            Navigation.findNavController(view1).navigate(action);
        });

        return view;
    }
}