package com.projects.satyajit.projectbca;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FragmentHome extends Fragment {
    DatabaseHelper myDb;
    private int[] itemImages = {R.drawable.placeholder_image};
    ArrayList<String> itemSlNo;
    List<FoodItem> foodItems, foodItems1, foodItems2, foodItems3;
    List <String> keyList, keyList1, keyList2, keyList3 ;
    private ProgressBar progressBar;
    private LinearLayout linearLayout;
    FirebaseAuth mAuth;
    private RecyclerView addedByUser;
    private List<FoodItemToFirebase> items = new ArrayList<>();
    HomeHorizontalAdapter userAddedAdapter;
    private DatabaseReference dbUserAddedfood;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        progressBar = view.findViewById(R.id.home_progressBar);
        linearLayout = view.findViewById(R.id.home_linear_layout);
        linearLayout.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
                linearLayout.setVisibility(View.VISIBLE);
            }
        },4000);

        final RecyclerView recentItems = view.findViewById(R.id.recent_items);
        recentItems.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        //You might also like
        final RecyclerView highEnergy = view.findViewById(R.id.high_energy);
        highEnergy.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        //High in protein
        final RecyclerView highProtein = view.findViewById(R.id.high_in_protein);
        highProtein.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        //Added By User
        addedByUser = view.findViewById(R.id.user_added_foods);
        addedByUser.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        itemSlNo = new ArrayList<>();

        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getUid()!=null){
        String userId = mAuth.getUid();
            new FirebaseDatabaseHelper("UserData").ReadFoodData(userId,"RecentItems",new FirebaseDatabaseHelper.DataStatus() {
                @Override
                public void DataIsLoaded(java.util.List<FoodItem> foods, List<String> keys) {
                    foodItems = foods;
                    Collections.reverse(foodItems);
                    keyList = keys;
                    recentItems.setAdapter(new HomeHorizontalAdapter(recentItems.getContext(), foodItems));
                }

                @Override
                public void DataIsInserted() {

                }

                @Override
                public void DataIsUpdated() {

                }
            });
        }


        new FirebaseDatabaseHelper("HighProtein").ReadFoodHighProteinData( new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(java.util.List<FoodItem> foods, List<String> keys) {
                foodItems1 = foods;
                Collections.shuffle(foodItems1);
                keyList1 = keys;
                highProtein.setAdapter(new HomeHorizontalAdapter(highProtein.getContext(), foodItems1));
            }

            @Override
            public void DataIsInserted() {

            }

            @Override
            public void DataIsUpdated() {

            }
        });

        new FirebaseDatabaseHelper("HighEnergy").ReadFoodHighProteinData( new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(java.util.List<FoodItem> foods, List<String> keys) {
                foodItems2 = foods;
                Collections.shuffle(foodItems2);
                keyList2 = keys;
                highEnergy.setAdapter(new HomeHorizontalAdapter(highEnergy.getContext(), foodItems2));
            }

            @Override
            public void DataIsInserted() {

            }

            @Override
            public void DataIsUpdated() {

            }
        });


           // getUserAddedData();



        return view;
    }
    public  void getUserAddedData(){
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                items.clear();
                if (dataSnapshot.exists()){
                    for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                        FoodItemToFirebase foodItemToFirebase = snapshot.getValue(FoodItemToFirebase.class);
                        items.add(foodItemToFirebase);
                        FoodItem foodItem = new FoodItem();

                        foodItem.setName(foodItemToFirebase.getName());
                        foodItem.setimage(foodItemToFirebase.getImage());
                        foodItems3.add(foodItem);
                    }
                    userAddedAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        dbUserAddedfood = FirebaseDatabase.getInstance().getReference("FoodDataBase");
        dbUserAddedfood.addListenerForSingleValueEvent(valueEventListener);
        /*for (int i=0; i<items.size(); i++){
            FoodItem foodItem = new FoodItem();

            foodItem.setName(items.get(i).getName());
            foodItem.setimage(items.get(i).getImage());
            foodItems3.add(foodItem);
        }*/
        userAddedAdapter = new HomeHorizontalAdapter(getActivity(), foodItems3);
        addedByUser.setAdapter(userAddedAdapter);

    }
}
