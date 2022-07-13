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
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.leftlovers.R;
import com.example.leftlovers.database.api.ApiConnection;
import com.example.leftlovers.model.Ingredient;
import com.example.leftlovers.service.DatabaseService;
import com.example.leftlovers.service.ApiDataService;
import com.example.leftlovers.util.FetchImg;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditIngredientFragment extends Fragment {

    private ActivityResultLauncher resultLauncher;
    private String uploadImagePath;
    private ApiDataService apiDataService;
    private DatabaseService databaseService;

    private static final int DEFAULT_AMOUNT = 1;
    private Ingredient chosenIngredient;
    private String name = "";
    private int amount = 1;
    private LocalDate expirationDate = LocalDate.now();
    private String notes = "";
    private ImageView ingredientImageView;
    private Uri imageURI;
    private boolean fromDb=false;

    private AutoCompleteTextView inputName;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> listOfSuggestions;
    private String selectedSuggestion;





    private enum InputError {
        NAME_ERROR,
        DATE_ERROR
    }


    public EditIngredientFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        apiDataService = new ApiDataService(getActivity());
        databaseService = new DatabaseService(getActivity());
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

        listOfSuggestions = new ArrayList<String>();
        inputName = view.findViewById(R.id.autoComplete);
        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, listOfSuggestions);
        inputName.setAdapter(adapter);


        TextInputLayout inputExpirationDate = view.findViewById(R.id.expiration_date_text_field);
        TextInputLayout inputNotes = view.findViewById(R.id.notes_text_field);
        Button deleteButton = view.findViewById(R.id.delete_button);
        ingredientImageView = view.findViewById(R.id.ingredient_image);

        // new or editing
        if(chosenIngredient == null) { // setup app page
            TextView title = view.findViewById(R.id.edit_title);
            title.setText(R.string.add_title);
            // setup default date
            inputExpirationDate.getEditText().setText(expirationDate.toString());
            inputAmount.setText(String.valueOf(DEFAULT_AMOUNT));
            deleteButton.setVisibility(View.INVISIBLE);
            chosenIngredient = new Ingredient();
        } else { // setup edit page
            // load exsisting data
            fromDb = true;
            selectedSuggestion = chosenIngredient.getName();
            inputName.setText(chosenIngredient.getName());
            inputAmount.setText(String.valueOf(chosenIngredient.getAmount()));
            // TODO: display image fehler beheben name wird nicht geladen

            new FetchImg(chosenIngredient.getImgUrl(), ingredientImageView).start();
            inputExpirationDate.getEditText().setText(chosenIngredient.getExpirationDate());
            inputNotes.getEditText().setText(chosenIngredient.getNotes());
        }

        // setup search
        inputName.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String search =  s.toString();
                if (!search.equals("")) {
                    getSuggestFromApi(search);

                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        inputName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                selectedSuggestion = adapter.getItem(position);

                if(selectedSuggestion.contains(" "))
                    selectedSuggestion.replace(" ", "%20");

                apiDataService.getIngredient(selectedSuggestion, new ApiConnection.IngredientResponseListener() {
                    @Override
                    public void onError(String message) {
                        Log.i("Error", message);
                    }

                    @Override
                    public void onResponse(Ingredient ingredient) {
                        chosenIngredient.setName(ingredient.getName());
                        chosenIngredient.setImgUrl(ingredient.getImgUrl());
                        new FetchImg(chosenIngredient.getImgUrl(), ingredientImageView).start();
                        //TODO Button enable setzen
                    }
                });


            }
        });

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

                // Ingredient in DB speichern
                if (selectedSuggestion!=null) {
                    String dateString = expirationDate.toString();
                    chosenIngredient.setExpirationDate(dateString);
                    chosenIngredient.setNotes(notes);
                    if (fromDb == false) {
                        // HIER update oder insert
                        int id = databaseService.saveNewIngredient(chosenIngredient);
                        chosenIngredient.setIngredientId(id);
                        databaseService.loadIngredientList();
                    } else {
                        databaseService.updateIngredient(chosenIngredient);
                    }
                    returnToFridge(v);
                }
            }
        });

        // delete button
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: delete ingredient from local database
                databaseService.deleteIngredient(chosenIngredient);
                returnToFridge(v);
            }
        });

        // cancel button
        Button cancelButton = view.findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // navigate back to fridge fragment
                returnToFridge(v);
            }
        });

        return view;
    }

    private void returnToFridge(View v) {
        NavDirections action = EditIngredientFragmentDirections.actionEditIngredientFragmentToFridgeFragment();
        Navigation.findNavController(v).navigate(action);
    }

    private void getSuggestFromApi(String search) {
        apiDataService.getSuggest(search, new ApiConnection.SuggestVolleyResponseListener() {
            @Override
            public void onError(String message) {
                Log.d("ERROR", message);
            }

            @Override
            public void onResponse(ArrayList<String> recipeList) {
                if (recipeList.size() > 0) {
                    listOfSuggestions = recipeList;
                    adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, listOfSuggestions);
                    inputName.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }
        });
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