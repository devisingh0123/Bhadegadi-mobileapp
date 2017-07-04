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

        isStoragePermissionGranted();





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
        Typeface comfortaa = Typeface.createFromAsset(getAssets(),  "fonts/Comfortaa-Regular.ttf");
        appTitle = (TextView) findViewById(R.id.tv_app_title);
        appTitle.setTypeface(comfortaa);
        Typeface gilroy = Typeface.createFromAsset(getAssets(),  "fonts/Gilroy-ExtraBold.otf");
        vehicleOwner = (Button) findViewById(R.id.btn_vehicle);
        getRide = (Button) findViewById(R.id.btn_getRide);
        vehicleOwner.setTypeface(gilroy);
        getRide.setTypeface(gilroy);
    }


    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.v("permission","Permission is granted");
                return true;
            } else {

                Log.v("permission","Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("permission","Permission is granted");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        try {
            Log.d("Request Code", Integer.toString(requestCode));
            Log.d("grant Code", (grantResults.toString()));
            switch (requestCode) {
                case 1: {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        Log.v("permission", "Permission: " + permissions[0] + "was " + grantResults[0]);
                        //resume tasks needing this permission
                    } else {
                        isStoragePermissionGranted();
                    }
                }
            }
        } catch (Exception e) {
            Log.d("error","erroe");
        }
    }


    public void vehicleHome(View view)
    {
        Intent intent = new Intent(getStartedActivity.this, vehicleHomeActivity.class);
        startActivity(intent);
    }

    public void getRide(View view)
    {
        Intent intent = new Intent(getStartedActivity.this, customerLoginActivity.class);
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
