package com.projects.satyajit.projectbca;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class UsdaApi {
    private static final String key = "CNnMlek3Wy8jYTBGSnp4OxXDb6xQJGbqShmbQ5W1";
    private static final String url = "https://api.nal.usda.gov/ndb/";

    public static PostService postService = null;
    public static PostService getService(){
        if(postService ==null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            postService = retrofit.create(PostService.class);
        }
        return postService;
    }


    public interface PostService {
        @GET ("search/?ds=Standard%20Reference&max=25&api_key=" + key )
        Call<SearchResult> getFoodList(@Query("q") String q);
        @GET("reports/?api_key=" + key)
        Call<FoodReport> getFoodReport(@Query("ndbno") String ndbno);

    }
}
