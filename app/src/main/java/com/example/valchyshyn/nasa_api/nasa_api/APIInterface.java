package com.example.valchyshyn.nasa_api.nasa_api;



import com.example.valchyshyn.nasa_api.model.NasaPicture;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;


public interface APIInterface {
    @GET("/planetary/apod")
    void getPictureWithKey(@Query("api_key") String apiKey, Callback<NasaPicture> cb);

    @GET("/planetary/apod")
    void getPictureWithKeyAndDate(@Query("api_key") String apiKey, @Query("date") String date,
                                  Callback<NasaPicture> cb);
}
