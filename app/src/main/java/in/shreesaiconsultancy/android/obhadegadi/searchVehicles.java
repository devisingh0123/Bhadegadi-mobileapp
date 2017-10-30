package in.shreesaiconsultancy.android.obhadegadi;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
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
 * Created by athulnair on 16/07/17.
 */

public class searchVehicles extends AsyncTask<String, Void, String> {

    Context context;
    private sessionManager session;
    String vehicleID;
    JSONObject resultList;
    searchVehicles (Context ctx) {
        context = ctx;
    }

    @Override
    protected String doInBackground(String... params) {

        String register_url = "http://ec2-35-167-97-234.us-west-2.compute.amazonaws.com:8080/api/getVehicleListAsPerFilters";
        try {
            Log.d("calles", "asdasdasd");

            ArrayList<String> listItems = new ArrayList<String>();
            URL url = new URL(register_url);
            String from = params[0];
            String to = params[1];
            String vehicleCategory = params[2];
            String vehicleCompany = params[3];
            String vehicleModel = params[4];
            String vehicleServiceClity = params[5];
            String vehicleServiceState = params[6];

            JSONObject searchJSON = new JSONObject();

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

            try {
                searchJSON.put("fromDate", from);
                searchJSON.put("taxiCategory", vehicleCategory);
                searchJSON.put("taxiCompany", vehicleCompany);
                searchJSON.put("toDate", to);
                searchJSON.put("workingCity", vehicleServiceClity);
                searchJSON.put("workingState", vehicleServiceState);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            bw.write(searchJSON.toString());
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
                line = j.getString("status");
                resultList = j;
                Log.d("one", resultList.toString());
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
        ProgressBar progressBar = (ProgressBar) ((Activity)context).findViewById(R.id.pb_add_vehicle);
        progressBar.setVisibility(ProgressBar.INVISIBLE);
        if(result == null) {
            Toast.makeText(context, "Please check your internet connection and try again.",
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, result, Toast.LENGTH_LONG).show();
            if(result.equals("Vehicle list is generated successfully.")){
                Intent i = new Intent(context, searchResultActivity.class).putExtra("resultList", resultList.toString());
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(i);
            }
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
