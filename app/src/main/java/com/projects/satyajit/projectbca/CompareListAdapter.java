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

public class CompareListAdapter extends RecyclerView.Adapter<CompareListAdapter.CompareListViewHolder> {

    Context context;
    DatabaseHelper myDb;
    private ArrayList<String> data;

    public CompareListAdapter( Context context, ArrayList<String> data) {

        this.data = data;
        this.context = context;
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
        final String nbdno = data.get(i);

        myDb = new DatabaseHelper(context);
        String[] column = new String[5];
        column[0]= "NAME";
        column[1]= "ENERGY";
        column[2]= "PROTEIN";
        column[3]= "FAT";
        column[4]= "CARBOHYDRATE";
        for(int n= 0;n<column.length; n++){
        switch (column[n]){
            case "NAME":
                Cursor res1 = myDb.getCompareFoodData("NAME", nbdno);
                if(res1.getCount() ==0){
                    Toast.makeText(context, "No data available", Toast.LENGTH_SHORT);
                }

                while(res1.moveToNext()){
                    final StringBuffer buffer = new StringBuffer();
                    buffer.append(res1.getString(0));
                    compareListViewHolder.item.setText(buffer.toString());
                }
                break;
            case "ENERGY":
                Cursor res2 = myDb.getCompareFoodData( "ENERGY", nbdno);
                if(res2.getCount() ==0){
                    Toast.makeText(context, "No data available", Toast.LENGTH_SHORT);
                }

                while(res2.moveToNext()){
                    StringBuffer buffer = new StringBuffer();
                    buffer.append(res2.getString(0));
                    compareListViewHolder.calories.setText(buffer.toString());
                }
                break;
            case "PROTEIN":
                Cursor res3 = myDb.getCompareFoodData( "PROTEIN",nbdno);
                if(res3.getCount() ==0){
                    Toast.makeText(context, "No data available", Toast.LENGTH_SHORT);
                }

                while(res3.moveToNext()){
                    StringBuffer buffer = new StringBuffer();
                    buffer.append(res3.getString(0));
                    compareListViewHolder.protein.setText(buffer.toString());
                }
                break;
            case "FAT":
                Cursor res4 = myDb.getCompareFoodData("FAT",nbdno);
                if(res4.getCount() ==0){
                    Toast.makeText(context, "No data available", Toast.LENGTH_SHORT);
                }

                while(res4.moveToNext()){
                    StringBuffer buffer = new StringBuffer();
                    buffer.append(res4.getString(0));
                    compareListViewHolder.fat.setText(buffer.toString());
                }
                break;
            case "CARBOHYDRATE":
                Cursor res5 = myDb.getCompareFoodData("CARBOHYDRATE",nbdno);
                if(res5.getCount() ==0){
                    Toast.makeText(context, "No data available", Toast.LENGTH_SHORT);
                }

                while(res5.moveToNext()){
                    StringBuffer buffer = new StringBuffer();
                    buffer.append(res5.getString(0));
                    compareListViewHolder.carbs.setText(buffer.toString());
                }
                break;
        }
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
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
