package com.example.leftlovers.view.searchPage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.leftlovers.R;
import com.example.leftlovers.adapter.ExploreAdapter;
import com.example.leftlovers.database.api.ApiConnection;
import com.example.leftlovers.service.ApiDataService;
import com.example.leftlovers.model.Recipe;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SeachHomeFragment extends Fragment {

    LinearLayoutManager HorizontalLayout;

    public SeachHomeFragment() {
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
        View view = inflater.inflate(R.layout.fragment_seach_home, container, false);

        RecyclerView rvExplore = (RecyclerView) view.findViewById(R.id.rvExplore);
        ApiDataService apiDataService = new ApiDataService(getActivity());


        apiDataService.getRandomRecipes(new ApiConnection.ListVolleyResponseListener() {
            @Override
            public void onError(String message) {
                Log.d("Api Connection Error", message);
            }

            @Override
            public void onResponse(List<Recipe> recipeList) {
                ExploreAdapter adapter = new ExploreAdapter(recipeList);

                rvExplore.setAdapter(adapter);
                HorizontalLayout = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);
                rvExplore.setLayoutManager(HorizontalLayout);

                // hide progress bar
                view.findViewById(R.id.loading_animation).setVisibility(View.INVISIBLE);
            }
        });

        return view;
    }
}