package com.example.valchyshyn.nasa_api.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.valchyshyn.nasa_api.R;


public class Description extends Activity {


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.description);

        TextView description = (TextView) findViewById(R.id.Description);
        Button previous = (Button) findViewById(R.id.Button02);
        previous.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            description.setText(extras.getString("pic_description"));
        }
    }
}
