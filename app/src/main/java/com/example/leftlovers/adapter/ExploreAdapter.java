package com.example.leftlovers.adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.leftlovers.R;
import com.example.leftlovers.model.Recipe;
import com.example.leftlovers.util.FetchImg;
import com.example.leftlovers.view.searchPage.SearchFragmentDirections;

import java.util.List;

//Basic adapter
public class ExploreAdapter extends RecyclerView.Adapter<ExploreAdapter.ViewHolder> {

    private View exploreView;


    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView recipeImage;
        public TextView recipeName;


        public ViewHolder(View itemView) {
            super(itemView);

            recipeImage = (ImageView) itemView.findViewById(R.id.rImage);
            recipeName = (TextView) itemView.findViewById(R.id.rName);
        }
    }

        private List<Recipe> mRecipes;

        public ExploreAdapter(List<Recipe> recipes) {
            mRecipes = recipes;
        }

        @Override
        public ExploreAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);

            exploreView = inflater.inflate(R.layout.explore, parent, false);

            ViewHolder viewHolder = new ViewHolder(exploreView);


            return viewHolder;
        }

        public void onBindViewHolder(ExploreAdapter.ViewHolder holder, int position) {
            Recipe recipe = mRecipes.get(position);

            //TODO get image
            ImageView imageView = holder.recipeImage;
            new FetchImg(mRecipes.get(position).getImgUrl(), imageView).start();
            TextView textView = holder.recipeName;
            textView.setText(recipe.getName());

            // setup navigation

            exploreView.findViewById(R.id.explore_cardview).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NavDirections action = com.example.leftlovers.view.searchPage.SearchFragmentDirections.actionSearchFragmentToRecipeDetailFragment(mRecipes.get(holder.getAdapterPosition()));
                    Navigation.findNavController(view).navigate(action);
                }
            });
        }



        @Override
        public int getItemCount() {
            return mRecipes.size();
        }


}
