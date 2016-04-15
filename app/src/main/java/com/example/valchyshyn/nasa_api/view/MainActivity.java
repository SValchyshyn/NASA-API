package com.example.valchyshyn.nasa_api.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.valchyshyn.nasa_api.R;
import com.example.valchyshyn.nasa_api.model.NasaPicture;
import com.example.valchyshyn.nasa_api.nasa_api.APIhandler;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity implements MainView {


    private static String apiKey = "EkUKBIvvXlLzDsqORPbScZAfX23lK2I5Pul2ZVes";
    private NasaPicture currentPicture;
    private APIhandler apiHandler = new APIhandler(this);


    @InjectView(R.id.imageView)
    ImageView imageView;

    @InjectView(R.id.icon_download)
    ImageView downloadView;

    @InjectView(R.id.progressbar)
    ProgressBar progressBar;

    @InjectView(R.id.picture_title)
    TextView titleView;

    @InjectView(R.id.icon_description)
    ImageView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        try{
            apiHandler.callNasaAPIWithKey(apiKey);
        }
        catch (Exception e){
            e.printStackTrace();
            try {
                apiHandler.callNasaAPIWithKey(apiKey);
            }
            catch (Exception f) {
                f.printStackTrace();
            }
        }
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(ProgressBar.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(ProgressBar.INVISIBLE);
    }

    @Override
    public void setPicture(NasaPicture picture) {
        currentPicture = picture;
        updateImage();
        updateTitle();
        setListeners();
    }

    private void setListeners(){
        downloadView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setImage();
            }
        });

        description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), Description.class);
                myIntent.putExtra("pic_description", currentPicture.getExplanation());
                startActivityForResult(myIntent, 0);
            }
        });
        imageView.setOnTouchListener(new OnSwipeTouchListener(this) {
            public void onSwipeLeft() {
                try {
                    apiHandler.getPicture(apiKey, "next");
                } catch (Exception e) {
                    e.printStackTrace();
                    try {
                        apiHandler.getPicture(apiKey, "next");
                    } catch (Exception f) {
                        f.printStackTrace();
                    }
                }
            }

            public void onSwipeRight() {

                try {
                    apiHandler.getPicture(apiKey, "previous");
                } catch (Exception e) {
                    e.printStackTrace();
                    try {
                        apiHandler.getPicture(apiKey, "previous");
                    } catch (Exception f) {
                        f.printStackTrace();
                    }
                }
            }

            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });
    }

      private void updateImage(){
        Picasso.with(this)
                .load(currentPicture.getUrl())
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        hideProgress();
                    }
                    @Override
                    public void onError() {
                        hideProgress();
                    }
                });
    }


    private void setImage(){
        Picasso.with(this)
                .load(currentPicture.getUrl())
                .into(new Target(){

                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        try{
                            setWallpaper(bitmap);
                            Toast.makeText( getBaseContext(),"Wallpaper is set",Toast.LENGTH_SHORT).show();
                        }
                        catch(IOException ex) {
                            ex.printStackTrace();
                        }
                    }

                    @Override
                    public void onBitmapFailed(final Drawable errorDrawable) {
                        Log.d("TAG", "FAILED");
                    }

                    @Override
                    public void onPrepareLoad(final Drawable placeHolderDrawable) {
                        Log.d("TAG", "Prepare Load");
                    }
                });
    }

    private void updateTitle(){
        titleView.setText(currentPicture.getTitle());
    }
}
