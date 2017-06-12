package com.example.android.slides;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class getStartedActivity extends AppCompatActivity {


    Button vehicleOwner;
    Button getRide;
    TextView appTitle;
    private sessionManager session;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);


        session = new sessionManager(this);
        if (!session.isFirstTimeLaunch()) {
            if(session.getUSER_Type().equals("operator")) {
                goToOperatorHome();
                finish();
            }
            if(session.getUSER_Type().equals("customer")) {
                goToUserHome();
                finish();
            }
        }

        Typeface montserrat = Typeface.createFromAsset(getAssets(),  "fonts/Montserrat-Light.otf");
        appTitle = (TextView) findViewById(R.id.tv_app_title);
        appTitle.setTypeface(montserrat);
        Typeface gilroy = Typeface.createFromAsset(getAssets(),  "fonts/Gilroy-ExtraBold.otf");
        vehicleOwner = (Button) findViewById(R.id.btn_vehicle);
        getRide = (Button) findViewById(R.id.btn_getRide);
        vehicleOwner.setTypeface(gilroy);
        getRide.setTypeface(gilroy);
    }



    public void vehicleHome(View view)
    {
        Intent intent = new Intent(getStartedActivity.this, vehicleHomeActivity.class);
        startActivity(intent);
    }

    public void goToOperatorHome() {
        Intent intent = new Intent(getStartedActivity.this, ownerHistoryActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void goToUserHome() {
        Intent intent = new Intent(getStartedActivity.this, userHomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

}
