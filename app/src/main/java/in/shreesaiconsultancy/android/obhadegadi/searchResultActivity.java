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


public class searchResultActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle navToggle;
    private Toolbar toolbar;
    private sessionManager session;


    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<vehicleResultItem> listItems;

    public ImageView iv_error;
    public TextView tv_error;

    private TextView novehicles;

    String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

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

        session = new sessionManager(this);

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
        if(action == null || !action.equals("Already created")) {
            Intent intent = getIntent();
            startActivity(intent);
            finish();
        }
        else
            getIntent().setAction(null);

        super.onResume();
    }



    private void loadRecyclerViewData() {
        listItems.clear();
        tv_error.setVisibility(View.INVISIBLE);
        iv_error.setVisibility(View.INVISIBLE);

        session = new sessionManager(this);
        String userId = session.getUserId();

        String result = getIntent().getStringExtra("resultList");

        try {
            Log.d("called","casdsad");
            JSONObject resultList = new JSONObject(result);
            JSONArray array = resultList.getJSONArray("resultList");

            for(int i=0; i<array.length(); i++) {
                JSONObject o = array.getJSONObject(i);

                if(!(o.getString("vehicleImageUrl").equals("null"))) {

                    String url  = "http://ec2-35-167-97-234.us-west-2.compute.amazonaws.com:8080/image/" + o.getString("userId") + "/" + o.getString("id") + "/VI.jpg";

                    vehicleResultItem item  = new vehicleResultItem(
                            o.getString("vehicleCompany") + " " + o.getString("vehicleModel"),
                            o.getString("perkmcharge"),
                            url,
                            o.getString("id"),
                            o.getString("userId"),
                            o.getString("vehicleCategory"),
                            o.getString("vehicleServiceState"),
                            o.getString("vehicleServiceCity"),
                            o.getString("verified")
                    );
                    listItems.add(item);
                } else {
                    vehicleResultItem item  = new vehicleResultItem(
                            o.getString("vehicleCompany") + " " + o.getString("vehicleModel"),
                            o.getString("perkmcharge"),
                            "noimage",
                            o.getString("id"),
                            o.getString("userId"),
                            o.getString("vehicleCategory"),
                            o.getString("vehicleServiceState"),
                            o.getString("vehicleServiceCity"),
                            o.getString("verified")
                    );
                    listItems.add(item);
                }

            }

            adapter = new vehicleResultAdapter(listItems, getApplicationContext());
            recyclerView.setAdapter(adapter);

            if(listItems.size() == 0) {
                novehicles = (TextView) findViewById(R.id.no_vehicles);
                novehicles.setVisibility(View.VISIBLE);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

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

    public void go_home(MenuItem item) {
        Intent intent = new Intent(this, HomePageActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


    public void go_search(MenuItem item) {
        Intent intent = new Intent(this, searchVehicleActivity.class);
        startActivity(intent);
    }

    public void go_support(MenuItem item) {
        Intent intent = new Intent(this, supportActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void go_about(MenuItem item) {
        Intent intent = new Intent(this, aboutActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void history(MenuItem item){
        Intent intent = new Intent(this, ownerHistoryActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
