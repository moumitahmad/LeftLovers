package com.example.leftlovers.view.searchPage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.leftlovers.R;
import com.example.leftlovers.view.searchPage.SeachHomeFragment;
import com.example.leftlovers.adapter.ExploreAdapter;
import com.example.leftlovers.database.api.ApiConnection;
import com.example.leftlovers.model.Recipe;
import com.example.leftlovers.service.ApiDataService;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

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
        if (savedInstanceState == null) {
            getActivity().getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.search_page_content, SeachHomeFragment.class, null)
                .commit();
        }
        return view;
    }
}