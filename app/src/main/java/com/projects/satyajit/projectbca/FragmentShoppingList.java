package com.projects.satyajit.projectbca;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class FragmentShoppingList extends Fragment {
    TextView tCalories, tProtein, tCarbs, tFat;
    float totalCalories, totalProtein, totalCarbs, totalFat;
    ArrayList<Integer> mQuantity;
    DatabaseHelper myDb;
    ArrayList <String> nbdnoList,mNbdnoList;
    List <String> keyList;
    List<FoodItem> foodItems;
    private FirebaseAuth mAuth;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopping_list, container, false);
        final RecyclerView shoppingList = view.findViewById(R.id.shopping_list_items_recycleview);
        shoppingList.setLayoutManager(new LinearLayoutManager(getActivity()));

        nbdnoList = new ArrayList<String>();
        //Pending entering path
        keyList = new ArrayList<>();
        foodItems = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if (mAuth.getUid()!=null) {
            String userId = user.getUid();

            new FirebaseDatabaseHelper("UserData").ReadFoodData(userId, "shoppingList", new FirebaseDatabaseHelper.DataStatus() {
                @Override
                public void DataIsLoaded(List<FoodItem> foods, List<String> keys) {
                    foodItems = foods;
                    keyList = keys;
                    shoppingList.setAdapter(new ShoppingListAdapter(shoppingList.getContext(), keyList, foodItems));
                }

                @Override
                public void DataIsInserted() {

                }

                @Override
                public void DataIsUpdated() {

                }
            });
        }
        else {
            Toast.makeText(getActivity(), "Please Login To get ShoppingList", Toast.LENGTH_LONG).show();
        }

        return view;

    }



}
