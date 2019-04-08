package com.projects.satyajit.projectbca;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ShoppingListViewHolder> {
    DatabaseHelper myDb;
    private ArrayList<String> data;
    Context context;
    public ShoppingListAdapter(Context context, ArrayList<String> data){
            this.data = data;
            this.context = context;
    }
    @NonNull
    @Override
    public ShoppingListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.shopping_list_item, viewGroup,false);
        return new ShoppingListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingListViewHolder shoppingListViewHolder, int i) {
        String nbdno = data.get(i);
        myDb = new DatabaseHelper(context);
        Cursor res1 = myDb.getShoppingListFoodData("NAME", nbdno);
        if(res1.getCount() ==0){
            Toast.makeText(context, "No data available", Toast.LENGTH_SHORT);
        }

        while(res1.moveToNext()){
            StringBuffer buffer = new StringBuffer();
            buffer.append(res1.getString(0));
            shoppingListViewHolder.checkItem.setText(buffer.toString());
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ShoppingListViewHolder extends RecyclerView.ViewHolder{
        CheckBox checkItem;
        EditText qantityEditText;
        public ShoppingListViewHolder(@NonNull View itemView) {
            super(itemView);
            checkItem = itemView.findViewById(R.id.item_checkbox);
            qantityEditText = itemView.findViewById(R.id.item_edittext);
        }
    }
}
