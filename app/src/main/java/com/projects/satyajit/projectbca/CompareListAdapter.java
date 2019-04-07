package com.projects.satyajit.projectbca;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class CompareListAdapter extends RecyclerView.Adapter<CompareListAdapter.CompareListViewHolder> {
    private ArrayList<String> data;

    public CompareListAdapter( ArrayList<String> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public CompareListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.compare_list_item, viewGroup, false);
        return new CompareListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CompareListViewHolder compareListViewHolder, int i) {
        String item = data.get(i);

        compareListViewHolder.item.setText(item);
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
