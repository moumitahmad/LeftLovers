package com.example.leftlovers.view.searchPage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.leftlovers.R;
import com.example.leftlovers.database.ApiConnection;
import com.example.leftlovers.model.Ingredient;
import com.example.leftlovers.model.Recipe;
import com.example.leftlovers.service.ReceipeDataService;
import com.example.leftlovers.view.SearchbarFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    private ReceipeDataService receipeDataService;
    private SearchbarFragment searchbarFragment;
    private String IMAGE_EXAMPLE = "https://www.vegini.at/wp-content/uploads/2017/10/Rezeptbild-Spaghetti.jpg";
    private String RECIPE_LINK = "https://www.gutekueche.at/spaggetti-bolognese-rezept-22388";
    List<Recipe> recipeListTest = new ArrayList<>();


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

        /*Ingredient i1 = new Ingredient("Tomatos", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS6s2oKgQVysceJyLpvwWRGBd2zj-b7c_LiTA&usqp=CAU");
        Ingredient i2 = new Ingredient("Beef", "https://www.gourmetfleisch.de/shop/images/products/main/thumb/14254.jpg");
        List myIngredients = new ArrayList();
        myIngredients.add(i1);
        myIngredients.add(i2);
        Recipe testRecipe = new Recipe("Spaghetti Bolonese", IMAGE_EXAMPLE, myIngredients, "test Description", RECIPE_LINK);*/

        receipeDataService.getRecipe("Tomato", new ApiConnection.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Log.d("Api Connection Error", message);
            }

            @Override
            public void onResponse(Recipe recipe) {
                Button recipeButton = view.findViewById(R.id.recipePlaceholder1);
                recipeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        NavDirections action = (NavDirections) SearchFragmentDirections.actionSearchFragmentToRecipeDetailFragment(recipe);
                        Navigation.findNavController(view).navigate(action);
                    }
                });
            }
        });

        receipeDataService.getList("egg", new ApiConnection.ListVolleyResponseListener() {
            @Override
            public void onError(String message) {
                Log.d("Api Connection Error", message);
            }

            @Override
            public void onResponse(List<Recipe> recipeList) {
                recipeListTest = recipeList;
                Log.v("List", recipeListTest.toString());
            }
        });

        return view;
    }
}