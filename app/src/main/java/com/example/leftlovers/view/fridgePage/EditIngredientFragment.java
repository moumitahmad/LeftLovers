package com.example.leftlovers.view.fridgePage;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.leftlovers.R;
import com.example.leftlovers.model.Ingredient;
import com.google.android.material.textfield.TextInputLayout;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditIngredientFragment extends Fragment {

    private static final int DEFAULT_AMOUNT = 1;
    private Ingredient chosenIngredient;
    private String name = "";
    private int amount = 1;
    private LocalDate expirationDate = LocalDate.now();
    private String notes = "";

    private enum InputError {
        NAME_ERROR,
        DATE_ERROR
    }


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

        TextView inputAmount = view.findViewById(R.id.amount_input);
        TextInputLayout inputName = view.findViewById(R.id.name_text_field);
        TextInputLayout inputExpirationDate = view.findViewById(R.id.expiration_date_text_field);
        TextInputLayout inputNotes = view.findViewById(R.id.notes_text_field);

        // new or editing
        if(chosenIngredient == null) { // setup app page
            TextView title = view.findViewById(R.id.edit_title);
            title.setText(R.string.add_title);
            // setup default date
            inputExpirationDate.getEditText().setText(expirationDate.toString());
            inputAmount.setText(DEFAULT_AMOUNT);
        } else { // setup edit page
            // load exsisting data
            inputName.getEditText().setText(chosenIngredient.getName());
            inputAmount.setText(String.valueOf(chosenIngredient.getAmount()));
            inputExpirationDate.getEditText().setText(chosenIngredient.getExpirationDate().toString());
            inputNotes.getEditText().setText(chosenIngredient.getNotes());
        }

        // setup amount buttons
        Button minusButton = view.findViewById(R.id.minus_button);
        minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputAmount.setText(String.valueOf(--amount));
                if(amount == 0) {
                    minusButton.setEnabled(false);
                }
            }
        });

        Button plusButton = view.findViewById(R.id.plus_button);
        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputAmount.setText(String.valueOf(++amount));
                if(amount == 1) {
                    minusButton.setEnabled(true);
                }
            }
        });

        Button saveButton = view.findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get inputs
                name = inputName.getEditText().getText().toString();
                amount = Integer.parseInt(inputAmount.getText().toString());
                expirationDate = LocalDate.parse(inputName.getEditText().getText().toString());
                notes = inputNotes.getEditText().getText().toString();

                // validate input
                for(InputError inputError: validateInputs()) {
                    // display errors
                    switch(inputError) {
                        case NAME_ERROR:
                            inputName.setError("Every ingredient requires a name");
                            inputName.setErrorEnabled(true);
                            break;
                        case DATE_ERROR:
                            inputExpirationDate.setError("This is not a valid Date. Required format: yyyy-mm-dd");
                            inputExpirationDate.setErrorEnabled(true);
                            break;
                    }
                    return;
                }

                // TODO: react to validation
                // if amount == 0 delete ingredient

            }
        });

        return view;
    }

    private List<InputError> validateInputs() {
        List<InputError> inputErrors = new ArrayList<>();

        if(name == null || name.equals("")) {
            inputErrors.add(InputError.NAME_ERROR);
        }

        return inputErrors;
    }
}