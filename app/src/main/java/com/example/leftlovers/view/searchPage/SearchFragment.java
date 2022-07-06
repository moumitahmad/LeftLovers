package com.example.leftlovers.view.searchPage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;

import com.example.leftlovers.R;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    // private String IMAGE_EXAMPLE = "https://www.vegini.at/wp-content/uploads/2017/10/Rezeptbild-Spaghetti.jpg";
    // private String RECIPE_LINK = "https://www.gutekueche.at/spaggetti-bolognese-rezept-22388";

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        // setup buttons
        SearchView search = view.findViewById(R.id.searchView);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                NavDirections action = (NavDirections) SearchFragmentDirections.actionSearchFragmentToSearchResultFragment(query);
                Navigation.findNavController(view).navigate(action);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return view;
    }
}