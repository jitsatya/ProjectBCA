package com.projects.satyajit.projectbca;

public class PutDataToShoppingListCompare {
    private String foodName[] = new String[20];

    public void setFoodName(String foodName)
    {
        for(int i=0; i<this.foodName.length;i++){
            this.foodName[i]= foodName;
        }
    }

    public  String getFoodName(){
        String fname=null;
        for(int i=0; i<this.foodName.length;i++){
            fname = foodName[i];
        }
        return fname;
    }

}
