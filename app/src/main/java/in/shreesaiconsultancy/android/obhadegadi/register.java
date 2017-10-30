package in.shreesaiconsultancy.android.obhadegadi;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.TextUtils;
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
import java.util.ArrayList;

/**
 * Created by athulnair on 03/06/17.
 */

class register extends AsyncTask<String, Void, String> {

    Context context;
    AlertDialog alertDialog;
    private sessionManager session;
    register (Context ctx) {
        context = ctx;
    }


    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        String register_url = "http://ec2-35-167-97-234.us-west-2.compute.amazonaws.com:8080/api/registerUser";
        if (type.equals("register")) {
            try {
                ArrayList<String> listItems = new ArrayList<String>();
                URL url = new URL(register_url);
                String name = params[1];
                String email = params[2];
                String phone = params[3];
                String password = params[4];
                String userType = params[5];
                String pincode = params[7];
                String cname = params[6];

                JSONObject registerJson = new JSONObject();


                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setRequestProperty("Content-Type", "application/json");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                try {

                    if(!cname.equals("121"))
                    {
                        registerJson.put("companyName", cname);
                    }

                    registerJson.put("username", name);

                    if(!(email.length() == 0)) {
                        registerJson.put("email", email);
                    }
                    registerJson.put("phone", phone);
                    registerJson.put("pincode", pincode);
                    registerJson.put("password", password);
                    registerJson.put("userType", userType);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                bw.write(registerJson.toString());
                bw.flush();
                bw.close();
                outputStream.close();

                int code = httpURLConnection.getResponseCode();
                Log.d("Response", Integer.toString(code));

                if(code != 200) {
                    JSONObject alreadyUsed = new JSONObject();
                    try {
                        alreadyUsed.put("requstStatus", "");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.d("json", alreadyUsed.toString());

                    return alreadyUsed.toString();
                }

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
                    Log.d("asdada", line);
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
        ProgressBar progressBar = (ProgressBar) ((Activity)context).findViewById(R.id.pb_register);
        progressBar.setVisibility(ProgressBar.VISIBLE);
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String result) {
        String Usertype = "";
        String uid = "";
        String status = "";

        ProgressBar progressBar = (ProgressBar) ((Activity)context).findViewById(R.id.pb_register);
        progressBar.setVisibility(ProgressBar.INVISIBLE);

        try {
            if(result != null) {
                JSONObject results = new JSONObject(result);
                Log.d("result", results.toString());

                if (results.toString().equals("{\"requstStatus\":\"\"}")) {
                    Toast.makeText(context, "Already user exist with phone or email, please try another",
                            Toast.LENGTH_LONG).show();
                }

                status = results.getString("requstStatus");
                Log.d("result2", status);

                JSONArray resultList = results.getJSONArray("resultList");
                uid = resultList.getJSONObject(0).getString("id");
                Usertype = resultList.getJSONObject(0).getString("userType");

                if (!TextUtils.isEmpty(status)){
                    Toast.makeText(context, "Successfully Registered",
                            Toast.LENGTH_LONG).show();
                    session = new sessionManager(context);
                    session.setFirstTimeLaunch(false);
                    session.setUserId(uid);
                    session.setUserType(Usertype);
                    if(Usertype.equals("operator")) {
                        Intent intent = new Intent(context, HomePageActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        context.startActivity(intent);
                    }
                    if(Usertype.equals("customer")) {
                        Intent intent = new Intent(context, HomePageActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        context.startActivity(intent);
                    }
                } else {
                    Toast.makeText(context, "Already user exist with phone or email, please try another",
                            Toast.LENGTH_LONG).show();
                }

            } else {
                Toast.makeText(context, "Please check your internet connection and try again.",
                        Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}

