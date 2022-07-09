package com.example.leftlovers.view.fridgePage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.leftlovers.R;
import com.example.leftlovers.model.Ingredient;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditIngredientFragment extends Fragment {

    private Ingredient chosenIngredient;
    private String EDIT_TITLE = "<b>Edit</b> \nyour food";

    public EditIngredientFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chosenIngredient = EditIngredientFragmentArgs.fromBundle(getArguments()).getChosenIngredient();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_ingredient, container, false);

        // new or editing
        if(chosenIngredient == null) { // setup app page
            TextView title = view.findViewById(R.id.edit_title);
            title.setText(EDIT_TITLE);
        } else { // setup edit page

        }

        return view;
    }
}