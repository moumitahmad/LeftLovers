package com.example.leftlovers.view.searchPage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;

import com.example.leftlovers.R;


public class SearchbarFragment extends Fragment {

    private FragmentManager fragmentManager;
    private boolean filterIsOpen = false;
    private boolean openResults = false;
    private String searchText;

    public SearchbarFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        fragmentManager = getActivity().getSupportFragmentManager();
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_searchbar, container, false);

        // setup search view
        SearchView search = view.findViewById(R.id.searchView);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchText = query;
                search.setQuery(searchText, false);
                displaySearchResults(search);
                openResults = true;
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        // setup buttons
        Button filterButton = view.findViewById(R.id.filter_button);
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // toggle filter dialog
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                if(filterIsOpen) {
                    if(openResults) {
                        displaySearchResults(search);
                    } else {
                        transaction.replace(R.id.search_page_content, SearchHomeFragment.class, null);
                    }
                    filterIsOpen = false;
                } else {
                    transaction.replace(R.id.search_page_content, FilterDialogFragment.class, null);
                    filterIsOpen = true;
                }
                transaction.commit();
            }
        });

        Button searchButton = view.findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchText = search.getQuery().toString();
                displaySearchResults(search);
                openResults = true;
            }
        });

        return view;
    }

    private void displaySearchResults(SearchView search) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        String[] filters = FilterDialogFragment.getChosenFilters();
        String[] chosenIngredients = FilterDialogFragment.getChosenIngredientFilter().toArray(new String[0]);
        if(chosenIngredients.length != 0) {
            StringBuilder query = new StringBuilder();
            for (String ingredient : chosenIngredients) {
                query.append(" ").append(ingredient);
            }
            search.setQuery(query.toString(), false);
        }

        Bundle args = new Bundle();
        args.putString("searchText", searchText);
        args.putStringArray("filters", filters);
        args.putStringArray("chosenIngredients", chosenIngredients);
        transaction.setReorderingAllowed(true);
        transaction.replace(R.id.search_page_content, SearchResultFragment.class, args);
        transaction.commit();
    }

}