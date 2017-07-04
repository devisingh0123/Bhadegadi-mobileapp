package in.shreesaiconsultancy.android.obhadegadi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

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
//        night = (EditText) findViewById(R.id.et_night_allowance);
//        day = (EditText) findViewById(R.id.et_day_allowance);


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
                    adapterVCompany = ArrayAdapter.createFromResource(getBaseContext(), R.array.loading_taxi, R.layout.spinner_layout);

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

                // Vehicle luxury

                //Maruti Suzuki
                if (parent.getItemAtPosition(position).equals("Maruti Suzuki")) {
                    adapterVModel = ArrayAdapter.createFromResource(getBaseContext(), R.array.Maruti_Suzuki, R.layout.spinner_layout); }

//Hyundai
                if (parent.getItemAtPosition(position).equals("Hyundai")) {
                    adapterVModel = ArrayAdapter.createFromResource(getBaseContext(), R.array.Hyundai, R.layout.spinner_layout); }

//Volkswagen
                if (parent.getItemAtPosition(position).equals("Volkswagen")) {
                    adapterVModel = ArrayAdapter.createFromResource(getBaseContext(), R.array.Volkswagen, R.layout.spinner_layout); }

//Toyota
                if (parent.getItemAtPosition(position).equals("Toyota")) {
                    adapterVModel = ArrayAdapter.createFromResource(getBaseContext(), R.array.Toyota, R.layout.spinner_layout); }

//Honda
                if (parent.getItemAtPosition(position).equals("Honda")) {
                    adapterVModel = ArrayAdapter.createFromResource(getBaseContext(), R.array.Honda, R.layout.spinner_layout); }

//Ford
                if (parent.getItemAtPosition(position).equals("Ford")) {
                    adapterVModel = ArrayAdapter.createFromResource(getBaseContext(), R.array.Ford, R.layout.spinner_layout); }

//Tata
                if(type.equals("Luxury Taxi")) {
                    if (company.equals("Tata")) {
                        adapterVModel = ArrayAdapter.createFromResource(getBaseContext(), R.array.Tata, R.layout.spinner_layout);
                    }
                }
//Nissan
                if (parent.getItemAtPosition(position).equals("Nissan")) {
                    adapterVModel = ArrayAdapter.createFromResource(getBaseContext(), R.array.Nissan, R.layout.spinner_layout); }

//Mahindra
                if (parent.getItemAtPosition(position).equals("Mahindra")) {
                    adapterVModel = ArrayAdapter.createFromResource(getBaseContext(), R.array.Mahindra, R.layout.spinner_layout); }

                // Datsun
                if (parent.getItemAtPosition(position).equals("Datsun")) {
                    adapterVModel = ArrayAdapter.createFromResource(getBaseContext(), R.array.Datsun, R.layout.spinner_layout); }

//Renault
                if (parent.getItemAtPosition(position).equals("Renault")) {
                    adapterVModel = ArrayAdapter.createFromResource(getBaseContext(), R.array.Renault, R.layout.spinner_layout); }

//Audi
                if (parent.getItemAtPosition(position).equals("Audi")) {
                    adapterVModel = ArrayAdapter.createFromResource(getBaseContext(), R.array.Audi, R.layout.spinner_layout); }

//BMW
                if (parent.getItemAtPosition(position).equals("BMW")) {
                    adapterVModel = ArrayAdapter.createFromResource(getBaseContext(), R.array.BMW, R.layout.spinner_layout); }

//Mercedes Benz
                if (parent.getItemAtPosition(position).equals("Mercedes Benz")) {
                    adapterVModel = ArrayAdapter.createFromResource(getBaseContext(), R.array.Mercedes_Benz, R.layout.spinner_layout); }

//Skoda
                if (parent.getItemAtPosition(position).equals("Skoda")) {
                    adapterVModel = ArrayAdapter.createFromResource(getBaseContext(), R.array.Skoda, R.layout.spinner_layout); }

//Chevrolet
                if (parent.getItemAtPosition(position).equals("Chevrolet")) {
                    adapterVModel = ArrayAdapter.createFromResource(getBaseContext(), R.array.Chevrolet, R.layout.spinner_layout); }

//Aston Martin
                if (parent.getItemAtPosition(position).equals("Aston Martin")) {
                    adapterVModel = ArrayAdapter.createFromResource(getBaseContext(), R.array.Aston_Martin, R.layout.spinner_layout); }

//Fiat
                if (parent.getItemAtPosition(position).equals("Fiat")) {
                    adapterVModel = ArrayAdapter.createFromResource(getBaseContext(), R.array.Fiat, R.layout.spinner_layout); }

