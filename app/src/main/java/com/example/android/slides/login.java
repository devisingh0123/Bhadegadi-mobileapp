package com.example.android.slides;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
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
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by athulnair on 04/06/17.
 */

class login extends AsyncTask<String, Void, String> {


    AlertDialog alertDialog;
    Context context;
    String uid;
    private sessionManager session;
    login (Context ctx) {
        context = ctx;
    }

    @Override
    protected String doInBackground(String... params) {





        String type = params[0];
        String register_url = "http://ec2-35-167-97-234.us-west-2.compute.amazonaws.com/api/doLogin";
        if (type.equals("login")) {
            try {
                ArrayList<String> listItems = new ArrayList<String>();
                URL url = new URL(register_url);
                String phone = params[1];
                String password = params[2];

                JSONObject loginJson = new JSONObject();

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setConnectTimeout(15000);
                httpURLConnection.setRequestProperty("Content-Type", "application/json");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                try {
                    loginJson.put("password", password);
                    loginJson.put("username", phone);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                bw.write(loginJson.toString());
                bw.flush();
                bw.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result="";
                String line="";


                while ((line = bufferedReader.readLine())!= null) {

                    result += line;
                }


                try {
                    String x;
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
        }





        return null;
    }

    @Override
    protected void onPreExecute() {
        ProgressBar progressBar = (ProgressBar) ((Activity)context).findViewById(R.id.pb_login);
        progressBar.setVisibility(ProgressBar.VISIBLE);
        super.onPreExecute();

    }

    @Override
    protected void onPostExecute(String result) {
        String status = "";
        String Usertype = "";
        String uid = "";
        ProgressBar progressBar = (ProgressBar) ((Activity)context).findViewById(R.id.pb_login);
        progressBar.setVisibility(ProgressBar.INVISIBLE);


        try {
            if(result != null) {
                JSONObject results = new JSONObject(result);
                status = results.getString("status");
                JSONArray resultList = results.getJSONArray("resultList");
                uid = resultList.getJSONObject(0).getString("id");
                Usertype = resultList.getJSONObject(0).getString("userType");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(result == null) {
            Toast.makeText(context, "Please check your internet connection and try again.",
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, status,
                    Toast.LENGTH_LONG).show();
        }



        if (status.equals("Successfully login validated")) {
            session = new sessionManager(context);
            session.setFirstTimeLaunch(false);
            session.setUserId(uid);
            session.setUserType(Usertype);
            if(Usertype.equals("operator")) {
                Intent intent = new Intent(context, ownerHistoryActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(intent);
            }
            if(Usertype.equals("customer")) {
                Intent intent = new Intent(context, userHomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(intent);
            }
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

}
