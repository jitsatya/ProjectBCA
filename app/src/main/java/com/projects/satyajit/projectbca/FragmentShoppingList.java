package com.projects.satyajit.projectbca;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentShoppingList extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_shopping_list, container, false);
        String[] foodlist= null;
        RecyclerView shoppingList = view.findViewById(R.id.shopping_list_items_recycleview);
        shoppingList.setLayoutManager(new LinearLayoutManager(getActivity()));
        shoppingList.setAdapter(new ShoppingListAdapter(foodlist));

        return view;
    }
}
