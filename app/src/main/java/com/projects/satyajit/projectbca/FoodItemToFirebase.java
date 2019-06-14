package com.projects.satyajit.projectbca;

public class FoodItemToFirebase {
    private String name, image, energy, fat, saturatedFat, monounsaturatedfat, PolyUnsaturatedFat, TransFat,
    Cholesterol, Carbohydrate, Sugar, DietaryFibre, Protein, Sodium, Iron, Magnesium, Zinc, id;

    public FoodItemToFirebase() {
    }

    public FoodItemToFirebase(String name, String iamge, String energy, String fat, String carbohydrate, String protein) {
        this.name = name;
        this.image = iamge;
        this.energy = energy;
        this.fat = fat;
        Carbohydrate = carbohydrate;
        Protein = protein;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIamge() {
        return image;
    }

    public void setIamge(String iamge) {
        this.image = iamge;
    }

    public String getEnergy() {
        return energy;
    }

    public void setEnergy(String energy) {
        this.energy = energy;
    }

    public String getFat() {
        return fat;
    }

    public void setFat(String fat) {
        this.fat = fat;
    }

    public String getSaturatedFat() {
        return saturatedFat;
    }

    public void setSaturatedFat(String saturatedFat) {
        this.saturatedFat = saturatedFat;
    }

    public String getMonounsaturatedfat() {
        return monounsaturatedfat;
    }

    public void setMonounsaturatedfat(String monounsaturatedfat) {
        this.monounsaturatedfat = monounsaturatedfat;
    }

    public String getPolyUnsaturatedFat() {
        return PolyUnsaturatedFat;
    }

    public void setPolyUnsaturatedFat(String polyUnsaturatedFat) {
        PolyUnsaturatedFat = polyUnsaturatedFat;
    }

    public String getTransFat() {
        return TransFat;
    }

    public void setTransFat(String transFat) {
        TransFat = transFat;
    }

    public String getCholesterol() {
        return Cholesterol;
    }

    public void setCholesterol(String cholesterol) {
        Cholesterol = cholesterol;
    }

    public String getCarbohydrate() {
        return Carbohydrate;
    }

    public void setCarbohydrate(String carbohydrate) {
        Carbohydrate = carbohydrate;
    }

    public String getSugar() {
        return Sugar;
    }

    public void setSugar(String sugar) {
        Sugar = sugar;
    }

    public String getDietaryFibre() {
        return DietaryFibre;
    }

    public void setDietaryFibre(String dietaryFibre) {
        DietaryFibre = dietaryFibre;
    }

    public String getProtein() {
        return Protein;
    }

    public void setProtein(String protein) {
        Protein = protein;
    }

    public String getSodium() {
        return Sodium;
    }

    public void setSodium(String sodium) {
        Sodium = sodium;
    }

    public String getIron() {
        return Iron;
    }

    public void setIron(String iron) {
        Iron = iron;
    }

    public String getMagnesium() {
        return Magnesium;
    }

    public void setMagnesium(String magnesium) {
        Magnesium = magnesium;
    }

    public String getZinc() {
        return Zinc;
    }

    public void setZinc(String zinc) {
        Zinc = zinc;
    }
}
