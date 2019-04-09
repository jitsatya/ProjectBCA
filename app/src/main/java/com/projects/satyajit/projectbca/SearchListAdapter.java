package com.projects.satyajit.projectbca;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;


public class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.SearchListViewHolder> {
    private Context context;
    private List<Item> items;
    private List<Hit> images;


    public SearchListAdapter(Context context, List<Item> items, List<Hit> images) {
        this.context = context;
        this.items = items;
        this.images=images;
    }

    @NonNull
    @Override
    //This function is called the moment recycler view loads, It creates all the views
    public SearchListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.food_item_list,viewGroup, false );
        return new SearchListViewHolder(view);
    }
    //This method set the data to the viewHolder. Data binding happens here.
    @Override
    public void onBindViewHolder(@NonNull SearchListViewHolder searchListViewHolder, int position) {
            final Item item = items.get(position);
            final Hit image = images.get(position);
            searchListViewHolder.foodName.setText(item.getName());
        Glide.with(context).load(image.getPreviewURL()).into(searchListViewHolder.foodImage);
            //searchListViewHolder.foodImage.setImageResource(R.drawable.placeholder_image);
            searchListViewHolder.foodGroupName.setText(item.getGroup());
            searchListViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent= new Intent(context,DetailedActivity.class);
                    intent.putExtra("ndbno", item.getNdbno());
                    intent.putExtra("name", item.getName());
                    intent.putExtra("image",image.getWebformatURL());
                    context.startActivity(intent);
                }
            });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class SearchListViewHolder extends RecyclerView.ViewHolder{
        ImageView foodImage;
        TextView foodName;
        TextView foodGroupName;

        public SearchListViewHolder(@NonNull View itemView) {
            super(itemView);
            foodImage = itemView.findViewById(R.id.food_image);
            foodName = itemView.findViewById(R.id.food_name);
            foodGroupName = itemView.findViewById(R.id.food_group_name);

        }
    }
}
