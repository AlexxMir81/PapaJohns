package com.example.papajohns.models;

public class CategoryApp {
    private int id;
    private String name;
    private int imageCategory;
    public CategoryApp() {
    }

    public CategoryApp(int id, String name, int imageCategory) {
        this.id = id;
        this.name = name;
        this.imageCategory = imageCategory;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getImageCategory() {
        return imageCategory;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImageCategory(int imageCategory) {
        this.imageCategory = imageCategory;
    }

    @Override
    public String toString() {
        return "CategoryApp{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
