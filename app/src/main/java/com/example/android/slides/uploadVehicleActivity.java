package com.example.android.slides;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
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
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

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

public class uploadVehicleActivity extends AppCompatActivity {

    // Nav bar
    sessionManager session;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle navToggle;
    private Toolbar toolbar;

    // Activity
    Button drivingLicense, vehicleImage, vehicleRc, InsuranceImage;
    int vehicleId;
    String encoded_string, image_name, artifacttype;
    Bitmap bitmap;
    File file;
    Uri file_uri;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        String s= getIntent().getExtras().getString("vehicleId");

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




    /*  Button Click Events */
    public void uploadDrivingLicense(View view) {
        artifacttype = "DL";
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        getFileUri();
        i.putExtra(MediaStore.EXTRA_OUTPUT, file_uri);
        startActivityForResult(i, 10);
    }


    /*  Button Click Events */
    public void uploadVehicleImage(View view) {
        artifacttype = "VI";
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        getFileUri();
        i.putExtra(MediaStore.EXTRA_OUTPUT, file_uri);
        startActivityForResult(i, 10);
    }


    /*  Button Click Events */
    public void uploadVehicleRC(View view) {
        artifacttype = "RC";
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        getFileUri();
        i.putExtra(MediaStore.EXTRA_OUTPUT, file_uri);
        startActivityForResult(i, 10);
    }


    /*  Button Click Events */
    public void uploadInsurance(View view) {
        artifacttype = "II";
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        getFileUri();
        i.putExtra(MediaStore.EXTRA_OUTPUT, file_uri);
        startActivityForResult(i, 10);
    }

    private void getFileUri() {
        image_name = "15UsssC.jpg";
        file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + File.separator + image_name);

        file_uri = Uri.fromFile(file);
        Toast.makeText(this, file_uri.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == 10 && resultCode == RESULT_OK) {
            new Encode_Image().execute();
        }


        super.onActivityResult(requestCode, resultCode, data);
    }


    private class Encode_Image extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... params) {

            bitmap = BitmapFactory.decodeFile(file_uri.getPath());
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);

            byte[] array = stream.toByteArray();
            encoded_string = Base64.encodeToString(array,0);

            Log.d("asdad", encoded_string);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
//            makeRequest();
        }
    }


    public void makeRequest() {
        Log.d("called", "sdadasdasd");
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, "http://10.0.2.2:8888/bhadeGadi/upload.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put("encoded_string",encoded_string);
                map.put("image_name",image_name);
                map.put("vehicleId",Integer.toString(vehicleId));

                return map;
            }
        };
        requestQueue.add(request);
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
