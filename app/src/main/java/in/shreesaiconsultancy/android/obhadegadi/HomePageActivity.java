package in.shreesaiconsultancy.android.obhadegadi;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class HomePageActivity extends AppCompatActivity {


    sessionManager session;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle navToggle;
    private Toolbar toolbar;
    private TextView tv_one, tv_two, tv_three, tv_four;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        tv_one = (TextView) findViewById(R.id.tv_one);
        tv_two = (TextView) findViewById(R.id.tv_two);
        tv_three = (TextView) findViewById(R.id.tv_three);
        tv_four = (TextView) findViewById(R.id.tv_four);


        Typeface monts = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Regular.otf");
        tv_one.setTypeface(monts);
        tv_two.setTypeface(monts);
        tv_three.setTypeface(monts);
        tv_four.setTypeface(monts);


        // Menu Initialization
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


    // Button Click Events

    public void add_new_vehicles(View view) {
        Intent intent = new Intent(this, addVehicleDetailsActivity.class);
        startActivity(intent);
    }

    public void go_history(View view) {
        Intent intent = new Intent(this, ownerHistoryActivity.class);
        startActivity(intent);
    }

    public void search_vehicles(View view) {
        Intent intent = new Intent(this,searchVehicleActivity.class);
        startActivity(intent);
    }

    public void show_vehicles(View view) {
        Intent intent = new Intent(this, showVehiclesActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(navToggle.onOptionsItemSelected(item)) { return true; }

        return super.onOptionsItemSelected(item);
    }

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
