package com.example.leftlovers.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.time.LocalDate;
import java.util.Date;

@Entity
public class Ingredient implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int ingredientId;

    @ColumnInfo(name = "ingredient_name")
    private String name;

    @ColumnInfo(name = "img_url")
    private String imgUrl;


    //Text with the amount of the Ingredient
    //for example "1 cup of sugar"
    @Ignore
    private String measureText;

    // own ingredient
    @ColumnInfo(name = "amount")
    private int amount;
    @ColumnInfo(name = "expirationDate")
    private String expirationDate;


    @ColumnInfo(name = "notes")
    private String notes;

    @Ignore
    public Ingredient(String name, String imgUrl, int amount, String expirationDate, String notes) {
        this.name = name;
        this.imgUrl = imgUrl;
        this.amount = amount;
        this.expirationDate = expirationDate;
        this.notes = notes;
    }

    @Ignore
    public Ingredient(String name, String imgUrl) {
        this.name = name;
        this.imgUrl = imgUrl;
    }


    public Ingredient(){}

    public int getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(int ingredientId) {
        this.ingredientId = ingredientId;
    }

    public String getName() {
        return name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getMeasureText() {
        return measureText;
    }

    public int getAmount() {
        return amount;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setMeasureText(String measureText) {
        this.measureText = measureText;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Ignore
    protected Ingredient(Parcel in) {
        name = in.readString();
        imgUrl = in.readString();
    }

    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
    }
}
