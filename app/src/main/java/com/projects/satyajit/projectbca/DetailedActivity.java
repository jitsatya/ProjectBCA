package com.projects.satyajit.projectbca;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailedActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    DatabaseHelper myDb;
    FoodItem foodItem;
    TextView name, energy, protein, fat, carbohydrate, measureText, tableitemName;
    ListView nutritionTable;
    //DatabaseReference dbshoppingListTable;
    String[] NUTRITION_NAMES = {"Total Fat", "Saturated Fat", "Polyunsaturated Fat", "Monounsaturated fat", "Cholesterol", "Sodium",
    "Potassium", "Total Carbohydrate", "Dietary Fibre", "Sugar", "Protein", "Vitamin A", "Vitamin D", "Vitamin C", "Vitamin B-6", "Calcium",
            "Iron", "Magnesium"};

    String[] NUTRITION_VALUES = new String[18];
    ImageView foodImage;
    Toolbar toolbar;
    private String ndbno, imageUrl, mFoodName, mEnergy, mProtien, mFat, mCarbohydrate;
    FragmentShoppingList fragmentShoppingList = new FragmentShoppingList();
    FirebaseAuth mAuth;

    String UserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        name = findViewById(R.id.food_name);
        energy = findViewById(R.id.energy);
        protein = findViewById(R.id.protein);
        tableitemName = findViewById(R.id.nutritional_facts_table_item);
        fat = findViewById(R.id.fat);
        carbohydrate = findViewById(R.id.carbohydrate);
        foodImage = findViewById(R.id.food_image);
        nutritionTable = findViewById(R.id.nutrition_table);
        measureText = findViewById(R.id.measure_text);
        measureText.setVisibility(View.INVISIBLE);
        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
            UserId = user.getUid();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        CustomAdapter customAdapter = new CustomAdapter();
        nutritionTable.setAdapter(customAdapter);
        //Firebase table

        myDb = new DatabaseHelper(this);
        getSupportActionBar().setTitle("Details of " + getIntent().getStringExtra("name"));
        tableitemName.setText(getIntent().getStringExtra("name"));
        ndbno = getIntent().getStringExtra("ndbno");
        imageUrl = getIntent().getStringExtra("image");
        Picasso.get().load(imageUrl).into(foodImage);

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
                foodItem = new FoodItem(food.getNdbno(), food.getName(), food.getDs(),food.getManu(), food.getRu(),
                        imageUrl, food.getNutrients());

                Nutrient nutrient1;
                Nutrient energy1;
                nutrient1 = food.getNutrients().get(3);
                energy1 = food.getNutrients().get(1);
                float energyValue = Float.parseFloat(energy1.getValue());
                float value = Float.parseFloat(nutrient1.getValue());

                if (value>10)
                {
                    new FirebaseDatabaseHelper("HighProtein").addFoodItem(foodItem, new FirebaseDatabaseHelper.DataStatus() {
                        @Override
                        public void DataIsLoaded(List<FoodItem> foods, List<String> keys) {

                        }

                        @Override
                        public void DataIsInserted() {
                            Toast.makeText(getBaseContext(), "Added to High Protein", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void DataIsUpdated() {

                        }
                    });
                }
                if (energyValue>300)
                {
                    new FirebaseDatabaseHelper("HighEnergy").addFoodItem(foodItem, new FirebaseDatabaseHelper.DataStatus() {
                        @Override
                        public void DataIsLoaded(List<FoodItem> foods, List<String> keys) {

                        }

                        @Override
                        public void DataIsInserted() {
                            Toast.makeText(getBaseContext(), "Added to High Protein", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void DataIsUpdated() {

                        }
                    });
                }

                new FirebaseDatabaseHelper("FoodItems").addFoodItem(foodItem, new FirebaseDatabaseHelper.DataStatus() {
                    @Override
                    public void DataIsLoaded(List<FoodItem> foods, List<String> keys) {

                    }

                    @Override
                    public void DataIsInserted() {
                        Toast.makeText(getBaseContext(), "Added to Recent", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void DataIsUpdated() {

                    }
                });

                UserData userData = new UserData();
                userData.setRecent(foodItem);
                new FirebaseDatabaseHelper("UserData").upDateUserHistory(UserId,"RecentItems", userData, new FirebaseDatabaseHelper.UserDataStatus() {
                    @Override
                    public void DataIsLoaded(List<UserData> foods, List<String> keys) {

                    }

                    @Override
                    public void DataIsInserted() {

                    }

                    @Override
                    public void DataIsUpdated() {
                        Toast.makeText(DetailedActivity.this, "Added to History", Toast.LENGTH_SHORT).show();
                    }
                });
                name.setText(food.getName());
                mFoodName = food.getName();
                List<Nutrient> nutrient = food.getNutrients();
                for(int i=0; i<nutrient.size();i++){
                    int id = Integer.parseInt(nutrient.get(i).getNutrientId());
                    switch (id){
                        case 203:
                            protein.setText("Protein: "+nutrient.get(i).getValue());
                            mProtien = nutrient.get(i).getValue();
                            NUTRITION_VALUES[10] = nutrient.get(i).getValue();
                            break;
                        case 204:
                            fat.setText("Fat: " +nutrient.get(i).getValue());
                            mFat = nutrient.get(i).getValue();
                            NUTRITION_VALUES[0] = nutrient.get(i).getValue();
                            break;
                        case 205:
                            carbohydrate.setText("Carbohydrate: "+nutrient.get(i).getValue());
                            mCarbohydrate = nutrient.get(i).getValue();
                            NUTRITION_VALUES[7] = nutrient.get(i).getValue();
                            break;
                        case 208:
                            energy.setText("Energy: "+nutrient.get(i).getValue());
                            mEnergy = nutrient.get(i).getValue();
                            break;
                        case 269:
                            NUTRITION_VALUES[9] = nutrient.get(i).getValue();
                            break;
                        case 291:
                            NUTRITION_VALUES[8] = nutrient.get(i).getValue();
                            break;
                        case 301:
                            NUTRITION_VALUES[15] = nutrient.get(i).getValue();
                            break;
                        case 303:
                            NUTRITION_VALUES[16] = nutrient.get(i).getValue();
                            break;
                        case 304:
                            NUTRITION_VALUES[17] = nutrient.get(i).getValue();
                            break;
                        case 306:
                            NUTRITION_VALUES[6] = nutrient.get(i).getValue();
                            break;
                        case 307:
                            NUTRITION_VALUES[5] = nutrient.get(i).getValue();
                            break;
                        case 308:
                            NUTRITION_VALUES[11] = nutrient.get(i).getValue();
                            break;
                        case 324:
                            NUTRITION_VALUES[12] = nutrient.get(i).getValue();
                            break;
                        case 401:
                            NUTRITION_VALUES[13] = nutrient.get(i).getValue();
                            break;
                        case 415:
                            NUTRITION_VALUES[14] = nutrient.get(i).getValue();
                            break;
                        case 606:
                            NUTRITION_VALUES[1] = nutrient.get(i).getValue();
                            break;
                        case 601:
                            NUTRITION_VALUES[4] = nutrient.get(i).getValue();
                            break;
                        case 645:
                            NUTRITION_VALUES[3] = nutrient.get(i).getValue();
                            break;
                        case 646:
                            NUTRITION_VALUES[2] = nutrient.get(i).getValue();
                            break;

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
                        UserData userData = new UserData();
                        userData.setShoppingList(foodItem);
                new FirebaseDatabaseHelper("UserData").upDateUserShoppingList(UserId,"shoppingList", userData, new FirebaseDatabaseHelper.UserDataStatus() {
                    @Override
                    public void DataIsLoaded(List<UserData> foods, List<String> keys) {

                    }

                    @Override
                    public void DataIsInserted() {

                    }

                    @Override
                    public void DataIsUpdated() {
                        Toast.makeText(DetailedActivity.this, "Added to shopping List", Toast.LENGTH_SHORT).show();
                    }
                });



                return  true;

            case R.id.add_to_compare:

                UserData userData1 = new UserData();
                userData1.setCompare(foodItem);
                new FirebaseDatabaseHelper("UserData").upDateUserCompareList(UserId,"Compare", userData1, new FirebaseDatabaseHelper.UserDataStatus() {
                    @Override
                    public void DataIsLoaded(List<UserData> foods, List<String> keys) {

                    }

                    @Override
                    public void DataIsInserted() {

                    }

                    @Override
                    public void DataIsUpdated() {
                        Toast.makeText(DetailedActivity.this, "Added to Compare", Toast.LENGTH_SHORT).show();
                    }
                });



                return  true;
             default:
                 return false;
        }
    }

    class CustomAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return NUTRITION_NAMES.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.nutrition_table_list_item, null);
            TextView nutrition_name = convertView.findViewById(R.id.nutrition_table_nutrition_name);
            TextView nutrition_value = convertView.findViewById(R.id.nutrition_table_nutrition_value);

            nutrition_name.setText(NUTRITION_NAMES[position]);
            nutrition_value.setText(NUTRITION_VALUES[position]);


            return convertView;
        }
    }

}
