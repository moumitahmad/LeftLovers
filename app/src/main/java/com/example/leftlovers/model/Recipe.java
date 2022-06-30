package com.example.leftlovers.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;


public class Recipe implements Parcelable {

    private boolean isBookmarked = false;
    private String name;
    private String imgUrl;
    private List<Ingredient> ingredients;
    private String description;
    private String link;

    public Recipe(String name, String imgUrl, List<Ingredient> ingredients, String description, String link) {
        this.name = name;
        this.imgUrl = imgUrl;
        this.ingredients = ingredients;
        this.description = description;
        this.link = link;
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

    public String getDescription() {
        return description;
    }

    public String getLink() {
        return link;
    }

    public void setBookmarked(boolean bookmarked) {
        isBookmarked = bookmarked;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return "RecipeModel{" +
                "name='" + name + '\'' +
                '}';
    }
}