//Force Motors
                if (parent.getItemAtPosition(position).equals("Force Motors")) {
                    adapterVModel = ArrayAdapter.createFromResource(getBaseContext(), R.array.Force_Motors, R.layout.spinner_layout); }

//Isuzu
                if (parent.getItemAtPosition(position).equals("Isuzu")) {
                    adapterVModel = ArrayAdapter.createFromResource(getBaseContext(), R.array.Isuzu, R.layout.spinner_layout); }

//Jaguar
                if (parent.getItemAtPosition(position).equals("Jaguar")) {
                    adapterVModel = ArrayAdapter.createFromResource(getBaseContext(), R.array.Jaguar, R.layout.spinner_layout); }

//Land Rover
                if (parent.getItemAtPosition(position).equals("Land Rover")) {
                    adapterVModel = ArrayAdapter.createFromResource(getBaseContext(), R.array.Land_Rover, R.layout.spinner_layout); }

//Mitsubishi
                if (parent.getItemAtPosition(position).equals("Mitsubishi")) {
                    adapterVModel = ArrayAdapter.createFromResource(getBaseContext(), R.array.Mitsubishi, R.layout.spinner_layout); }

//Porsche
                if (parent.getItemAtPosition(position).equals("Porsche")) {
                    adapterVModel = ArrayAdapter.createFromResource(getBaseContext(), R.array.Porsche, R.layout.spinner_layout); }

//Rolls Royce
                if (parent.getItemAtPosition(position).equals("Rolls Royce")) {
                    adapterVModel = ArrayAdapter.createFromResource(getBaseContext(), R.array.Rolls_Royce, R.layout.spinner_layout); }

//Volvo
                if (parent.getItemAtPosition(position).equals("Volvo")) {
                    adapterVModel = ArrayAdapter.createFromResource(getBaseContext(), R.array.Volvo, R.layout.spinner_layout); }



                // Tata Loading
                if(type.equals("Loading Taxi")) {
                    if (parent.getItemAtPosition(position).equals("Tata")) {
                        adapterVModel = ArrayAdapter.createFromResource(getBaseContext(), R.array.tata_loading, R.layout.spinner_layout);
                    }
                }

                // Bajaj
                if (parent.getItemAtPosition(position).equals("Bajaj")) {
                    adapterVModel = ArrayAdapter.createFromResource(getBaseContext(), R.array.bajaj, R.layout.spinner_layout);
                }

                // Atul
                if (parent.getItemAtPosition(position).equals("Atul")) {
                    adapterVModel = ArrayAdapter.createFromResource(getBaseContext(), R.array.atul, R.layout.spinner_layout);
                }

                // Mahindra Loading
                if(type.equals("Loading Taxi")) {
                    if (parent.getItemAtPosition(position).equals("Mahindra")) {
                        adapterVModel = ArrayAdapter.createFromResource(getBaseContext(), R.array.mahindra, R.layout.spinner_layout);
                    }
                }

                // SML Isuzu
                if (parent.getItemAtPosition(position).equals("SML Isuzu")) {
                    adapterVModel = ArrayAdapter.createFromResource(getBaseContext(), R.array.sml_isuzu, R.layout.spinner_layout);
                }

                // Piaggio
                if (parent.getItemAtPosition(position).equals("Piaggio")) {
                    adapterVModel = ArrayAdapter.createFromResource(getBaseContext(), R.array.piaggio, R.layout.spinner_layout);
                }

                // maruti loading
                if (parent.getItemAtPosition(position).equals("Maruti")) {
                    adapterVModel = ArrayAdapter.createFromResource(getBaseContext(), R.array.maruti, R.layout.spinner_layout);
                }


                if (parent.getItemAtPosition(position).equals("AMW")) {
                    adapterVModel = ArrayAdapter.createFromResource(getBaseContext(), R.array.amw, R.layout.spinner_layout);
                }

                if (parent.getItemAtPosition(position).equals("Ashok Leyland")) {
                    adapterVModel = ArrayAdapter.createFromResource(getBaseContext(), R.array.ashok_leyland, R.layout.spinner_layout);
                }

                if(type.equals("Heavy Vehicle")) {
                    if (parent.getItemAtPosition(position).equals("Tata")) {
                        adapterVModel = ArrayAdapter.createFromResource(getBaseContext(), R.array.tata_heavy, R.layout.spinner_layout);
                    }
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
//        String nightallowance = night.getText().toString();
        String nightallowance = "1";
//        String dayallowance = day.getText().toString();
        String dayallowance = "1";


        //Validation and Logic

        if(perkmcharge.length() == 0
                || type.equals("Select Vehicle Type*")
                || company.equals("Select Vehicle Company*")
                || model.equals("Select Vehicle Model*")
                || cities.equals("Select Vehicle City*")
                || state.equals("Select Vehicle State*")) {
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
