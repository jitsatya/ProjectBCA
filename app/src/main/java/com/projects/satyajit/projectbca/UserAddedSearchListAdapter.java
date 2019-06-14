package com.projects.satyajit.projectbca;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.zip.Inflater;

public class UserAddedSearchListAdapter extends RecyclerView.Adapter<UserAddedSearchListAdapter.UserSearchListViewHolder>{

    private Context context;
    private List<FoodItemToFirebase> items;

    public UserAddedSearchListAdapter(Context context, List<FoodItemToFirebase> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public UserSearchListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view =  inflater.inflate(R.layout.food_item_list, viewGroup, false);
        return new UserSearchListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserSearchListViewHolder userSearchListViewHolder, final int i) {
                   userSearchListViewHolder.foodName.setText(items.get(i).getName());
                   Picasso.get().load(items.get(i).getImage()).into(userSearchListViewHolder.foodImage);
                   final FoodItemToFirebase item = new FoodItemToFirebase();
                           item.setId(items.get(i).getId());
                           item.setName(items.get(i).getName());
                           item.setImage(items.get(i).getImage());
                   userSearchListViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           Intent intent= new Intent(context,DetailedActivityForAddedData.class);
                           intent.putExtra("id", item.getId());
                           intent.putExtra("name", item.getName());
                           intent.putExtra("image",item.getImage());
                           context.startActivity(intent);
                       }
                   });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class UserSearchListViewHolder extends RecyclerView.ViewHolder{
        ImageView foodImage;
        TextView foodName;
        TextView foodGroupName;
        public UserSearchListViewHolder(@NonNull View itemView) {
            super(itemView);
            foodImage = itemView.findViewById(R.id.food_image);
            foodName = itemView.findViewById(R.id.food_name);
            foodGroupName = itemView.findViewById(R.id.food_group_name);
        }
    }
}


