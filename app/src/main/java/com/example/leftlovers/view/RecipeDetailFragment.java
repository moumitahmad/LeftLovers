package com.example.leftlovers.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leftlovers.R;
import com.example.leftlovers.model.Recipe;
import com.example.leftlovers.database.ApiConnection;
import com.example.leftlovers.service.ReceipeDataService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeDetailFragment extends Fragment {

    private boolean isBookmarked = false;
    private Recipe choosenRecipe;
    private Handler imageHandler = new Handler();
    ReceipeDataService receipeDataService;

    public RecipeDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        receipeDataService = new ReceipeDataService(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe_detail, container, false);

        // get choosen recipe
        //choosenRecipe = getArguments().getParcelable("recipe");

        receipeDataService.getRecipe("Tomato", new ApiConnection.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Log.d("Error", "Sth went wrong in RecipeDetailFragment");
            }

            @Override
            public void onResponse(Recipe recipe) {
                choosenRecipe = recipe;
                TextView nameText = view.findViewById(R.id.recipe_name);
                nameText.setText(choosenRecipe.getName());

                // load image from url
                Log.i("fetching image from: ", choosenRecipe.getImgUrl());
                new FetchRecipeImg(choosenRecipe.getImgUrl()).start();
            }
        });


        /*****************************************/





        // setup bookmark function
        FloatingActionButton bookmarkButton = view.findViewById(R.id.bookmark_button);
        bookmarkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // toggle FloatingButton image
                int drawableResource = R.drawable.ic_baseline_bookmark_24;
                if(isBookmarked) {
                    drawableResource = R.drawable.ic_baseline_bookmark_border_24;
                    isBookmarked = false;
                } else {
                    isBookmarked = true;
                }
                bookmarkButton.setImageResource(drawableResource);
                // TODO: save/delete bookmark in Database
            }
        });

        return view;
    }

    class FetchRecipeImg extends Thread {
        String url;
        Bitmap bitmap;

        FetchRecipeImg(String url) {
            this.url = url;
        }

        @Override
        public void run() {
            InputStream inputStream = null;
            try {
                inputStream = new java.net.URL(url).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (IOException e) {
                Log.e("fetching image-url: ", e.getMessage());
            }

            imageHandler.post(new Runnable() {
                @Override
                public void run() {
                    Log.i("fetching image-url: ", "LOADED");
                    ImageView iv = getView().findViewById(R.id.recipe_image);
                    iv.setImageBitmap(bitmap);
                }
            });
        }
    }
}