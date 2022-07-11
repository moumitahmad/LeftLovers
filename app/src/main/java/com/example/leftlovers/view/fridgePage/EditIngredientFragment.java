package com.example.leftlovers.view.fridgePage;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.leftlovers.R;
import com.example.leftlovers.model.Ingredient;
import com.example.leftlovers.util.FetchImg;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditIngredientFragment extends Fragment {

    private ActivityResultLauncher resultLauncher;
    private String uploadImagePath;

    private static final int DEFAULT_AMOUNT = 1;
    private Ingredient chosenIngredient;
    private String name = "";
    private int amount = 1;
    private LocalDate expirationDate = LocalDate.now();
    private String notes = "";
    private ImageView ingredientImageView;
    private Uri imageURI;

    private static final String[] INGREDIENTS = new String[] {
            "Eggs", "Tomato", "Edam", "Eclair", "Espresso", "Tabasco", "Turkey", "Tom Yum"
    };

    private enum InputError {
        NAME_ERROR,
        DATE_ERROR
    }


    public EditIngredientFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            imageURI = data.getData();
                            Log.d("IMG RESULT", imageURI.toString());
                            ingredientImageView = getView().findViewById(R.id.ingredient_image);
                            ingredientImageView.setImageURI(imageURI);
                            // TODO: save image in local database
                        }
                    }
                }
        );
        chosenIngredient = EditIngredientFragmentArgs.fromBundle(getArguments()).getChosenIngredient();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_ingredient, container, false);

        TextView inputAmount = view.findViewById(R.id.amount_input);
        AutoCompleteTextView inputName = view.findViewById(R.id.autoComplete);


        TextInputLayout inputExpirationDate = view.findViewById(R.id.expiration_date_text_field);
        TextInputLayout inputNotes = view.findViewById(R.id.notes_text_field);

        // new or editing
        if(chosenIngredient == null) { // setup app page
            TextView title = view.findViewById(R.id.edit_title);
            title.setText(R.string.add_title);
            // setup default date
            inputExpirationDate.getEditText().setText(expirationDate.toString());
            inputAmount.setText(String.valueOf(DEFAULT_AMOUNT));
        } else { // setup edit page
            // load exsisting data

          //TODO !!!!  inputName.getEditText().setText(chosenIngredient.getName());
            inputAmount.setText(String.valueOf(chosenIngredient.getAmount()));
            // TODO: display image
            inputExpirationDate.getEditText().setText(chosenIngredient.getExpirationDate().toString());
            inputNotes.getEditText().setText(chosenIngredient.getNotes());
        }

        // setup search
        inputName.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, INGREDIENTS);
                inputName.setAdapter(adapter);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        /*
        inputName.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        }); */
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

        // upload button
        FloatingActionButton uploadButton = view.findViewById(R.id.upload_button);
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImg(getContext());
            }
        });

        // save button
        Button saveButton = view.findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get inputs
                amount = Integer.parseInt(inputAmount.getText().toString());
                notes = inputNotes.getEditText().getText().toString();

                // validate input
                List<InputError> inputErrors = validateInputs();
                for(InputError inputError: inputErrors) {
                    // display errors
                    switch(inputError) {
                        case NAME_ERROR:
                            //TODO ERROR BEHANDLUNG
                            // inputName.setError("Every ingredient requires a name");
                            // inputName.setErrorEnabled(true);
                            break;
                        case DATE_ERROR:
                            inputExpirationDate.setError("This is not a valid Date. Required format: yyyy-mm-dd");
                            inputExpirationDate.setErrorEnabled(true);
                            break;
                    }
                }

                if(!inputErrors.isEmpty())
                    return;

                // valid inputs
                expirationDate = LocalDate.parse(inputExpirationDate.getEditText().getText().toString());
                // if amount == 0 delete ingredient
                String msg = "The Fridge has " + amount + " " + name + "s." +
                        "\n image :" + imageURI +
                        "\n expiration date :" + expirationDate.toString() +
                        "\n notes: " + notes;
                Log.i("INGREDIENT", msg);

                // TODO: save in local db

            }
        });

        // delete button
        Button deleteButton = view.findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: delete ingredient from local database
            }
        });

        // cancel button
        Button cancelButton = view.findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // navigate back to fridge fragment
                NavDirections action = EditIngredientFragmentDirections.actionEditIngredientFragmentToFridgeFragment();
                Navigation.findNavController(v).navigate(action);
            }
        });

        return view;
    }

    private void selectImg(Context context) {
        // create dialog
        final CharSequence[] options = {"Choose from your gallery", "Cancel"};
        MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(context);
        dialogBuilder.setTitle(R.string.upload_title);
        dialogBuilder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                switch(item) {
                    case 0:  // "Choose from your gallery"
                        Log.d("UPLOAD DIALOG", "gallery");
                        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                        galleryIntent.setType("image/jpg");
                        galleryIntent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                        resultLauncher.launch(galleryIntent);
                        break;
                    case 1: // Cancel
                        Log.d("UPLOAD DIALOG", "gallery");
                        dialog.dismiss();
                }
            }
        });
        dialogBuilder.show();
    }

    private List<InputError> validateInputs() {
        List<InputError> inputErrors = new ArrayList<>();
        //TODO Ablaufdatum Validate
     //   if(name == null || name.equals("")) {
     //       inputErrors.add(InputError.NAME_ERROR);
     //   }

        return inputErrors;
    }
}