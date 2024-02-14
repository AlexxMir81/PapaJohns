package com.example.papajohns.models;

public class Product {
    private int id;
    private String name;
    private int cost;
    private String composition;
    private int image;
    private int category;


    public Product() {
    }
    public Product(String name, int cost, String composition, int image, int category) {
        this.name = name;
        this.cost = cost;
        this.composition = composition;
        this.image = image;
        this.category = category;
    }
    public Product(int id, String name, int cost, String composition, int image, int category) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.composition = composition;
        this.image = image;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getCategory() {
        return category;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getComposition() {
        return composition;
    }

    public void setComposition(String composition) {
        this.composition = composition;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
    public void setCategory(int category) {
        this.category = category;
    }

}
