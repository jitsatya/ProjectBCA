package com.projects.satyajit.projectbca;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DetailedActivityForAddedData extends AppCompatActivity {

    TextView name, energy, protein, fat, carbohydrate, measureText;
    String id, imageUrl, mName;
    FoodItemToFirebase food = new FoodItemToFirebase();
    ImageView foodImage;
    Toolbar toolbar;
    private DatabaseReference dbUserAddedfood;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_for_added_data);
        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getSupportActionBar().setTitle("Details of " + getIntent().getStringExtra("name"));
        name = findViewById(R.id.food_name);
        energy = findViewById(R.id.energy);
        protein = findViewById(R.id.protein);
        fat = findViewById(R.id.fat);
        carbohydrate = findViewById(R.id.carbohydrate);
        foodImage = findViewById(R.id.food_image);

        id = getIntent().getStringExtra("id");
        imageUrl = getIntent().getStringExtra("image");
        mName = getIntent().getStringExtra("name");



        Picasso.get().load(imageUrl).into(foodImage);
        name.setText(mName);
        getdata();

       // energy.setText("Energy: "+ food.getEnergy());


    }

    private void getdata(){
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        FoodItemToFirebase artist = snapshot.getValue(FoodItemToFirebase.class);
                        food = artist;
                        energy.setText("Energy: "+ food.getEnergy());
                        protein.setText("Protein: "+artist.getProtein());
                        carbohydrate.setText("Carbohydrate: "+artist.getCarbohydrate());
                        fat.setText("Fat: "+ artist.getFat());
                    }
                }
            }



            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        dbUserAddedfood = FirebaseDatabase.getInstance().getReference("FoodDataBase");
        Query query6 = FirebaseDatabase.getInstance().getReference("FoodDataBase")
                .orderByChild("id")
                .equalTo(id);
        query6.addListenerForSingleValueEvent(valueEventListener);


    }
    public void ShowOptions(View view) {
    }
}
