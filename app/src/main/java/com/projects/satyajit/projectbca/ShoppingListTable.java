package com.projects.satyajit.projectbca;

public class ShoppingListTable {
    private String id,ndbno, name, energy, protein, fat, carbohydrate;

    public ShoppingListTable() {

    }



    public ShoppingListTable(String id, String ndbno, String name, String energy, String protein, String fat, String carbohydrate) {
        this.id = id;
        this.ndbno = ndbno;
        this.name = name;
        this.energy = energy;
        this.protein = protein;
        this.fat = fat;
        this.carbohydrate = carbohydrate;
    }

    public String getId() {
        return id;
    }

    public String getNdbno() {
        return ndbno;
    }

    public String getName() {
        return name;
    }

    public String getEnergy() {
        return energy;
    }

    public String getProtein() {
        return protein;
    }

    public String getFat() {
        return fat;
    }

    public String getCarbohydrate() {
        return carbohydrate;
    }
}
