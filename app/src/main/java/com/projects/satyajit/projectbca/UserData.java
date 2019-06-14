package com.projects.satyajit.projectbca;

import java.util.List;

public class UserData {
    private String fName;
    private String lName;
    private String email;
    private String uImage;
    private String age;
    private String height;
    private String weight;
    private String gender;
    private FoodItem shoppingList;
    private FoodItem compare;
    private FoodItem recent;
    private FoodItemToFirebase foodItemToFirebase;

    public UserData() {
    }

    public UserData(String fName, String lName, String email, String uImage, String age,
                    String height, String weight, String gender, FoodItem shoppingList,FoodItem compare, FoodItem recent) {
        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.uImage = uImage;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.gender = gender;
        this.shoppingList = shoppingList;
        this.compare = compare;
        this.recent = recent;
    }

    public FoodItem getRecent() {
        return recent;
    }

    public void setRecent(FoodItem recent) {
        this.recent = recent;
    }

    public FoodItemToFirebase getFoodItemToFirebase() {
        return foodItemToFirebase;
    }

    public void setFoodItemToFirebase(FoodItemToFirebase foodItemToFirebase) {
        this.foodItemToFirebase = foodItemToFirebase;
    }

    public FoodItem getShoppingList() {
        return shoppingList;
    }

    public void setShoppingList(FoodItem shoppingList) {
        this.shoppingList = shoppingList;
    }

    public FoodItem getCompare() {
        return compare;
    }

    public void setCompare(FoodItem compare) {
        this.compare = compare;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getuImage() {
        return uImage;
    }

    public void setuImage(String uImage) {
        this.uImage = uImage;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
