package com.example.android.slides;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.jar.Manifest;
import java.util.logging.Handler;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class uploadVehicleActivity extends AppCompatActivity {

    // Nav bar
    sessionManager session;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle navToggle;
    private Toolbar toolbar;

    // Activity
    Button drivingLicense, vehicleImage, vehicleRc, InsuranceImage;
    int vehicleId;
    String artifactType;


    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String s= getIntent().getExtras().getString("vehicleId");


        session = new sessionManager(this);
        vehicleId = Integer.parseInt(session.getUserId());


                /******** Uploads **********/
        drivingLicense = (Button) findViewById(R.id.drivingLicense);
        vehicleImage = (Button) findViewById(R.id.vehicleImage);
        vehicleRc = (Button) findViewById(R.id.vehicleRc);
        InsuranceImage = (Button) findViewById(R.id.insuranceImage);





        /********** Notification Bar ***********/

//        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_vehicle);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);

        drawerLayout.addDrawerListener(navToggle);
        navToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }


    private android.os.Handler handler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            String status = msg.getData().getString("what");



            Toast.makeText(uploadVehicleActivity.this, status, Toast.LENGTH_LONG).show();

            if(status.equals("Upload Successful")) {

                drivingLicense = (Button) findViewById(R.id.drivingLicense);
                vehicleImage = (Button) findViewById(R.id.vehicleImage);
                vehicleRc = (Button) findViewById(R.id.vehicleRc);
                InsuranceImage = (Button) findViewById(R.id.insuranceImage);

                if(artifactType.equals("DL")) {
                    drivingLicense.setText("Uploaded");
                }
                if(artifactType.equals("VI")) {
                    vehicleImage.setText("Uploaded");
                }
                if(artifactType.equals("RC")) {
                    vehicleRc.setText("Uploaded");
                }
                if(artifactType.equals("II")) {
                    InsuranceImage.setText("Uploaded");
                }

                if(drivingLicense.getText().equals("Uploaded") && vehicleImage.getText().equals("Uploaded") && vehicleRc.getText().equals("Uploaded") && InsuranceImage.getText().equals("Uploaded")) {

                    Intent intent = new Intent(uploadVehicleActivity.this, showVehiclesActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                }




            }


            Log.d("status Handler", status);

        }
    };



    /*  Button Click Events */
    public void uploadDrivingLicense(View view) {

        artifactType = "DL";
        new MaterialFilePicker()
                .withActivity(this)
                .withRequestCode(10)
                .start();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        if(requestCode == 10 && resultCode == RESULT_OK) {



            pd = new ProgressDialog(this);
            pd.setMessage("Please Wait");
            pd.setTitle("Uploading Document");
            pd.show();

            new Thread(new Runnable() {
                @Override
                public void run() {

                    File f = new File(data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH));
                    fileupload(f);

                }
            }).start();
        }
    }

    private void fileupload(File f) {
        vehicleId = 7;
        OkHttpClient client = new OkHttpClient();

        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("artifact", f.getName(),
                        RequestBody.create(MediaType.parse("image/jpeg"),f))
                .addFormDataPart("artifacttype", artifactType)
                .addFormDataPart("vehicleId", Integer.toString(vehicleId))
                .build();

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url("http://ec2-35-167-97-234.us-west-2.compute.amazonaws.com/api/uploadArtifact")
                .post(body)
                .build();

        try {
            okhttp3.Response response =  client.newCall(request).execute();
            Log.d("RESponse log",response.body().toString());

            String jsonData = response.body().string();
            try {


                JSONObject Jobject = new JSONObject(jsonData);

                String resultStatus = Jobject.getString("requstStatus");

                if (resultStatus.equals("OK")) {
                    Message msg = new Message();
                    Bundle b = new Bundle();
                    b.putString("what", "Upload Successful"); // for example
                    msg.setData(b);
                    handler.sendMessage(msg);
                } else {
                    Message msg = new Message();
                    Bundle b = new Bundle();
                    b.putString("what", "Upload Failed! Try Again"); // for example
                    msg.setData(b);
                    handler.sendMessage(msg);

                }
                pd.dismiss();


            } catch (JSONException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }















    /*  Button Click Events */
    public void uploadVehicleImage(View view) {
        artifactType = "VI";
        new MaterialFilePicker()
                .withActivity(this)
                .withRequestCode(10)
                .start();
    }


    /*  Button Click Events */
    public void uploadVehicleRC(View view) {
        artifactType = "RC";
        new MaterialFilePicker()
                .withActivity(this)
                .withRequestCode(10)
                .start();

    }


    /*  Button Click Events */
    public void uploadInsurance(View view) {
        artifactType = "II";
        new MaterialFilePicker()
                .withActivity(this)
                .withRequestCode(10)
                .start();

    }



    /////////// Navigation functions //////////////////

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(navToggle.onOptionsItemSelected(item)) { return true; }

        return super.onOptionsItemSelected(item);
    }


            /* Menu onClick Functions */


    public void logout(MenuItem item){
        session = new sessionManager(this);
        session.setFirstTimeLaunch(true);
        Intent intent = new Intent(this, ownerLoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


    public void vehicles(MenuItem item){
        Intent intent = new Intent(this, showVehiclesActivity.class);
        startActivity(intent);
    }

    public void history(MenuItem item){
        Intent intent = new Intent(this, ownerHistoryActivity.class);
        startActivity(intent);
    }

}
