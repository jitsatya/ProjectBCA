package com.projects.satyajit.projectbca;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ShoppingListViewHolder> {
    DatabaseHelper myDb;
    //private ArrayList<String> data;
    private java.util.List<String> keysList = new ArrayList<>();
    private List<FoodItem> foodItems = new ArrayList<>();



    Context context;

    public ShoppingListAdapter(Context context, List<String> data, List<FoodItem> foodItems){
            this.keysList = data;
            this.foodItems = foodItems;
            this.context = context;
    }
    @NonNull
    @Override
    public ShoppingListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.shopping_list_item, viewGroup,false);
        ShoppingListViewHolder vh = new ShoppingListViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingListViewHolder shoppingListViewHolder, int i) {
        final String nbdno = keysList.get(i);
        /*shoppingListViewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foodItems.remove(i);
                keysList.remove(i);
                notifyItemRemoved(i);
                notifyItemRangeChanged(i,keysList.size());
                notifyItemChanged(i, foodItems);
            }
        });*/

        shoppingListViewHolder.checkItem.setText(foodItems.get(i).getName());
        shoppingListViewHolder.qantityEditText.setText("100");


    }

    @Override
    public int getItemCount() {
        return keysList.size();
    }

    public class ShoppingListViewHolder extends RecyclerView.ViewHolder{
        CheckBox checkItem;
        EditText qantityEditText;
        //ImageButton delete;

        public ShoppingListViewHolder(@NonNull View itemView) {
            super(itemView);

            checkItem = itemView.findViewById(R.id.item_checkbox);
            qantityEditText = itemView.findViewById(R.id.item_edittext);
            //delete = itemView.findViewById(R.id.delete_item);

        }
    }

}

