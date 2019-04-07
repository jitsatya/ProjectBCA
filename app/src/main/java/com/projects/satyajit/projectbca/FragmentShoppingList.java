package com.projects.satyajit.projectbca;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import java.util.ArrayList;

public class FragmentShoppingList extends Fragment {
    //CheckBox test;
    DatabaseHelper myDb;
    ArrayList <String> foodlist;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopping_list, container, false);
        RecyclerView shoppingList = view.findViewById(R.id.shopping_list_items_recycleview);
       // test = shoppingList.findViewById(R.id.item_checkbox);

        shoppingList.setLayoutManager(new LinearLayoutManager(getActivity()));
        foodlist = new ArrayList<String>();
        shoppingList.setAdapter(new ShoppingListAdapter(foodlist));

        myDb = new DatabaseHelper(this.getContext());
        Cursor res = myDb.getFoodName();
            if(res.getCount() ==0){
                Toast.makeText(this.getContext(), "No data available", Toast.LENGTH_SHORT);
            }

            while(res.moveToNext()){
                StringBuffer buffer = new StringBuffer();
                //buffer.append("NBDNO: "+ res.getString(0));
                buffer.append(res.getString(0));
                //buffer.append("ENERGY: "+ res.getString(2));
                //buffer.append("PROTEIN: "+ res.getString(3));
               // buffer.append("FAT: "+ res.getString(4));
                //buffer.append("CARBOHYDRATE: "+ res.getString(5));
                foodlist.add(buffer.toString());
            }

            //Show all data

            //Toast.makeText(this.getContext(),text,Toast.LENGTH_LONG);
        return view;

    }


}
