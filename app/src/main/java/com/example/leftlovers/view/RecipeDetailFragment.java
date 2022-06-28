package com.example.leftlovers.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.leftlovers.R;
import com.example.leftlovers.model.ImageLoadTask;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeDetailFragment extends Fragment {

    private String IMAGE_EXAMPLE = "https://www.vegini.at/wp-content/uploads/2017/10/Rezeptbild-Spaghetti.jpg";

    public RecipeDetailFragment() {
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

        ImageView image = view.findViewById(R.id.recipe_image);
        //new ImageLoadTask(IMAGE_EXAMPLE, image).execute();

        return view;
    }
}