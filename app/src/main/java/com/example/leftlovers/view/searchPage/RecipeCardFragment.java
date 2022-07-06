package com.example.leftlovers.view.searchPage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.leftlovers.R;
import com.example.leftlovers.model.Recipe;
import com.example.leftlovers.util.FetchImg;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecipeCardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipeCardFragment extends Fragment {

    private static final String ARG_PARAM = "recipe";
    private Recipe shownRecipe;

    public RecipeCardFragment() {
        // Required empty public constructor
    }

    public static RecipeCardFragment newInstance(Recipe recipe) {
        RecipeCardFragment fragment = new RecipeCardFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM, recipe);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            shownRecipe = getArguments().getParcelable(ARG_PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe_card, container, false);

        // setup view
        new FetchImg(shownRecipe.getImgUrl(), view.findViewById(R.id.recipe_image)).start();

        TextView text = view.findViewById(R.id.recipe_name);
        text.setText(shownRecipe.getName());

        // setup navigation
        view.findViewById(R.id.recipe_card).setOnClickListener(view1 -> {
            NavDirections action = SearchResultFragmentDirections.actionSearchResultFragmentToRecipeDetailFragment(shownRecipe);
            Navigation.findNavController(view1).navigate(action);
        });

        return view;
    }
}