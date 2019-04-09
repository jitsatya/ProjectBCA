package com.projects.satyajit.projectbca;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailedActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    DatabaseHelper myDb;
    TextView name, energy, protein, fat, carbohydrate, measureText;
    ImageView foodImage;
    Toolbar toolbar;
    private String ndbno, imageUrl, mFoodName, mEnergy, mProtien, mFat, mCarbohydrate;
    FragmentShoppingList fragmentShoppingList = new FragmentShoppingList();

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
        measureText = findViewById(R.id.measure_text);
        measureText.setVisibility(View.INVISIBLE);
        //foodImage.setImageResource(R.drawable.placeholder_image);
        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        myDb = new DatabaseHelper(this);
        getSupportActionBar().setTitle("Details of " + getIntent().getStringExtra("name"));
        ndbno = getIntent().getStringExtra("ndbno");
        imageUrl = getIntent().getStringExtra("image");
        Glide.with(this).load(imageUrl).into(foodImage);
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
                mFoodName = food.getName();
                List<Nutrient> nutrient = food.getNutrients();
                for(int i=0; i<nutrient.size();i++){
                    int id = Integer.parseInt(nutrient.get(i).getNutrientId());
                    switch (id){
                        case 208:
                            energy.setText("Energy: "+nutrient.get(i).getValue());
                            mEnergy = nutrient.get(i).getValue();
                        case 203:
                            protein.setText("Protein: "+nutrient.get(i).getValue());
                            mProtien = nutrient.get(i).getValue();
                        case 204:
                            fat.setText("Fat: " +nutrient.get(i).getValue());
                            mFat = nutrient.get(i).getValue();
                        case 205:
                            carbohydrate.setText("Carbohydrate: "+nutrient.get(i).getValue());
                            mCarbohydrate = nutrient.get(i).getValue();
                    }

                }
                measureText.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<FoodReport> call, Throwable t) {
                Toast.makeText(DetailedActivity.this, "Error Occurred!", Toast.LENGTH_SHORT).show();
            }
        });
    }




    public void ShowOptions(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.detailed_activity_popup);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.add_to_shopping_list:

                boolean isInserted = myDb.insertShoppingListData(ndbno,mFoodName, mEnergy, mProtien, mFat, mCarbohydrate);
                if(isInserted) {
                    Toast.makeText(this, "Added to shopping List", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(this, "Error Occured While Adding to Shoppind List", Toast.LENGTH_SHORT).show();
                }
                //fragmentShoppingList.setFoodName(mFoodName);

                return  true;

            case R.id.add_to_compare:
                boolean isInserted1 = myDb.insertCompareData(ndbno, mFoodName, mEnergy, mProtien, mFat, mCarbohydrate);
                if(isInserted1) {
                    Toast.makeText(this, "Added to Compare", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(this, "Error Occured While Adding to Compare", Toast.LENGTH_SHORT).show();
                }
                //fragmentShoppingList.setFoodName(mFoodName);

                return  true;
             default:
                 return false;
        }
    }

}
