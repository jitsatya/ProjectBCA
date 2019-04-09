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
        compareList.setAdapter(new CompareListAdapter(getActivity(),foodlist));

        myDb = new DatabaseHelper(this.getContext());
        Cursor res = myDb.getCompareFoodNbdno();
        if(res.getCount() ==0){
            Toast.makeText(this.getContext(), "Please Add items to compare!", Toast.LENGTH_SHORT).show();
        }

        while(res.moveToNext()){
            StringBuffer buffer = new StringBuffer();
            buffer.append(res.getString(0));
            foodlist.add(buffer.toString());
        }

        return view;
    }
}
