package com.example.leftlovers.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;


@Entity
public class Recipe implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    public int recipeId;

    @ColumnInfo(name = "recipe_name")
    private String name;

    @ColumnInfo(name = "recipe_id")
    private String identifingUri;

    @ColumnInfo(name = "recipe_img")
    private String imgUrl;

    @Ignore
    private String instructionsUrl;

    @Ignore
    private boolean isBookmarked = false;
    @Ignore
    private List<Ingredient> ingredients;


    @Ignore
    public Recipe(String name, String imgUrl, List<Ingredient> ingredients, String identifingUri, String instructionsUrl) {
        this.name = name;
        this.imgUrl = imgUrl;
        this.ingredients = ingredients;
        this.identifingUri = identifingUri;
        this.instructionsUrl = instructionsUrl;
    }


    public Recipe(String name){
        this.name = name;
    }

    public boolean isBookmarked() {
        return isBookmarked;
    }

    public String getName() {
        return name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }


    public String getIdentifingUri() {
        return identifingUri;
    }

    public void setBookmarked(boolean bookmarked) {
        isBookmarked = bookmarked;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public String getInstructionsUrl() {
        return instructionsUrl;
    }

    public void setInstructionsUrl(String instructionsUrl) {
        this.instructionsUrl = instructionsUrl;
    }


    public void setIdentifingUri(String identifingUri) {
        this.identifingUri = identifingUri;
    }

    @Override
    public String toString() {
        return "RecipeModel{" +
                "name='" + name + '\'' +
                '}';
    }


    /*
     * Implementation of Parcelable interface
     */

    protected Recipe(Parcel in) {
        name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) { return new Recipe[size]; }
    };

}