package com.projects.satyajit.projectbca;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FoodItem {


    private String ndbno;

    private String name;

    private String ds;

    private String manu;

    private String ru;

    private String image;

    public FoodItem() {
    }

    public FoodItem(String ndbno, String name, String ds, String manu, String ru, String image, List<Nutrient> nutrients) {
        this.ndbno = ndbno;
        this.name = name;
        this.ds = ds;
        this.manu = manu;
        this.ru = ru;
        this.image = image;
        this.nutrients = nutrients;
    }

    private List<Nutrient> nutrients = null;

    public String getNdbno() {
        return ndbno;
    }

    public void setNdbno(String ndbno) {
        this.ndbno = ndbno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDs() {
        return ds;
    }

    public void setDs(String ds) {
        this.ds = ds;
    }

    public String getManu() {
        return manu;
    }

    public void setManu(String manu) {
        this.manu = manu;
    }

    public String getRu() {
        return ru;
    }

    public void setRu(String ru) {
        this.ru = ru;
    }

    public void setimage(String image){
        this.image = image;
    }

    public String getImage(){
        return image;
    }

    public List<Nutrient> getNutrients() {
        return nutrients;
    }

    public void setNutrients(List<Nutrient> nutrients) {
        this.nutrients = nutrients;
    }

}
