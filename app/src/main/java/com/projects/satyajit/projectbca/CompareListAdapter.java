package com.projects.satyajit.projectbca;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CompareListAdapter extends RecyclerView.Adapter<CompareListAdapter.CompareListViewHolder> {

    Context context;
    DatabaseHelper myDb;
    //private ArrayList<String> data;
    private java.util.List<String> keysList = new ArrayList<>();
    private List<FoodItem> foodItems = new ArrayList<>();

    public CompareListAdapter(Context context, List<String> keysList, List<FoodItem> foodItems) {
        this.context = context;
        this.keysList = keysList;
        this.foodItems = foodItems;
    }

    @NonNull
    @Override
    public CompareListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.compare_list_item, viewGroup, false);
        return new CompareListViewHolder(view);
    }

    //Binding Data to the viewHolder
    @Override
    public void onBindViewHolder(@NonNull final CompareListViewHolder compareListViewHolder, int i) {
        //final String nbdno = keysList.get(i);
        compareListViewHolder.item.setText(foodItems.get(i).getName());
        List<Nutrient> nutrient = foodItems.get(i).getNutrients();
        for (int a = 0; a<nutrient.size(); a++ )
        {
            int id = Integer.parseInt(nutrient.get(a).getNutrientId());
            switch (id){
                case 203:
                    compareListViewHolder.protein.setText(nutrient.get(a).getValue());
                    break;
                case 204:
                    compareListViewHolder.fat.setText(nutrient.get(a).getValue());
                    break;
                case 205:
                    compareListViewHolder.carbs.setText(nutrient.get(a).getValue());
                case 208:
                    compareListViewHolder.calories.setText(nutrient.get(a).getValue());

                    break;
            }
        }



    }

    @Override
    public int getItemCount() {
        return keysList.size();
    }

    public class CompareListViewHolder extends RecyclerView.ViewHolder{
        TextView item, protein, fat, carbs, calories;
        public CompareListViewHolder(@NonNull View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.compare_item_name);
            protein = itemView.findViewById(R.id.compare_protein_value);
            fat = itemView.findViewById(R.id.compare_fat_value);
            carbs = itemView.findViewById(R.id.compare_carbs_value);
            calories = itemView.findViewById(R.id.compare_calories_value);

        }
    }
}
