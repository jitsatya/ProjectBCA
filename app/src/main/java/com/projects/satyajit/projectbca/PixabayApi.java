package com.projects.satyajit.projectbca;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class PixabayApi {
    private static final String key = "12140260-b0d1ddc4ab3b70a8f7bcf1d02";
    private static final String url = "https://pixabay.com/api/";

    public static PixabayApi.PostImageService postImageService = null;
    public static PixabayApi.PostImageService getService(){
        if(postImageService ==null){
            Retrofit retrofit1 = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            postImageService = retrofit1.create(PixabayApi.PostImageService.class);
        }
        return postImageService;
    }


    public interface PostImageService {
        @GET("?per_page=50&image_type=photo&category=food&order=popular&key=" + key )
        Call<FoodImage> getFoodImageList(@Query("q") String q);
    }
}
