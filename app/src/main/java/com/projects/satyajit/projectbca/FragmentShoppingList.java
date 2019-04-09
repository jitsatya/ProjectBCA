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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;

public class FragmentShoppingList extends Fragment {
    TextView tCalories, tProtein, tCarbs, tFat;
    float totalCalories, totalProtein, totalCarbs, totalFat;
    DatabaseHelper myDb;
    ArrayList <String> nbdnoList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopping_list, container, false);
        RecyclerView shoppingList = view.findViewById(R.id.shopping_list_items_recycleview);
        shoppingList.setLayoutManager(new LinearLayoutManager(getActivity()));

        tCalories = view.findViewById(R.id.total_calories);
        tProtein = view.findViewById(R.id.total_protein);
        tCarbs = view.findViewById(R.id.total_carbs);
        tFat = view.findViewById(R.id.total_fat);

        nbdnoList = new ArrayList<String>();
        shoppingList.setAdapter(new ShoppingListAdapter(shoppingList.getContext(),nbdnoList));

        myDb = new DatabaseHelper(this.getContext());
        Cursor res = myDb.getShoppingListFoodNbdno();
            if(res.getCount() ==0){
                Toast.makeText(this.getContext(), "There are no items in the Shopping List", Toast.LENGTH_SHORT).show();
            }

            while(res.moveToNext()){
                StringBuffer buffer = new StringBuffer();
                buffer.append(res.getString(0));
                nbdnoList.add(buffer.toString());
            }
            TotalCalculation();
            tCalories.setText(String.valueOf(totalCalories) );
            tProtein.setText(String.valueOf(totalProtein) );
            tCarbs.setText(String.valueOf(totalCarbs));
            tFat.setText(String.valueOf(totalFat));

        return view;

    }

    public void TotalCalculation()
    {
        String[] column = new String[5];
        column[0]= "NAME";
        column[1]= "ENERGY";
        column[2]= "PROTEIN";
        column[3]= "FAT";
        column[4]= "CARBOHYDRATE";
        Iterator i = nbdnoList.iterator();
        while(i.hasNext()) {
            String nbdno = i.next().toString();
            for (int n = 0; n < column.length; n++) {
                switch (column[n]) {
                    case "NAME":
                        Cursor res1 = myDb.getShoppingListFoodData("NAME", nbdno);
                        if (res1.getCount() == 0) {
                            Toast.makeText(this.getContext(), "No data available", Toast.LENGTH_SHORT);
                        }

                        while (res1.moveToNext()) {
                            StringBuffer buffer = new StringBuffer();
                            buffer.append(res1.getString(0));

                        }
                        break;
                    case "ENERGY":
                        Cursor res2 = myDb.getShoppingListFoodData("ENERGY", nbdno);
                        if (res2.getCount() == 0) {
                            Toast.makeText(this.getContext(), "No data available", Toast.LENGTH_SHORT);
                        }

                        while (res2.moveToNext()) {
                            StringBuffer buffer = new StringBuffer();
                            buffer.append(res2.getString(0));
                            totalCalories = totalCalories + Float.parseFloat( buffer.toString());
                        }
                        break;
                    case "PROTEIN":
                        Cursor res3 = myDb.getShoppingListFoodData("PROTEIN", nbdno);
                        if (res3.getCount() == 0) {
                            Toast.makeText(this.getContext(), "No data available", Toast.LENGTH_SHORT);
                        }

                        while (res3.moveToNext()) {
                            StringBuffer buffer = new StringBuffer();
                            buffer.append(res3.getString(0));
                            totalProtein = totalProtein + Float.parseFloat( buffer.toString());
                        }
                        break;
                    case "FAT":
                        Cursor res4 = myDb.getShoppingListFoodData("FAT", nbdno);
                        if (res4.getCount() == 0) {
                            Toast.makeText(this.getContext(), "No data available", Toast.LENGTH_SHORT);
                        }

                        while (res4.moveToNext()) {
                            StringBuffer buffer = new StringBuffer();
                            buffer.append(res4.getString(0));
                            totalFat = totalFat + Float.parseFloat( buffer.toString());
                        }
                        break;
                    case "CARBOHYDRATE":
                        Cursor res5 = myDb.getShoppingListFoodData("CARBOHYDRATE", nbdno);
                        if (res5.getCount() == 0) {
                            Toast.makeText(this.getContext(), "No data available", Toast.LENGTH_SHORT);
                        }

                        while (res5.moveToNext()) {
                            StringBuffer buffer = new StringBuffer();
                            buffer.append(res5.getString(0));
                            totalCarbs = totalCarbs + Float.parseFloat( buffer.toString());
                        }
                        break;
                }
            }
        }
    }


}
