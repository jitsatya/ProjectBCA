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
import android.widget.Toast;

import java.util.ArrayList;

public class FragmentCompare extends Fragment {
    DatabaseHelper myDb;
    ArrayList<String> foodlist;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_compare, container, false);
        RecyclerView compareList = view.findViewById(R.id.compare_list);
        compareList.setLayoutManager(new LinearLayoutManager(getActivity()));
        foodlist = new ArrayList<String>();
        compareList.setAdapter(new CompareListAdapter(foodlist));

        myDb = new DatabaseHelper(this.getContext());
        Cursor res = myDb.getFoodDetails();
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
