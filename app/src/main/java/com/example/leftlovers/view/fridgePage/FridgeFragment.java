package com.example.leftlovers.view.fridgePage;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.leftlovers.R;
import com.example.leftlovers.model.Ingredient;
import com.example.leftlovers.util.ExpandableHeightGridView;
import com.example.leftlovers.util.FetchImg;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FridgeFragment extends Fragment {

    private final List<Ingredient> expiringIngredients = new ArrayList<Ingredient>();
    private final List<Ingredient> otherIngredients = new ArrayList<Ingredient>();

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


        // TODO: mit room Abfrage austauschen
        String url = "https://upload.wikimedia.org/wikipedia/commons/thumb/1/10/Tomates_cerises_Luc_Viatour.jpg/220px-Tomates_cerises_Luc_Viatour.jpg";
        Ingredient i1 = new Ingredient("Tomato", url, 2, LocalDate.now(), "this are some notes");
        Ingredient i2 = new Ingredient("Cherry", url, 5, LocalDate.parse("2022-08-12"), "this are some notes");
        Ingredient i4 = new Ingredient("Eggs", url, 10, LocalDate.parse("2030-12-12"), "this are some notes");
        Ingredient i5 = new Ingredient("Milk", url, 12, LocalDate.parse("2023-10-12"), "this are some notes");

        expiringIngredients.add(i1);
        expiringIngredients.add(i2);
        expiringIngredients.add(i2);
        expiringIngredients.add(i2);
        expiringIngredients.add(i2);

        otherIngredients.add(i4);
        otherIngredients.add(i4);
        otherIngredients.add(i4);
        otherIngredients.add(i5);
        otherIngredients.add(i5);

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
            //NavDirections action = FridgeFragmentDirections.actionFridgeFragmentToSearchFragment();
            //Navigation.findNavController(view1).navigate(action);
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
            new FetchImg(ingredients.get(position).getImgUrl(), img).start();

            // setup navigation
            convertView.findViewById(R.id.recipe_card).setOnClickListener(view1 -> {
                NavDirections action = (NavDirections) FridgeFragmentDirections.actionFridgeFragmentToEditIngredientFragment(ingredients.get(position));
                Navigation.findNavController(view1).navigate(action);
            });

            return convertView;
        }
    }
}