package in.shreesaiconsultancy.android.obhadegadi;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

        //Home Page Redirect
        session = new sessionManager(this);
        if(session.isFirstTimeLaunch()) {
            Intent intent = new Intent(getStartedActivity.this, vehicleHomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        //Landing Page
        if (!session.isFirstTimeLaunch()) {
        	Intent intent = new Intent(getStartedActivity.this, HomePageActivity.class);
        	intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        	startActivity(intent);
            finish();
        }

        Typeface montserrat = Typeface.createFromAsset(getAssets(),  "fonts/Montserrat-Light.otf");
        Typeface comfortaa = Typeface.createFromAsset(getAssets(),  "fonts/Comfortaa-Regular.ttf");
        appTitle = (TextView) findViewById(R.id.tv_app_title);
        appTitle.setTypeface(comfortaa);
        Typeface gilroy = Typeface.createFromAsset(getAssets(),  "fonts/Gilroy-ExtraBold.otf");
        vehicleOwner = (Button) findViewById(R.id.btn_vehicle);
        getRide = (Button) findViewById(R.id.btn_getRide);
        vehicleOwner.setTypeface(gilroy);
        getRide.setTypeface(gilroy);
    }


}
