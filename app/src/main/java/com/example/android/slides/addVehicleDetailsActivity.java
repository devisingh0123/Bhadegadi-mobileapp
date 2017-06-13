package com.example.android.slides;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class addVehicleDetailsActivity extends AppCompatActivity {

    sessionManager session;
    Spinner vehicleType, vehicleCompany, vehicleModel, states, city;
    ArrayAdapter<CharSequence> adapterVType, adapterVCompany, adapterVModel, adapterState, adapterCity;
    String type, company, model, state, cities, userId;
    EditText perkm, night, day;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle_details);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        session = new sessionManager(this);
        userId = session.getUserId();


        perkm = (EditText) findViewById(R.id.et_pkm_charge);
        night = (EditText) findViewById(R.id.et_night_allowance);
        day = (EditText) findViewById(R.id.et_day_allowance);


        vehicleType = (Spinner) findViewById(R.id.sp_vehicle_type);

        /* Initialize Vehicle Company Spinner */
        vehicleCompany = (Spinner) findViewById(R.id.sp_vehicle_company);

        /* Initialize Vehicle Model Spinner */
        vehicleModel = (Spinner) findViewById(R.id.sp_vehicle_model);

        /* Initialize city spinners */
        city = (Spinner) findViewById(R.id.sp_service_city);



        /* Initialize Vehicle Type Spinner */
        adapterVType = ArrayAdapter.createFromResource(this, R.array.vehicle_type, R.layout.spinner_layout);
        adapterVType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vehicleType.setAdapter(adapterVType);
        vehicleType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                type = parent.getItemAtPosition(position).toString();

                // Default
                if (parent.getItemAtPosition(position).equals("Select Vehicle Type*")) {
                    adapterVCompany = ArrayAdapter.createFromResource(getBaseContext(), R.array.company_default, R.layout.spinner_layout);

                }

                // Luxury Taxi
                if (parent.getItemAtPosition(position).equals("Luxury Taxi")) {
                    adapterVCompany = ArrayAdapter.createFromResource(getBaseContext(), R.array.luxury, R.layout.spinner_layout);

                }


                // Loading Taxi
                if (parent.getItemAtPosition(position).equals("Loading Taxi")) {
                    adapterVCompany = ArrayAdapter.createFromResource(getBaseContext(), R.array.company_default, R.layout.spinner_layout);

                }

                // Heavy Vehicle
                if (parent.getItemAtPosition(position).equals("Heavy Vehicle")) {
                    adapterVCompany = ArrayAdapter.createFromResource(getBaseContext(), R.array.heavy, R.layout.spinner_layout);

                }

                adapterVCompany.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                vehicleCompany.setAdapter(adapterVCompany);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }


        });

        vehicleCompany.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                company = parent.getItemAtPosition(position).toString();

                // Default
                if (parent.getItemAtPosition(position).equals("Select Vehicle Company*")) {
                    adapterVModel = ArrayAdapter.createFromResource(getBaseContext(), R.array.model_default, R.layout.spinner_layout);
                }

                // Maruti Suzuki
                if (parent.getItemAtPosition(position).equals("Maruti Suzuki")) {
                    adapterVModel = ArrayAdapter.createFromResource(getBaseContext(), R.array.maruti, R.layout.spinner_layout);
                }

                // Tata
                if (parent.getItemAtPosition(position).equals("Tata")) {
                    adapterVModel = ArrayAdapter.createFromResource(getBaseContext(), R.array.tata, R.layout.spinner_layout);
                }

                // Maruti Suzuki
                if (parent.getItemAtPosition(position).equals("Tata ")) {
                    adapterVModel = ArrayAdapter.createFromResource(getBaseContext(), R.array.tata_heavy, R.layout.spinner_layout);
                }

                // Tata
                if (parent.getItemAtPosition(position).equals("Ashok Leyland")) {
                    adapterVModel = ArrayAdapter.createFromResource(getBaseContext(), R.array.ashok, R.layout.spinner_layout);
                }

                // Eicher
                if (parent.getItemAtPosition(position).equals("Eicher")) {
                    adapterVModel = ArrayAdapter.createFromResource(getBaseContext(), R.array.eicher, R.layout.spinner_layout);
                }


                adapterVModel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                vehicleModel.setAdapter(adapterVModel);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        vehicleModel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                model = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        /* Initialize State Spinner */
        states = (Spinner) findViewById(R.id.sp_service_state);
        adapterState = ArrayAdapter.createFromResource(this, R.array.states, R.layout.spinner_layout);
        adapterState.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        states.setAdapter(adapterState);
        states.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                state = parent.getItemAtPosition(position).toString();


                // Default
                if (parent.getItemAtPosition(position).equals("Select Service State*")) {
                    adapterCity = ArrayAdapter.createFromResource(getBaseContext(), R.array.city_default, R.layout.spinner_layout);
                }

                // Andra Pradesh
                if (parent.getItemAtPosition(position).equals("Andra Pradesh")) {
                    adapterCity = ArrayAdapter.createFromResource(getBaseContext(), R.array.andra, R.layout.spinner_layout);
                }

                // Arunachal Pradesh
                if (parent.getItemAtPosition(position).equals("Arunachal Pradesh")) {
                    adapterCity = ArrayAdapter.createFromResource(getBaseContext(), R.array.arunachal, R.layout.spinner_layout);
                }

                // Assam
                if (parent.getItemAtPosition(position).equals("Assam")) {
                    adapterCity = ArrayAdapter.createFromResource(getBaseContext(), R.array.assam, R.layout.spinner_layout);
                }

                // Bihar
                if (parent.getItemAtPosition(position).equals("Bihar")) {
                    adapterCity = ArrayAdapter.createFromResource(getBaseContext(), R.array.bihar, R.layout.spinner_layout);
                }

                // Chhattisgarh
                if (parent.getItemAtPosition(position).equals("Chhattisgarh")) {
                    adapterCity = ArrayAdapter.createFromResource(getBaseContext(), R.array.chhattisgarh, R.layout.spinner_layout);
                }

                // Goa
                if (parent.getItemAtPosition(position).equals("Goa")) {
                    adapterCity = ArrayAdapter.createFromResource(getBaseContext(), R.array.goa, R.layout.spinner_layout);
                }

                // Gujarat
                if (parent.getItemAtPosition(position).equals("Gujarat")) {
                    adapterCity = ArrayAdapter.createFromResource(getBaseContext(), R.array.Gujarat, R.layout.spinner_layout);
                }

                // Haryana
                if (parent.getItemAtPosition(position).equals("Haryana")) {
                    adapterCity = ArrayAdapter.createFromResource(getBaseContext(), R.array.haryana, R.layout.spinner_layout);
                }

                // Himachal Pradesh
                if (parent.getItemAtPosition(position).equals("Himachal Pradesh")) {
                    adapterCity = ArrayAdapter.createFromResource(getBaseContext(), R.array.himachal, R.layout.spinner_layout);
                }

                // Himachal Pradesh
                if (parent.getItemAtPosition(position).equals("Himachal Pradesh")) {
                    adapterCity = ArrayAdapter.createFromResource(getBaseContext(), R.array.himachal, R.layout.spinner_layout);
                }

                // Jammu and Kashmir
                if (parent.getItemAtPosition(position).equals("Jammu and Kashmir")) {
                    adapterCity = ArrayAdapter.createFromResource(getBaseContext(), R.array.jk, R.layout.spinner_layout);
                }

                // Jharkhand
                if (parent.getItemAtPosition(position).equals("Jharkhand")) {
                    adapterCity = ArrayAdapter.createFromResource(getBaseContext(), R.array.jharkhand, R.layout.spinner_layout);
                }

                // Karnataka
                if (parent.getItemAtPosition(position).equals("Karnataka")) {
                    adapterCity = ArrayAdapter.createFromResource(getBaseContext(), R.array.karnataka, R.layout.spinner_layout);
                }

                // Kerala
                if (parent.getItemAtPosition(position).equals("Kerala")) {
                    adapterCity = ArrayAdapter.createFromResource(getBaseContext(), R.array.Kerala, R.layout.spinner_layout);
                }

                // Madhya Pradesh
                if (parent.getItemAtPosition(position).equals("Madhya Pradesh")) {
                    adapterCity = ArrayAdapter.createFromResource(getBaseContext(), R.array.mp, R.layout.spinner_layout);
                }

                // Maharashtra
                if (parent.getItemAtPosition(position).equals("Maharashtra")) {
                    adapterCity = ArrayAdapter.createFromResource(getBaseContext(), R.array.maharastra, R.layout.spinner_layout);
                }

                // Manipur
                if (parent.getItemAtPosition(position).equals("Manipur")) {
                    adapterCity = ArrayAdapter.createFromResource(getBaseContext(), R.array.manipur, R.layout.spinner_layout);
                }

                // Meghalaya
                if (parent.getItemAtPosition(position).equals("Meghalaya")) {
                    adapterCity = ArrayAdapter.createFromResource(getBaseContext(), R.array.meghalaya, R.layout.spinner_layout);
                }

                // Mizoram
                if (parent.getItemAtPosition(position).equals("Mizoram")) {
                    adapterCity = ArrayAdapter.createFromResource(getBaseContext(), R.array.mizoram, R.layout.spinner_layout);
                }

                // Nagaland
                if (parent.getItemAtPosition(position).equals("Nagaland")) {
                    adapterCity = ArrayAdapter.createFromResource(getBaseContext(), R.array.nagaland, R.layout.spinner_layout);
                }

                // Orissa
                if (parent.getItemAtPosition(position).equals("Orissa")) {
                    adapterCity = ArrayAdapter.createFromResource(getBaseContext(), R.array.Orissa, R.layout.spinner_layout);
                }

                // Punjab
                if (parent.getItemAtPosition(position).equals("Punjab")) {
                    adapterCity = ArrayAdapter.createFromResource(getBaseContext(), R.array.Punjab, R.layout.spinner_layout);
                }

                // Rajasthan
                if (parent.getItemAtPosition(position).equals("Rajasthan")) {
                    adapterCity = ArrayAdapter.createFromResource(getBaseContext(), R.array.Rajasthan, R.layout.spinner_layout);
                }

                // Sikkim
                if (parent.getItemAtPosition(position).equals("Sikkim")) {
                    adapterCity = ArrayAdapter.createFromResource(getBaseContext(), R.array.Sikkim, R.layout.spinner_layout);
                }

                // Tamil Nadu
                if (parent.getItemAtPosition(position).equals("Tamil Nadu")) {
                    adapterCity = ArrayAdapter.createFromResource(getBaseContext(), R.array.tn, R.layout.spinner_layout);
                }

                // Telangana
                if (parent.getItemAtPosition(position).equals("Telangana")) {
                    adapterCity = ArrayAdapter.createFromResource(getBaseContext(), R.array.Telangana, R.layout.spinner_layout);
                }

                // Tripura
                if (parent.getItemAtPosition(position).equals("Tripura")) {
                    adapterCity = ArrayAdapter.createFromResource(getBaseContext(), R.array.Tripura, R.layout.spinner_layout);
                }

                // Uttar Pradesh
                if (parent.getItemAtPosition(position).equals("Uttar Pradesh")) {
                    adapterCity = ArrayAdapter.createFromResource(getBaseContext(), R.array.up, R.layout.spinner_layout);
                }

                // Uttarakhand
                if (parent.getItemAtPosition(position).equals("Uttarakhand")) {
                    adapterCity = ArrayAdapter.createFromResource(getBaseContext(), R.array.Uttarakhand, R.layout.spinner_layout);
                }

                // West Bengal
                if (parent.getItemAtPosition(position).equals("West Bengal")) {
                    adapterCity = ArrayAdapter.createFromResource(getBaseContext(), R.array.Bengal, R.layout.spinner_layout);
                }


                adapterCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                city.setAdapter(adapterCity);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cities = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }







    /* Add Vehicle Button */

    public void addVehicle(View view) {

        String perkmcharge = perkm.getText().toString();
        String nightallowance = night.getText().toString();
        String dayallowance = day.getText().toString();


        //Validation and Logic

        if(perkmcharge.length() == 0 || nightallowance.length() == 0 || dayallowance.length() == 0 || type.equals("Select Vehicle Type*") || company.equals("Select Vehicle Company*") || model.equals("Select Vehicle Model*") || cities.equals("Select Vehicle City*") || state.equals("Select Vehicle State*")) {
            Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_LONG).show();
        } else {
            addVehicle add = new addVehicle(this);
            add.execute(dayallowance, nightallowance, perkmcharge, userId, type, company, model, cities, state);
        }

//        Intent intent = new Intent(this, uploadVehicleActivity.class).putExtra("vehicleId", "7");
//        startActivity(intent);


    }

    public void goback(View view) {
        finish();
    }



}
