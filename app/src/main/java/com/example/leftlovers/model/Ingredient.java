package com.example.leftlovers.model;

public class Ingredient {
    private String name;
    private String imgUrl;

    public Ingredient(String name, String imgUrl) {
        this.name = name;
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public String getImgUrl() {
        return imgUrl;
    }
}
