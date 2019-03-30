package com.projects.satyajit.projectbca;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailedActivity extends AppCompatActivity {
    TextView name, energy, protein, fat, carbohydrate;
    ImageView foodImage;
    Toolbar toolbar;
    private String ndbno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        name = findViewById(R.id.food_name);
        energy = findViewById(R.id.energy);
        protein = findViewById(R.id.protein);
        fat = findViewById(R.id.fat);
        carbohydrate = findViewById(R.id.carbohydrate);
        foodImage = findViewById(R.id.food_image);
        foodImage.setImageResource(R.drawable.placeholder_image);
        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Details of " + getIntent().getStringExtra("name"));
        ndbno = getIntent().getStringExtra("ndbno");
        //Toast.makeText(this, getIntent().getStringExtra("ndbno"), Toast.LENGTH_SHORT).show();
        getData();
    }

    private void getData() {
        Call<FoodReport> list = UsdaApi.getService().getFoodReport(ndbno);
        list.enqueue(new Callback<FoodReport>() {
            @Override
            public void onResponse(Call<FoodReport> call, Response<FoodReport> response) {
                FoodReport report = response.body();
                Report report1 = report.getReport();
                Food food = report1.getFood();
                name.setText(food.getName());
                //setting data to PutDataToShoppingListCompare
                PutDataToShoppingListCompare putDataToShoppingListCompare = new PutDataToShoppingListCompare();
                putDataToShoppingListCompare.setFoodName(food.getName());
                List<Nutrient> nutrient = food.getNutrients();
                for(int i=0; i<nutrient.size();i++){
                    int id = Integer.parseInt(nutrient.get(i).getNutrientId());
                    switch (id){
                        case 208:
                            energy.setText("Energy: "+nutrient.get(i).getValue());
                        case 203:
                            protein.setText("Protein: "+nutrient.get(i).getValue());
                        case 204:
                            fat.setText("Fat: " +nutrient.get(i).getValue());
                        case 205:
                            carbohydrate.setText("Carbohydrate: "+nutrient.get(i).getValue());
                    }

                }

                // List list = new List();
                //list = resultList.getList();
                //recyclerView.setAdapter(new SearchListAdapter(DetailedActivity.this, list.getItem()));
                //Toast.makeText(DetailedActivity.this, "success!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<FoodReport> call, Throwable t) {
                Toast.makeText(DetailedActivity.this, "Error Occurred!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
