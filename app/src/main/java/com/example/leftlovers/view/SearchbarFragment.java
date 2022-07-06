package com.example.leftlovers.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.leftlovers.R;
import com.example.leftlovers.service.ReceipeDataService;


public class SearchbarFragment extends Fragment {

    private ReceipeDataService receipeDataService;
    private EditText editText;
    private Button searchButton;
    private String ingredient;

    public String getUserInput() {
        ingredient = editText.getText().toString();
        return ingredient;
    }
    

    public SearchbarFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        receipeDataService = new ReceipeDataService(getActivity());
        //s = searchView.findViewById(R.id.searchView);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_searchbar, container, false);

        editText = view.findViewById(R.id.searchbar_input);
        searchButton = view.findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("EditText", getUserInput());
            }
        });

        return view;
    }
}