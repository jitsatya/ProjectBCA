package com.projects.satyajit.projectbca;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.ReferenceQueue;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class search_activity extends AppCompatActivity {
    private EditText searchField;
    private TextView displayResults;
    private ImageButton searchButton;
    private String searchKey;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_activity);
        recyclerView =(RecyclerView)findViewById(R.id.search_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchField = (EditText) findViewById(R.id.searchField);
        searchButton = (ImageButton) findViewById(R.id.searchButton);


    }

    public void getResult(View view) {
        searchKey = searchField.getText().toString();
        getData();

    }
    private void getData() {
        Call<SearchResult> list = UsdaApi.getService().getFoodList(searchKey);
        list.enqueue(new Callback<SearchResult>() {
            @Override
            public void onResponse(Call<SearchResult> call, Response<SearchResult> response) {
                SearchResult resultList = response.body();
                List list = new List();
                list = resultList.getList();
                recyclerView.setAdapter(new SearchListAdapter(search_activity.this, list.getItem()));
                //Toast.makeText(search_activity.this, "success!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<SearchResult> call, Throwable t) {
                Toast.makeText(search_activity.this, "Error Occurred!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}

