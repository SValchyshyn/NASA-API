package com.example.valchyshyn.nasa_api.view;


import com.example.valchyshyn.nasa_api.model.NasaPicture;

public interface MainView {

    void showProgress();

    void hideProgress();

    void setPicture(NasaPicture picture);

}
