package com.projects.satyajit.projectbca;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.projects.satyajit.projectbca.Food;

import java.util.ArrayList;
import java.util.List;

public class FirebaseDatabaseHelper {
    FirebaseDatabase mDatabase;
    DatabaseReference mFoodReference;

    List<FoodItem> foods = new ArrayList<>();
    List<ShoppingListTable> shoppingListItem = new ArrayList<>();
    List<UserData> userData = new ArrayList<>();

    public interface UserDataStatus{
        void DataIsLoaded(List<UserData> foods, List<String>keys);
        void DataIsInserted();
        void DataIsUpdated();
    }

    public interface DataStatus{
        void DataIsLoaded(List<FoodItem> foods, List<String>keys);
        void DataIsInserted();
        void DataIsUpdated();
    }

    public FirebaseDatabaseHelper(String path) {
        mDatabase = FirebaseDatabase.getInstance();
        mFoodReference = mDatabase.getReference(path);
    }

    public void ReadFoodData(String child, String nextChild, final DataStatus dataStatus){
        mFoodReference.child(child).child(nextChild).limitToLast(25).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                foods.clear();
                List<String> keys = new ArrayList<>();
                for(DataSnapshot keyNode: dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    FoodItem food = keyNode.getValue(FoodItem.class);
                    foods.add(food);
                }
                dataStatus.DataIsLoaded(foods,keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void ReadFoodHighProteinData(final DataStatus dataStatus){
        mFoodReference.limitToLast(100).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                foods.clear();
                List<String> keys = new ArrayList<>();
                for(DataSnapshot keyNode: dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    FoodItem food = keyNode.getValue(FoodItem.class);
                    foods.add(food);
                }
                dataStatus.DataIsLoaded(foods,keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void addFoodItem(FoodItem food, final DataStatus dataStatus){
        String key = mFoodReference.push().getKey();
        mFoodReference.child(key).setValue(food).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dataStatus.DataIsInserted();
            }
        });

    }
    public void addUserData(String userId, UserData userData, final UserDataStatus dataStatus) {
        mFoodReference.child(userId).setValue(userData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dataStatus.DataIsInserted();
            }
        });


    }

    public void upDateUserdata(String userId, UserData userData, final UserDataStatus dataStatus){
            mFoodReference.child(userId).setValue(userData).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                        dataStatus.DataIsUpdated();
                }
            });
    }
    public void upDateUserShoppingList(String userId, String child, UserData userData, final UserDataStatus dataStatus){
        String key = mFoodReference.push().getKey();
        FoodItem foodItem = userData.getShoppingList();
        mFoodReference.child(userId).child(child).child(key).setValue(foodItem).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dataStatus.DataIsUpdated();
            }
        });
    }

    public void deleteUserShoppingList(String userId, String child, UserData userData, final UserDataStatus dataStatus){
        String key = mFoodReference.push().getKey();
        FoodItem foodItem = userData.getShoppingList();
        mFoodReference.child(userId).child(child).child(key).setValue(foodItem).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dataStatus.DataIsUpdated();
            }
        });
    }
    public void upDateUserCompareList(String userId, String child, UserData userData, final UserDataStatus dataStatus){

        String key = mFoodReference.push().getKey();
        FoodItem foodItem  = userData.getCompare();
        mFoodReference.child(userId).child(child).child(key).setValue(foodItem).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dataStatus.DataIsUpdated();
            }
        });
    }
    public void upDateUserHistory(String userId, String child, UserData userData, final UserDataStatus dataStatus){

        String key = mFoodReference.push().getKey();
        FoodItem foodItem  = userData.getRecent();
        mFoodReference.child(userId).child(child).child(key).setValue(foodItem).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dataStatus.DataIsUpdated();
            }
        });
    }
}
