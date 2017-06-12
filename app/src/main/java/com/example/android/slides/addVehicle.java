package com.example.android.slides;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by athulnair on 11/06/17.
 */

public class addVehicle extends AsyncTask<String, Void, String> {

    Context context;
    private sessionManager session;
    addVehicle (Context ctx) {
        context = ctx;
    }


    @Override
    protected String doInBackground(String... params) {
        session = new sessionManager(context);
        if (!session.isFirstTimeLaunch()) {
//            goToHome();

        }
        String register_url = "http://ec2-35-167-97-234.us-west-2.compute.amazonaws.com/api/addVehicle";
            try {
                ArrayList<String> listItems = new ArrayList<String>();
                URL url = new URL(register_url);
                int dayallowance = Integer.parseInt(params[0]);
                int nightallowance = Integer.parseInt(params[1]);
                int perkmcharge = Integer.parseInt(params[2]);
                int userId = Integer.parseInt(params[3]);
                String vehicleCategory = params[4];
                String vehicleCompany = params[5];
                String vehicleModel = params[6];
                String vehicleServiceClity = params[7];
                String vehicleServiceState = params[8];

                JSONObject registerJson = new JSONObject();


                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setRequestProperty("Content-Type", "application/json");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));



                try {
                    registerJson.put("dayallowance", dayallowance);
                    registerJson.put("nightallowance", nightallowance);
                    registerJson.put("perkmcharge", perkmcharge);
                    registerJson.put("userId", userId);
                    registerJson.put("vehicleCategory", vehicleCategory);
                    registerJson.put("vehicleCompany", vehicleCompany);
                    registerJson.put("vehicleModel", vehicleModel);
                    registerJson.put("vehicleServiceClity", vehicleServiceClity);
                    registerJson.put("vehicleServiceState", vehicleServiceState);

                } catch (JSONException e) {
                    e.printStackTrace();
                }



                bw.write(registerJson.toString());
                bw.flush();
                bw.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                String result="";
                String line="";






                while ((line = bufferedReader.readLine())!= null) {

                    result += line;
                }


                try {
                    JSONObject j = new JSONObject(result);
                    line = j.toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }




                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return line;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        return null;
    }


    @Override
    protected void onPreExecute() {
        ProgressBar progressBar = (ProgressBar) ((Activity)context).findViewById(R.id.pb_add_vehicle);
        progressBar.setVisibility(ProgressBar.VISIBLE);
        super.onPreExecute();

    }

    @Override
    protected void onPostExecute(String result) {
        Toast.makeText(context, result, Toast.LENGTH_LONG).show();
        ProgressBar progressBar = (ProgressBar) ((Activity)context).findViewById(R.id.pb_add_vehicle);
        progressBar.setVisibility(ProgressBar.INVISIBLE);


    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
