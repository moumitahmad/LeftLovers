package com.example.leftlovers.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.leftlovers.R;
import com.example.leftlovers.database.api.ApiConnection;
import com.example.leftlovers.service.ApiDataService;
import com.example.leftlovers.service.DatabaseService;
import com.example.leftlovers.util.ExpandableHeightGridView;
import com.example.leftlovers.util.FetchImg;
import com.example.leftlovers.model.Recipe;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookmarkFragment extends Fragment {

    private DatabaseService databaseService;
    private static ApiDataService apiDataService;

    private List<Recipe> bookmarks;

    public BookmarkFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseService = new DatabaseService(getActivity());
        apiDataService = new ApiDataService(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bookmark, container, false);

        // setup bookmark grid
        bookmarks = databaseService.loadRecipeList();
        view.findViewById(R.id.loading_animation).setVisibility(View.INVISIBLE);
        if(bookmarks.isEmpty()) {
            view.findViewById(R.id.bookmark_error_text).setVisibility(View.VISIBLE);
        }

        ExpandableHeightGridView bookmarkGrid = view.findViewById(R.id.bookmark_grid);
        RecipeGridAdapter rga = new RecipeGridAdapter(bookmarks, requireActivity().getLayoutInflater());
        bookmarkGrid.setAdapter(rga);
        bookmarkGrid.setExpanded(true);

        return view;
    }

    public static class RecipeGridAdapter extends BaseAdapter {
        private final List<Recipe> recipes;
        private final LayoutInflater layoutInflater;

        public RecipeGridAdapter(List<Recipe> recipes, LayoutInflater layoutInflater) {
            this.recipes = recipes;
            this.layoutInflater = layoutInflater;
        }

        @Override
        public int getCount() {
            return recipes.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if(convertView == null) {
                convertView = layoutInflater.inflate(R.layout.fragment_recipe_card, parent, false);
            }

            // setup UI
            TextView name = convertView.findViewById(R.id.recipe_name);
            name.setText(recipes.get(position).getName());
            //TODO: save, display image
            ImageView img = convertView.findViewById(R.id.recipe_image);
            new FetchImg(recipes.get(position).getImgUrl(), img).start();

            // setup navigation
            convertView.findViewById(R.id.recipe_card).setOnClickListener(view1 -> {
                // get Ingredients
                try {
                    apiDataService.getRecipeByIdentifier(recipes.get(position).getIdentifingUri(), new ApiConnection.VolleyResponseListener() {
                        @Override
                        public void onError(String message) {
                            Log.e("Bookmark: get specific Recipe -> Error", message);
                        }

                        @Override
                        public void onResponse(Recipe recipe) {
                            // TODO: remove 2
                            NavDirections action = (NavDirections) BookmarkFragmentDirections.actionBookmarkFragmentToRecipeDetailFragment(recipe);
                            Navigation.findNavController(view1).navigate(action);
                        }
                    });
                } catch (UnsupportedEncodingException e) {
                    Log.e("UnsupportedEncodingException", e.getMessage());
                }
            });

            return convertView;
        }
    }
}