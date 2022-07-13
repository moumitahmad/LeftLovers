package com.example.leftlovers.view.fridgePage;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.leftlovers.R;
import com.example.leftlovers.model.Ingredient;
import com.example.leftlovers.service.DatabaseService;
import com.example.leftlovers.util.ExpandableHeightGridView;
import com.example.leftlovers.util.FetchImg;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FridgeFragment extends Fragment {

    private final List<Ingredient> expiringIngredients = new ArrayList<Ingredient>();
    private final List<Ingredient> otherIngredients = new ArrayList<Ingredient>();
    private List<Ingredient> allIngredients = new ArrayList<Ingredient>();
    private DatabaseService databaseService;

    public FridgeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fridge, container, false);
        databaseService = new DatabaseService(getActivity());
       // databaseService.removeAllIngredients();

        // TODO: mit room Abfrage austauschen
        allIngredients = databaseService.loadIngredientList();

        for (int i=0; i<allIngredients.size(); i++) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(allIngredients.get(i).getExpirationDate(), formatter);
            long daysBetween = ChronoUnit.DAYS.between( LocalDate.now() , localDate );

            if (daysBetween>4) {
                otherIngredients.add(allIngredients.get(i));
            } else {
                expiringIngredients.add(allIngredients.get(i));
            }

        }

        // setup ui
        // expiring section
        ExpandableHeightGridView exIngredientsGrid = view.findViewById(R.id.expiring_ingredients_grid);
        IngredientGridAdapter iga1 = new IngredientGridAdapter(expiringIngredients, requireActivity().getLayoutInflater());
        exIngredientsGrid.setAdapter(iga1);
        exIngredientsGrid.setExpanded(true);

        // other section
        ExpandableHeightGridView otherIngredientsGrid = view.findViewById(R.id.other_ingredients_grid);
        IngredientGridAdapter iga2 = new IngredientGridAdapter(otherIngredients, requireActivity().getLayoutInflater());
        otherIngredientsGrid.setAdapter(iga2);
        otherIngredientsGrid.setExpanded(true);

        // hide Headings

        if (expiringIngredients.size()<1)
            view.findViewById(R.id.fridge_subtitle1).setVisibility(View.INVISIBLE);
        if (otherIngredients.size()<1)
            view.findViewById(R.id.fridge_subtitle2).setVisibility(View.INVISIBLE);

         if (otherIngredients.size()<1 && expiringIngredients.size()<1)
            view.findViewById(R.id.fridge_subtitle3).setVisibility(View.VISIBLE);


        // hide progress bar
        view.findViewById(R.id.loading_expiring).setVisibility(View.INVISIBLE);
        view.findViewById(R.id.loading_other).setVisibility(View.INVISIBLE);

        // action buttons
        setupAddButton(view);
        setupSearchButton(view);

        return view;
    }

    private void setupAddButton(View view) {
        FloatingActionButton addButton = view.findViewById(R.id.add_button);
        addButton.setOnClickListener(view1 -> {
            // navigate to add fragment
            NavDirections action = FridgeFragmentDirections.actionFridgeFragmentToEditIngredientFragment(null);
            Navigation.findNavController(view1).navigate(action);
        });
    }

    private void setupSearchButton(View view) {
        FloatingActionButton searchButton = view.findViewById(R.id.search_button);
        searchButton.setOnClickListener(view1 -> {
            // navigate to search fragment
            NavDirections action = FridgeFragmentDirections.actionFridgeFragmentToSearchFragment2();
            Navigation.findNavController(view1).navigate(action);
        });
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
                convertView = layoutInflater.inflate(R.layout.fragment_recipe_card, parent, false);
            }

            // setup UI
            TextView name = convertView.findViewById(R.id.recipe_name);
            name.setText(ingredients.get(position).getName());
            ImageView img = convertView.findViewById(R.id.recipe_image);
            if (ingredients.get(position).getImgUrl().contains("://www.")) {
                new FetchImg(ingredients.get(position).getImgUrl(), img).start();
            } else {
                Uri localImageUri = Uri.parse(ingredients.get(position).getImgUrl());
                img.setImageURI(localImageUri);
            }

            // setup navigation
            convertView.findViewById(R.id.recipe_card).setOnClickListener(view1 -> {
                NavDirections action = (NavDirections) FridgeFragmentDirections.actionFridgeFragmentToEditIngredientFragment(ingredients.get(position));
                Navigation.findNavController(view1).navigate(action);
            });

            return convertView;
        }
    }
}