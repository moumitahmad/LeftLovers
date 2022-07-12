package com.example.leftlovers.view.searchPage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RadioGroup;

import com.example.leftlovers.R;
import com.example.leftlovers.database.api.ApiConnection;
import com.example.leftlovers.service.ApiDataService;
import com.example.leftlovers.util.ExpandableHeightGridView;
import com.google.android.material.radiobutton.MaterialRadioButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FilterDialogFragment extends Fragment {

    private ApiDataService apiDataService;

    private List<String> dietFilters = new ArrayList<>();
    private List<String> healthFilters = new ArrayList<>();
    private List<String> cuisineTypeFilters = new ArrayList<>();
    private List<String> mealTypeFilters = new ArrayList<>();
    private List<String> dishTypeFilters = new ArrayList<>();

    private static String[] chosenFilters = new String[5];

    public static String[] getChosenFilters() {
        return chosenFilters;
    }


    public FilterDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        apiDataService = new ApiDataService(getActivity());
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_filter_dialog, container, false);

        apiDataService.getPossibleFiltersFromAPI(new ApiConnection.FilterVolleyResponseListener() {
            @Override
            public void onError(String message) {
                Log.e("get api filters", message);
            }

            @Override
            public void onResponse(List<JSONObject> filterList) {
                sortFilters(filterList);

                // setup filter grids
                setupRadioGroup(view.findViewById(R.id.dishType_grid), 0, dishTypeFilters);
                setupRadioGroup(view.findViewById(R.id.mealType_grid), 1, mealTypeFilters);
                setupRadioGroup(view.findViewById(R.id.cuisineType_grid), 2, cuisineTypeFilters);
                setupRadioGroup(view.findViewById(R.id.health_grid), 3, healthFilters);
                setupRadioGroup(view.findViewById(R.id.diet_grid), 4, dietFilters);
            }
        });

        return view;
    }

    private void setupRadioGroup(RadioGroup group, int categoryID, List<String> filters) {
        for(String filter: filters) {
            MaterialRadioButton filterButton = new MaterialRadioButton(getContext());
            filterButton.setText(filter);
            filterButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    chosenFilters[categoryID] = filter;
                }
            });
            group.addView(filterButton);
        }
    }

    private void sortFilters(List<JSONObject> filterList) {
        try {
            for(int i=0; i<filterList.size(); i++) {
                JSONArray items = filterList.get(i).getJSONObject("items").getJSONArray("enum");
                for(int j=0; j<items.length(); j++) {
                    switch(i) {
                        case 0:
                            dietFilters.add(items.getString(j));
                            break;
                        case 1:
                            if(j == 5)
                                break;
                            healthFilters.add(items.getString(j));
                            break;
                        case 2:
                            cuisineTypeFilters.add(items.getString(j));
                            break;
                        case 3:
                            mealTypeFilters.add(items.getString(j));
                            break;
                        case 4:
                            dishTypeFilters.add(items.getString(j));
                            break;
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }
    }

}