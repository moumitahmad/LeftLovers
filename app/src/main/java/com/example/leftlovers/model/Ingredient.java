package com.example.leftlovers.model;

public class Ingredient {
    private String name;
    private String imgUrl;

    //Text with the amount of the Indridient
    //for example "1 cup of suger"
    private String measureText;

    public Ingredient(String name, String imgUrl) {
        this.name = name;
        this.imgUrl = imgUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getMeasureText() {
        return measureText;
    }

    public void setMeasureText(String measureText) {
        this.measureText = measureText;
    }

    public String getName() {
        return name;
    }

    public String getImgUrl() {
        return imgUrl;
    }
}
