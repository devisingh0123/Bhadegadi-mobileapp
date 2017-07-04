package in.shreesaiconsultancy.android.obhadegadi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class showVehiclesActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle navToggle;
    private Toolbar toolbar;
    private sessionManager session;


    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<vehicleListItem> listItems;

    public ImageView iv_error;
    public TextView tv_error;

    private TextView novehicles;

    String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_vehicles);

        getIntent().setAction("Already created");

        iv_error = (ImageView) findViewById(R.id.iv_error);
        tv_error = (TextView) findViewById(R.id.tv_error);

        recyclerView = (RecyclerView) findViewById(R.id.rv_vehicles);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        session = new sessionManager(this);
        userID = session.getUserId();

        listItems = new ArrayList<>();

        loadRecyclerViewData();

        // Menus

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);

        drawerLayout.addDrawerListener(navToggle);
        navToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav);
        View headerView = navigationView.getHeaderView(0);
        TextView app_name = (TextView) headerView.findViewById(R.id.menu_name);

        Typeface comfortaa = Typeface.createFromAsset(getAssets(), "fonts/Comfortaa-Bold.ttf");
        app_name.setTypeface(comfortaa);

    }

    @Override
    protected void onResume() {
        Log.v("Example", "onResume");

        String action = getIntent().getAction();
        // Prevent endless loop by adding a unique action, don't restart if action is present
        if(action == null || !action.equals("Already created")) {
            Intent intent = getIntent();
            startActivity(intent);
            finish();
        }
        // Remove the unique action so the next time onResume is called it will restart
        else
            getIntent().setAction(null);

        super.onResume();
    }



    private void loadRecyclerViewData() {
        listItems.clear();
        tv_error.setVisibility(View.INVISIBLE);
        iv_error.setVisibility(View.INVISIBLE);
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Please Wait...");
        pd.show();

        session = new sessionManager(this);
        String userId = session.getUserId();
        Log.d("user", userId);
        String url = "http://ec2-35-167-97-234.us-west-2.compute.amazonaws.com/api/" + userId + "/getUserVehicleList";
        Log.d("uel", url);

        StringRequest stringReq = new StringRequest(Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pd.dismiss();
                        try {
                            JSONObject result = new JSONObject(response);


                            Log.d("result", result.toString());

                            JSONArray array = result.getJSONArray("resultList");
                            boolean drivingLicensePresent = false;

                            for(int i=0; i<array.length(); i++) {
                                JSONObject o = array.getJSONObject(i);

                                if(!(o.getString("artifactList").equals("null"))) {
                                    JSONArray a = o.getJSONArray("artifactList");
                                    String js = a.toString();
                                    String[] x = js.split("\\,");
                                    for(int j =0; j<a.length(); j++ ){

                                        String[] y = x[j].split("\\/");

                                        if(y[(y.length - 1)].equals("VI.jpg\"") || y[(y.length - 1)].equals("VI.jpg\"]")) {
                                            drivingLicensePresent = true;
                                            break;
                                        }


                                    }
                                }





                                vehicleListItem item = new vehicleListItem(
                                        o.getString("vehicleCompany") + " " + o.getString("vehicleModel"),
                                        o.getString("perkmcharge"),
                                        String.valueOf(drivingLicensePresent),
                                        o.getString("vehicleID"),
                                        userID,
                                        o.getString("vehicleCategory"),
                                        o.getString("vehicleServiceState"),
                                        o.getString("vehicleServiceCity"),
                                        o.getString("verified")

                                );

                                listItems.add(item);
                            }

                            adapter = new vehicleAdapter(listItems, getApplicationContext());
                            recyclerView.setAdapter(adapter);

                            Log.d("List Size",Integer.toString(listItems.size()));

                            if(listItems.size() == 0) {
                                novehicles = (TextView) findViewById(R.id.no_vehicles);
                                novehicles.setVisibility(View.VISIBLE);
                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss();
                tv_error.setVisibility(View.VISIBLE);
                iv_error.setVisibility(View.VISIBLE);
                Toast.makeText(getBaseContext(), "Error while loading Vehicle List", Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringReq);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(navToggle.onOptionsItemSelected(item)) { return true; }

        return super.onOptionsItemSelected(item);
    }


    public void addVehicle(View view) {
        Intent intent = new Intent(this, addVehicleDetailsActivity.class);
        startActivity(intent);
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
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void history(MenuItem item){
        Intent intent = new Intent(this, ownerHistoryActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

}
