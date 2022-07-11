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

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.leftlovers.R;
import com.example.leftlovers.model.Ingredient;
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
                            Uri uri = data.getData();
                            Log.d("IMG RESULT", uri.toString());
                            ImageView ingredientImage = getView().findViewById(R.id.ingredient_image);
                            ingredientImage.setImageURI(uri);
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
        TextInputLayout inputName = view.findViewById(R.id.name_text_field);
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

        if(name == null || name.equals("")) {
            inputErrors.add(InputError.NAME_ERROR);
        }

        return inputErrors;
    }
}