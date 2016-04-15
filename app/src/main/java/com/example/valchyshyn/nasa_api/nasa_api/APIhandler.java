package com.example.valchyshyn.nasa_api.nasa_api;



import com.example.valchyshyn.nasa_api.model.NasaPicture;
import com.example.valchyshyn.nasa_api.view.MainView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class APIhandler {

    private static final String BASE_URL = "https://api.nasa.gov";
    private RestAdapter restAdapter;
    private APIInterface apiInterface;
    private MainView mainView;
    private int day;
    private int curDay;

    public APIhandler(MainView mainView1){
        mainView = mainView1;
        Calendar calendar = Calendar.getInstance();
        curDay = calendar.get(Calendar.DAY_OF_YEAR);
        day = curDay;
        restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(BASE_URL)
                .build();
        apiInterface = restAdapter.create(APIInterface.class);
    }

    public void callNasaAPIWithKey(final String key){
        mainView.showProgress();
        apiInterface.getPictureWithKey(key, new Callback<NasaPicture>() {
            @Override
            public void success(NasaPicture picture, Response response) {
                if (picture.getMediaType() != null && picture.getMediaType().equals("video")) {
                    getPicture(key, "previous");
                } else {
                    mainView.setPicture(picture);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
                mainView.hideProgress();
            }
        });
    }

    public void getPicture(String apiKey, String direction){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        switch (direction) {
            case "next":
                if (curDay != day)
                day++;
                break;
            case "previous":
                day--;
                break;
        }
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_YEAR, day);
        String yesterday = dateFormat.format(cal.getTime());
        callNasaAPIWithKeyAndDate(apiKey, yesterday);
    }

    public void callNasaAPIWithKeyAndDate(final String key, String date){
        mainView.showProgress();
        apiInterface.getPictureWithKeyAndDate(key, date, new Callback<NasaPicture>() {
            @Override
            public void success(NasaPicture picture, Response response) {
                if (picture.getMediaType() != null && picture.getMediaType().equals("video")) {
                    getPicture(key, "previous");
                } else {
                    mainView.setPicture(picture);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
                mainView.hideProgress();
            }
        });
    }
}
