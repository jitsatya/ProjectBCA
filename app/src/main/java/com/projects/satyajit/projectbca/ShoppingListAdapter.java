package com.projects.satyajit.projectbca;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ShoppingListViewHolder> {
    private String[] data;
    public ShoppingListAdapter(String[] data){
            this.data = data;
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
            String foodName = data[i];
            shoppingListViewHolder.checkItem.setText(foodName);
    }

    @Override
    public int getItemCount() {
        return data.length;
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
