package in.shreesaiconsultancy.android.obhadegadi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class vehicleDetailsActivity extends AppCompatActivity {

    ViewPager viewPager;
    sessionManager session;
    private ImageView imageView;
    private TextView tv_vehicle_name, tv_vehicle_type, tv_vehicle_pkc, tv_state, tv_city;
    private TextView tv_vehicle_name_value, tv_vehicle_type_value, tv_vehicle_pkc_value, tv_state_value, tv_city_value;

    private String vehicle_name, vehicle_type, vehicle_pkc, state, city, url, vehicle_id, user_id;
    private LinearLayout linearLayout3;
    private LinearLayout linearLayout2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_details);

        session = new sessionManager(this);
        user_id = session.getUserId();

        viewPager = (ViewPager) findViewById(R.id.view_pager);

        linearLayout2 = (LinearLayout) findViewById(R.id.two_img);
        linearLayout3 = (LinearLayout) findViewById(R.id.three_img);

        Typeface Montserrat = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Light.otf");

        tv_vehicle_name = (TextView) findViewById(R.id.tv_vehicle_name);
        tv_vehicle_name.setTypeface(Montserrat);
        tv_vehicle_name_value = (TextView) findViewById(R.id.tv_vehicle_name_value);
        tv_vehicle_name_value.setTypeface(Montserrat);

        tv_vehicle_type = (TextView) findViewById(R.id.tv_vehicle_type);
        tv_vehicle_type.setTypeface(Montserrat);
        tv_vehicle_type_value = (TextView) findViewById(R.id.tv_vehicle_type_value);
        tv_vehicle_type_value.setTypeface(Montserrat);

        tv_vehicle_pkc = (TextView) findViewById(R.id.tv_vehicle_pkc);
        tv_vehicle_pkc.setTypeface(Montserrat);
        tv_vehicle_pkc_value = (TextView) findViewById(R.id.tv_vehicle_pkc_value);
        tv_vehicle_pkc_value.setTypeface(Montserrat);

        tv_state = (TextView) findViewById(R.id.tv_vehicle_state);
        tv_state.setTypeface(Montserrat);
        tv_state_value = (TextView) findViewById(R.id.tv_vehicle_state_value);
        tv_state_value.setTypeface(Montserrat);

        tv_city = (TextView) findViewById(R.id.tv_vehicle_city);
        tv_city.setTypeface(Montserrat);
        tv_city_value = (TextView) findViewById(R.id.tv_vehicle_city_value);
        tv_city_value.setTypeface(Montserrat);

        Bundle extras = getIntent().getExtras();
        vehicle_name = extras.getString("vehicle_name");
        vehicle_type = extras.getString("vehicle_type");
        vehicle_pkc = extras.getString("vehicle_pkc");
        state = extras.getString("vehicle_state");
        city = extras.getString("vehicle_city");
        url = extras.getString("image_url");
        vehicle_id = extras.getString("vehicle_id");

        tv_vehicle_name_value.setText(vehicle_name);
        tv_vehicle_type_value.setText(vehicle_type);
        tv_vehicle_pkc_value.setText(vehicle_pkc + " ₹ Per Km");
        tv_state_value.setText(state);
        tv_city_value.setText(city);

        imageView = (ImageView) findViewById(R.id.imageView);

        if(url.equals("true")){
            imageView.setImageResource(R.drawable.no_car);
        } else {
            Picasso.with(this)
                    .load(url)
                    .into(imageView);
        }

        getImages();

    }





    private void getImages() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Please Wait...");
        pd.show();

        String url = "http://ec2-35-167-97-234.us-west-2.compute.amazonaws.com:8080/api/" + vehicle_id + "/getUserVehicleImageList";


        StringRequest stringReq = new StringRequest(Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pd.dismiss();
                        try {
                            JSONObject result = new JSONObject(response);

                            if(result.getString("resultList").equals("null"))
                            {
                                Log.d("noimage","no image");
                            } else {
                                JSONArray arr = result.getJSONArray("resultList");
                                String [] img = {"0","0","0"};

                                for(int i=0; i < arr.length(); i++) {
                                    JSONObject o = arr.getJSONObject(i);

                                    String url = o.getString("vehicleImageUrl");
                                    String imageName = url.substring(url.lastIndexOf( "/" )+1, url.length()) ;
                                    String img_url = "http://ec2-35-167-97-234.us-west-2.compute.amazonaws.com:8080/image/" + user_id + "/" + vehicle_id + "/" + imageName;
                                    img[i] = img_url;

                                }

                                int count = 0;
                                for (int i=0;i < 3;i++) {
                                    if(!img[i].equals("0")) {
                                        count++;
                                    }
                                }


                                if(count == 3) {
                                    linearLayout3.setVisibility(View.VISIBLE);
                                }

                                if(count == 2) {
                                    linearLayout2.setVisibility(View.VISIBLE);
                                }

                                String [] images = new String[count];

                                for(int i=0; i < count; i++){
                                    images[i] = img[i];
                                }

                                Log.d("array", img.toString());

                                swipeadaptor swipeadaptor = new swipeadaptor(getBaseContext(), images);

                                viewPager.setAdapter(swipeadaptor);
                            }




                            String status = result.getString("status");

                            if(status.equals("User profile data is generated.")) {

                                JSONArray resultList = result.getJSONArray("resultList");
                                tv_state_value.setText(resultList.getJSONObject(0).getString("username"));
                                tv_city_value.setText(resultList.getJSONObject(0).getString("phone"));


                            }




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss();
                Toast.makeText(getBaseContext(), "Error while getting Contact details", Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringReq);
    }

    public void deleteVehicle(View view){
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Please Wait...");
        pd.show();

        String url = "http://ec2-35-167-97-234.us-west-2.compute.amazonaws.com:8080/api/" + vehicle_id + "/deleteVehicleEntry";

        StringRequest stringReq = new StringRequest(Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pd.dismiss();
                        try {
                            JSONObject result = new JSONObject(response);

                            String status = result.getString("status");

                            Toast.makeText(getBaseContext(), status ,Toast.LENGTH_LONG).show();

                            if(status.equals("Vehicle entry has been deleted successfully")) {
                                Intent intent = new Intent(getBaseContext(), showVehiclesActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss();
                Toast.makeText(getBaseContext(), "Error while deleting Vehicle", Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringReq);
    }

    public void goback(View view) {
        finish();
    }

    public void imagesUpdate(View view) {
        Intent i = new Intent(this, uploadVehicleActivity.class).putExtra("vehicleId", vehicle_id);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }
}
