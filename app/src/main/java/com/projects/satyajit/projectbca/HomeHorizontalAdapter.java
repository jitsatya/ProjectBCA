package com.projects.satyajit.projectbca;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class HomeHorizontalAdapter extends RecyclerView.Adapter<HomeHorizontalAdapter.HorizontalViewHolder> {

    private Context context;
    DatabaseHelper myDb;
    //private java.util.List<String> keysList = new ArrayList<>();
    private List<FoodItem> foodItems = new ArrayList<>();

    public HomeHorizontalAdapter(Context context, List<FoodItem> foodItems) {
        //this.keysList = keysList;
        this.foodItems = foodItems;
        this.context = context;
    }

    @NonNull
    @Override
    public HorizontalViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.home_items, viewGroup, false);
        return new HorizontalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HorizontalViewHolder horizontalViewHolder, int i) {

        horizontalViewHolder.itemName.setText(foodItems.get(i).getName());
        Picasso.get().load(foodItems.get(i).getImage()).into(horizontalViewHolder.itemImage);
        final String name = foodItems.get(i).getName();
        final String nbdno = foodItems.get(i).getNdbno();
        final String image = foodItems.get(i).getImage();

        horizontalViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent= new Intent(context,DetailedActivity.class);
                intent.putExtra("ndbno", nbdno);
                intent.putExtra("name", name);
                intent.putExtra("image",image);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodItems.size();
    }

    public class HorizontalViewHolder extends RecyclerView.ViewHolder {
        TextView itemName;
        ImageView itemImage;
        public HorizontalViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName =itemView.findViewById(R.id.home_item_name);
            itemImage =itemView.findViewById(R.id.home_item_image);
        }
    }
}
