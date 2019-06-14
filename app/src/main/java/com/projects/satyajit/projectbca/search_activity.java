package com.projects.satyajit.projectbca;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class search_activity extends AppCompatActivity {
    private EditText searchField;
    private TextView displayResults;
    private ImageButton searchButton;
    FloatingActionButton addFood;
    Toolbar toolbar;
    private String searchKey;
    RecyclerView recyclerView, userAddedRecyclerView;
    java.util.List <Item> mList = new ArrayList<>();
    java.util.List <Hit> images = new ArrayList<>();
    java.util.List<FoodItemToFirebase> items = new ArrayList<>();
    private UserAddedSearchListAdapter userAddedSearchListAdapter;
    private DatabaseReference dbUserAddedfood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_activity);
        recyclerView =(RecyclerView)findViewById(R.id.search_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        userAddedRecyclerView = findViewById(R.id.searchList_userAdded);
        userAddedRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchField = (EditText) findViewById(R.id.searchField);
        searchButton = (ImageButton) findViewById(R.id.searchButton);
        addFood = findViewById(R.id.add_food);
        addFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(search_activity.this,AddFoodToFirebase.class);
                startActivity(intent);
            }
        });
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchKey = searchField.getText().toString();
                getData();

            }
        });
        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void getData() {
        Call<SearchResult> list = UsdaApi.getService().getFoodList(searchKey);
        list.enqueue(new Callback<SearchResult>() {
            @Override
            public void onResponse(Call<SearchResult> call, Response<SearchResult> response) {
                SearchResult resultList = response.body();
                List list = resultList.getList();
                if (list==null){
                    Toast.makeText(search_activity.this, "No food results found!", Toast.LENGTH_SHORT).show();
                }
                else {
                    settingItem(list.getItem());
                    //recyclerView.setAdapter(new SearchListAdapter(search_activity.this, list.getItem(),));
                }
            }

            @Override
            public void onFailure(Call<SearchResult> call, Throwable t) {
                Toast.makeText(search_activity.this, "Error Occurred!", Toast.LENGTH_SHORT).show();
            }
        });
        getImage();
        settingAdapter(mList, images);
        getUserAddedData();

    }

    private void getImage() {
        Call<FoodImage> list = PixabayApi.getService().getFoodImageList(searchKey);
        list.enqueue(new Callback<FoodImage>() {
            @Override
            public void onResponse(Call<FoodImage> call, Response<FoodImage> response) {
                FoodImage resultList = response.body();
                if (resultList==null){
                    Toast.makeText(search_activity.this, "No image results found!", Toast.LENGTH_SHORT).show();
                }
                else {
                    settingImage(resultList.getHits());
                    //recyclerView.setAdapter(new SearchListAdapter(search_activity.this, resultList.getHits());
                }
            }

            @Override
            public void onFailure(Call<FoodImage> call, Throwable t) {
                Toast.makeText(search_activity.this, "Error Occurred!", Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void settingItem(java.util.List<Item> items){
        mList = items;
    }
    public void settingImage(java.util.List<Hit> images)
    {
        this.images = images;
    }

    public void settingAdapter(java.util.List<Item> items,java.util.List<Hit> images){
        recyclerView.setAdapter(new SearchListAdapter(search_activity.this, items,images));
        userAddedSearchListAdapter = new UserAddedSearchListAdapter(this, this.items);
        userAddedRecyclerView.setAdapter(userAddedSearchListAdapter);
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
                    }
                    userAddedSearchListAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        dbUserAddedfood = FirebaseDatabase.getInstance().getReference("FoodDataBase");

        Query query = FirebaseDatabase.getInstance().getReference("FoodDataBase")
                .orderByChild("name")
                .startAt(searchKey)
                .endAt(searchKey+"\uf8ff");

        query.addListenerForSingleValueEvent(valueEventListener);
    }
}

