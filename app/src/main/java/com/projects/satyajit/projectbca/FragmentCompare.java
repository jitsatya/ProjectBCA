package com.projects.satyajit.projectbca;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class FragmentCompare extends Fragment {
    DatabaseHelper myDb;
    ArrayList<String> foodlist;
    List<FoodItem> foodItems;
    List <String> keyList;
    FirebaseAuth mAuth;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_compare, container, false);
        final RecyclerView compareList = view.findViewById(R.id.compare_list);
        compareList.setLayoutManager(new LinearLayoutManager(getActivity()));
        foodlist = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getUid()!=null){
        String userId = mAuth.getUid();
            new FirebaseDatabaseHelper("UserData").ReadFoodData(userId,"Compare",new FirebaseDatabaseHelper.DataStatus() {
                @Override
                public void DataIsLoaded(java.util.List<FoodItem> foods, List<String> keys) {
                    foodItems = foods;
                    keyList = keys;
                    compareList.setAdapter(new CompareListAdapter(compareList.getContext(),keyList, foodItems));
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
            Toast.makeText(getActivity(), "Please Login To get Compare", Toast.LENGTH_LONG).show();
        }


        return view;
    }
}
