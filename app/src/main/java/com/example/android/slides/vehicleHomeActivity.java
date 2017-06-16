package com.example.android.slides;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class vehicleHomeActivity extends AppCompatActivity {

    TextView appTitle;
    TextView catchPhrase;
    TextView subHeading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_home);

        Typeface montserrat = Typeface.createFromAsset(getAssets(),  "fonts/Montserrat-Regular.otf");
        Typeface comfortaa = Typeface.createFromAsset(getAssets(),  "fonts/Comfortaa-Bold.ttf");
        appTitle = (TextView) findViewById(R.id.tv_app_title);
        appTitle.setTypeface(comfortaa);

        catchPhrase = (TextView) findViewById(R.id.tv_catch_phrase);
        Typeface gilroy = Typeface.createFromAsset(getAssets(),  "fonts/Gilroy-ExtraBold.otf");
        catchPhrase.setTypeface(gilroy);

        subHeading = (TextView) findViewById(R.id.tv_sub_heading);
        subHeading.setTypeface(montserrat);

    }

    public void register(View view)
    {
        Intent intent = new Intent(vehicleHomeActivity.this, ownerRegistrationActivity.class);
        startActivity(intent);
    }

    public void login(View view)
    {
        Intent intent = new Intent(vehicleHomeActivity.this, ownerLoginActivity.class);
        startActivity(intent);
    }
}
