package in.shreesaiconsultancy.android.obhadegadi;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class searchVehicleActivity extends AppCompatActivity {

    sessionManager session;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle navToggle;
    private Toolbar toolbar;

    Spinner vehicleType, vehicleCompany, vehicleModel, states, city;
    ArrayAdapter<CharSequence> adapterVType, adapterVCompany, adapterVModel, adapterState, adapterCity;
    String type, company, model, state, cities, userId;

    TextView tv_From, tv_To;

    Calendar calendar;

    String clicked = "none";

    int fyear, fday, fmonth;
    int tyear, tday, tmonth;
    int cyear, cday, cmonth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_vehicle);


        vehicleType = (Spinner) findViewById(R.id.sp_vehicle_type);

        /* Initialize Vehicle Company Spinner */
        vehicleCompany = (Spinner) findViewById(R.id.sp_vehicle_company);

        /* Initialize Vehicle Model Spinner */
        vehicleModel = (Spinner) findViewById(R.id.sp_vehicle_model);

        /* Initialize city spinners */
        city = (Spinner) findViewById(R.id.sp_service_city);


        tv_From = (TextView) findViewById(R.id.from_date);
        tv_To = (TextView) findViewById(R.id.to_date);

        calendar = Calendar.getInstance();
        cyear = calendar.get(Calendar.YEAR);
        cmonth = calendar.get(Calendar.MONTH);
        cday = calendar.get(Calendar.DAY_OF_MONTH);




        /* Initialize Vehicle Type Spinner */
        adapterVType = ArrayAdapter.createFromResource(this, R.array.vehicle_type, R.layout.spinner_layout_search);
        adapterVType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vehicleType.setAdapter(adapterVType);
        vehicleType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                type = parent.getItemAtPosition(position).toString();

                // Default
                if (parent.getItemAtPosition(position).equals("Select Vehicle Type")) {
                    adapterVCompany = ArrayAdapter.createFromResource(getBaseContext(), R.array.company_default, R.layout.spinner_layout_search);

                }

                // Luxury Taxi
                if (parent.getItemAtPosition(position).equals("Luxury Taxi")) {
                    adapterVCompany = ArrayAdapter.createFromResource(getBaseContext(), R.array.luxury, R.layout.spinner_layout_search);

                }


                // Loading Taxi
                if (parent.getItemAtPosition(position).equals("Loading Taxi")) {
                    adapterVCompany = ArrayAdapter.createFromResource(getBaseContext(), R.array.loading_taxi, R.layout.spinner_layout_search);

                }

                // Heavy Vehicle
                if (parent.getItemAtPosition(position).equals("Heavy Vehicle")) {
                    adapterVCompany = ArrayAdapter.createFromResource(getBaseContext(), R.array.heavy, R.layout.spinner_layout_search);

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
                if (parent.getItemAtPosition(position).equals("Select Vehicle Company")) {
                    adapterVModel = ArrayAdapter.createFromResource(getBaseContext(), R.array.model_default, R.layout.spinner_layout_search);
                }

                // Vehicle luxury

                //Maruti Suzuki
                if (parent.getItemAtPosition(position).equals("Maruti Suzuki")) {
                    adapterVModel = ArrayAdapter.createFromResource(getBaseContext(), R.array.Maruti_Suzuki, R.layout.spinner_layout_search); }

//Hyundai
                if (parent.getItemAtPosition(position).equals("Hyundai")) {
                    adapterVModel = ArrayAdapter.createFromResource(getBaseContext(), R.array.Hyundai, R.layout.spinner_layout_search); }

//Volkswagen
                if (parent.getItemAtPosition(position).equals("Volkswagen")) {
                    adapterVModel = ArrayAdapter.createFromResource(getBaseContext(), R.array.Volkswagen, R.layout.spinner_layout_search); }

//Toyota
                if (parent.getItemAtPosition(position).equals("Toyota")) {
                    adapterVModel = ArrayAdapter.createFromResource(getBaseContext(), R.array.Toyota, R.layout.spinner_layout_search); }

//Honda
                if (parent.getItemAtPosition(position).equals("Honda")) {
                    adapterVModel = ArrayAdapter.createFromResource(getBaseContext(), R.array.Honda, R.layout.spinner_layout_search); }

//Ford
                if (parent.getItemAtPosition(position).equals("Ford")) {
                    adapterVModel = ArrayAdapter.createFromResource(getBaseContext(), R.array.Ford, R.layout.spinner_layout_search); }

//Tata
                if(type.equals("Luxury Taxi")) {
                    if (company.equals("Tata")) {
                        adapterVModel = ArrayAdapter.createFromResource(getBaseContext(), R.array.Tata, R.layout.spinner_layout_search);
                    }
                }
//Nissan
                if (parent.getItemAtPosition(position).equals("Nissan")) {
                    adapterVModel = ArrayAdapter.createFromResource(getBaseContext(), R.array.Nissan, R.layout.spinner_layout_search); }

//Mahindra
                if (parent.getItemAtPosition(position).equals("Mahindra")) {
                    adapterVModel = ArrayAdapter.createFromResource(getBaseContext(), R.array.Mahindra, R.layout.spinner_layout_search); }

                // Datsun
                if (parent.getItemAtPosition(position).equals("Datsun")) {
                    adapterVModel = ArrayAdapter.createFromResource(getBaseContext(), R.array.Datsun, R.layout.spinner_layout_search); }

//Renault
                if (parent.getItemAtPosition(position).equals("Renault")) {
                    adapterVModel = ArrayAdapter.createFromResource(getBaseContext(), R.array.Renault, R.layout.spinner_layout_search); }

//Audi
                if (parent.getItemAtPosition(position).equals("Audi")) {
                    adapterVModel = ArrayAdapter.createFromResource(getBaseContext(), R.array.Audi, R.layout.spinner_layout_search); }

//BMW
                if (parent.getItemAtPosition(position).equals("BMW")) {
                    adapterVModel = ArrayAdapter.createFromResource(getBaseContext(), R.array.BMW, R.layout.spinner_layout_search); }

//Mercedes Benz
                if (parent.getItemAtPosition(position).equals("Mercedes Benz")) {
                    adapterVModel = ArrayAdapter.createFromResource(getBaseContext(), R.array.Mercedes_Benz, R.layout.spinner_layout_search); }

//Skoda
                if (parent.getItemAtPosition(position).equals("Skoda")) {
                    adapterVModel = ArrayAdapter.createFromResource(getBaseContext(), R.array.Skoda, R.layout.spinner_layout_search); }

//Chevrolet
                if (parent.getItemAtPosition(position).equals("Chevrolet")) {
                    adapterVModel = ArrayAdapter.createFromResource(getBaseContext(), R.array.Chevrolet, R.layout.spinner_layout_search); }

//Aston Martin
                if (parent.getItemAtPosition(position).equals("Aston Martin")) {
                    adapterVModel = ArrayAdapter.createFromResource(getBaseContext(), R.array.Aston_Martin, R.layout.spinner_layout_search); }

//Fiat
                if (parent.getItemAtPosition(position).equals("Fiat")) {
                    adapterVModel = ArrayAdapter.createFromResource(getBaseContext(), R.array.Fiat, R.layout.spinner_layout_search); }

//Force Motors
                if (parent.getItemAtPosition(position).equals("Force Motors")) {
                    adapterVModel = ArrayAdapter.createFromResource(getBaseContext(), R.array.Force_Motors, R.layout.spinner_layout_search); }

//Isuzu
                if (parent.getItemAtPosition(position).equals("Isuzu")) {
                    adapterVModel = ArrayAdapter.createFromResource(getBaseContext(), R.array.Isuzu, R.layout.spinner_layout_search); }

//Jaguar
                if (parent.getItemAtPosition(position).equals("Jaguar")) {
                    adapterVModel = ArrayAdapter.createFromResource(getBaseContext(), R.array.Jaguar, R.layout.spinner_layout_search); }

//Land Rover
                if (parent.getItemAtPosition(position).equals("Land Rover")) {
                    adapterVModel = ArrayAdapter.createFromResource(getBaseContext(), R.array.Land_Rover, R.layout.spinner_layout_search); }

//Mitsubishi
                if (parent.getItemAtPosition(position).equals("Mitsubishi")) {
                    adapterVModel = ArrayAdapter.createFromResource(getBaseContext(), R.array.Mitsubishi, R.layout.spinner_layout_search); }

//Porsche
                if (parent.getItemAtPosition(position).equals("Porsche")) {
                    adapterVModel = ArrayAdapter.createFromResource(getBaseContext(), R.array.Porsche, R.layout.spinner_layout_search); }

//Rolls Royce
                if (parent.getItemAtPosition(position).equals("Rolls Royce")) {
                    adapterVModel = ArrayAdapter.createFromResource(getBaseContext(), R.array.Rolls_Royce, R.layout.spinner_layout_search); }

//Volvo
                if (parent.getItemAtPosition(position).equals("Volvo")) {
                    adapterVModel = ArrayAdapter.createFromResource(getBaseContext(), R.array.Volvo, R.layout.spinner_layout_search); }



                // Tata Loading
                if(type.equals("Loading Taxi")) {
                    if (parent.getItemAtPosition(position).equals("Tata")) {
                        adapterVModel = ArrayAdapter.createFromResource(getBaseContext(), R.array.tata_loading, R.layout.spinner_layout_search);
                    }
                }

                // Bajaj
                if (parent.getItemAtPosition(position).equals("Bajaj")) {
                    adapterVModel = ArrayAdapter.createFromResource(getBaseContext(), R.array.bajaj, R.layout.spinner_layout_search);
                }

                // Atul
                if (parent.getItemAtPosition(position).equals("Atul")) {
                    adapterVModel = ArrayAdapter.createFromResource(getBaseContext(), R.array.atul, R.layout.spinner_layout_search);
                }

                // Mahindra Loading
                if(type.equals("Loading Taxi")) {
                    if (parent.getItemAtPosition(position).equals("Mahindra")) {
                        adapterVModel = ArrayAdapter.createFromResource(getBaseContext(), R.array.mahindra, R.layout.spinner_layout_search);
                    }
                }

                // SML Isuzu
                if (parent.getItemAtPosition(position).equals("SML Isuzu")) {
                    adapterVModel = ArrayAdapter.createFromResource(getBaseContext(), R.array.sml_isuzu, R.layout.spinner_layout_search);
                }

                // Piaggio
                if (parent.getItemAtPosition(position).equals("Piaggio")) {
                    adapterVModel = ArrayAdapter.createFromResource(getBaseContext(), R.array.piaggio, R.layout.spinner_layout_search);
                }

                // maruti loading
                if (parent.getItemAtPosition(position).equals("Maruti")) {
                    adapterVModel = ArrayAdapter.createFromResource(getBaseContext(), R.array.maruti, R.layout.spinner_layout_search);
                }


                if (parent.getItemAtPosition(position).equals("AMW")) {
                    adapterVModel = ArrayAdapter.createFromResource(getBaseContext(), R.array.amw, R.layout.spinner_layout_search);
                }

                if (parent.getItemAtPosition(position).equals("Ashok Leyland")) {
                    adapterVModel = ArrayAdapter.createFromResource(getBaseContext(), R.array.ashok_leyland, R.layout.spinner_layout_search);
                }

                if(type.equals("Heavy Vehicle")) {
                    if (parent.getItemAtPosition(position).equals("Tata")) {
                        adapterVModel = ArrayAdapter.createFromResource(getBaseContext(), R.array.tata_heavy, R.layout.spinner_layout_search);
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
        adapterState = ArrayAdapter.createFromResource(this, R.array.states, R.layout.spinner_layout_search);
        adapterState.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        states.setAdapter(adapterState);
        states.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                state = parent.getItemAtPosition(position).toString();


                // Default
                if (parent.getItemAtPosition(position).equals("Select Service State")) {
                    adapterCity = ArrayAdapter.createFromResource(getBaseContext(), R.array.city_default, R.layout.spinner_layout_search);
                }

                // Andra Pradesh
                if (parent.getItemAtPosition(position).equals("Andra Pradesh")) {
                    adapterCity = ArrayAdapter.createFromResource(getBaseContext(), R.array.andra, R.layout.spinner_layout_search);
                }

                // Arunachal Pradesh
                if (parent.getItemAtPosition(position).equals("Arunachal Pradesh")) {
                    adapterCity = ArrayAdapter.createFromResource(getBaseContext(), R.array.arunachal, R.layout.spinner_layout_search);
                }

                // Assam
                if (parent.getItemAtPosition(position).equals("Assam")) {
                    adapterCity = ArrayAdapter.createFromResource(getBaseContext(), R.array.assam, R.layout.spinner_layout_search);
                }

                // Bihar
                if (parent.getItemAtPosition(position).equals("Bihar")) {
                    adapterCity = ArrayAdapter.createFromResource(getBaseContext(), R.array.bihar, R.layout.spinner_layout_search);
                }

                // Chhattisgarh
                if (parent.getItemAtPosition(position).equals("Chhattisgarh")) {
                    adapterCity = ArrayAdapter.createFromResource(getBaseContext(), R.array.chhattisgarh, R.layout.spinner_layout_search);
                }

                // Goa
                if (parent.getItemAtPosition(position).equals("Goa")) {
                    adapterCity = ArrayAdapter.createFromResource(getBaseContext(), R.array.goa, R.layout.spinner_layout_search);
                }

                // Gujarat
                if (parent.getItemAtPosition(position).equals("Gujarat")) {
                    adapterCity = ArrayAdapter.createFromResource(getBaseContext(), R.array.Gujarat, R.layout.spinner_layout_search);
                }

                // Haryana
                if (parent.getItemAtPosition(position).equals("Haryana")) {
                    adapterCity = ArrayAdapter.createFromResource(getBaseContext(), R.array.haryana, R.layout.spinner_layout_search);
                }

                // Himachal Pradesh
                if (parent.getItemAtPosition(position).equals("Himachal Pradesh")) {
                    adapterCity = ArrayAdapter.createFromResource(getBaseContext(), R.array.himachal, R.layout.spinner_layout_search);
                }

                // Himachal Pradesh
                if (parent.getItemAtPosition(position).equals("Himachal Pradesh")) {
                    adapterCity = ArrayAdapter.createFromResource(getBaseContext(), R.array.himachal, R.layout.spinner_layout_search);
                }

                // Jammu and Kashmir
                if (parent.getItemAtPosition(position).equals("Jammu and Kashmir")) {
                    adapterCity = ArrayAdapter.createFromResource(getBaseContext(), R.array.jk, R.layout.spinner_layout_search);
                }

                // Jharkhand
                if (parent.getItemAtPosition(position).equals("Jharkhand")) {
                    adapterCity = ArrayAdapter.createFromResource(getBaseContext(), R.array.jharkhand, R.layout.spinner_layout_search);
                }

                // Karnataka
                if (parent.getItemAtPosition(position).equals("Karnataka")) {
                    adapterCity = ArrayAdapter.createFromResource(getBaseContext(), R.array.karnataka, R.layout.spinner_layout_search);
                }

                // Kerala
                if (parent.getItemAtPosition(position).equals("Kerala")) {
                    adapterCity = ArrayAdapter.createFromResource(getBaseContext(), R.array.Kerala, R.layout.spinner_layout_search);
                }

                // Madhya Pradesh
                if (parent.getItemAtPosition(position).equals("Madhya Pradesh")) {
                    adapterCity = ArrayAdapter.createFromResource(getBaseContext(), R.array.mp, R.layout.spinner_layout_search);
                }

                // Maharashtra
                if (parent.getItemAtPosition(position).equals("Maharashtra")) {
                    adapterCity = ArrayAdapter.createFromResource(getBaseContext(), R.array.maharastra, R.layout.spinner_layout_search);
                }

                // Manipur
                if (parent.getItemAtPosition(position).equals("Manipur")) {
                    adapterCity = ArrayAdapter.createFromResource(getBaseContext(), R.array.manipur, R.layout.spinner_layout_search);
                }

                // Meghalaya
                if (parent.getItemAtPosition(position).equals("Meghalaya")) {
                    adapterCity = ArrayAdapter.createFromResource(getBaseContext(), R.array.meghalaya, R.layout.spinner_layout_search);
                }

                // Mizoram
                if (parent.getItemAtPosition(position).equals("Mizoram")) {
                    adapterCity = ArrayAdapter.createFromResource(getBaseContext(), R.array.mizoram, R.layout.spinner_layout_search);
                }

                // Nagaland
                if (parent.getItemAtPosition(position).equals("Nagaland")) {
                    adapterCity = ArrayAdapter.createFromResource(getBaseContext(), R.array.nagaland, R.layout.spinner_layout_search);
                }

                // Orissa
                if (parent.getItemAtPosition(position).equals("Orissa")) {
                    adapterCity = ArrayAdapter.createFromResource(getBaseContext(), R.array.Orissa, R.layout.spinner_layout_search);
                }

                // Punjab
                if (parent.getItemAtPosition(position).equals("Punjab")) {
                    adapterCity = ArrayAdapter.createFromResource(getBaseContext(), R.array.Punjab, R.layout.spinner_layout_search);
                }

                // Rajasthan
                if (parent.getItemAtPosition(position).equals("Rajasthan")) {
                    adapterCity = ArrayAdapter.createFromResource(getBaseContext(), R.array.Rajasthan, R.layout.spinner_layout_search);
                }

                // Sikkim
                if (parent.getItemAtPosition(position).equals("Sikkim")) {
                    adapterCity = ArrayAdapter.createFromResource(getBaseContext(), R.array.Sikkim, R.layout.spinner_layout_search);
                }

                // Tamil Nadu
                if (parent.getItemAtPosition(position).equals("Tamil Nadu")) {
                    adapterCity = ArrayAdapter.createFromResource(getBaseContext(), R.array.tn, R.layout.spinner_layout_search);
                }

                // Telangana
                if (parent.getItemAtPosition(position).equals("Telangana")) {
                    adapterCity = ArrayAdapter.createFromResource(getBaseContext(), R.array.Telangana, R.layout.spinner_layout_search);
                }

                // Tripura
                if (parent.getItemAtPosition(position).equals("Tripura")) {
                    adapterCity = ArrayAdapter.createFromResource(getBaseContext(), R.array.Tripura, R.layout.spinner_layout_search);
                }

                // Uttar Pradesh
                if (parent.getItemAtPosition(position).equals("Uttar Pradesh")) {
                    adapterCity = ArrayAdapter.createFromResource(getBaseContext(), R.array.up, R.layout.spinner_layout_search);
                }

                // Uttarakhand
                if (parent.getItemAtPosition(position).equals("Uttarakhand")) {
                    adapterCity = ArrayAdapter.createFromResource(getBaseContext(), R.array.Uttarakhand, R.layout.spinner_layout_search);
                }

                // West Bengal
                if (parent.getItemAtPosition(position).equals("West Bengal")) {
                    adapterCity = ArrayAdapter.createFromResource(getBaseContext(), R.array.Bengal, R.layout.spinner_layout_search);
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


    @SuppressWarnings("deprecation")
    public void fromDate (View view) {
        clicked = "from";
        showDialog(999);
    }
    @SuppressWarnings("deprecation")
    public void toDate (View view) {
        clicked = "to";
        showDialog(999);
    }


    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, cyear, cmonth, cday);
        }
        return null;


    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {

                    if(clicked.equals("from")){
                        boolean prev_date = false;

                        fyear = arg1;
                        fmonth = arg2+1;
                        fday = arg3;

                        if(cyear > arg1) {
                            prev_date = true;
                        }
                        if (cmonth+1 > arg2+1 && cyear == arg1) {
                            prev_date = true;
                        }
                        if (cday > arg3 && cmonth+1 == arg2+1 && cyear == arg1) {
                            prev_date =true;
                        }


                        if(prev_date){
                            Toast.makeText(getBaseContext(), "Please do not select a previous date.", Toast.LENGTH_LONG).show();
                        } else {
                            showDate(arg1, arg2+1, arg3);
                        }
                    }

                    if(clicked.equals("to")) {
                        if(tv_From.getText().equals("")) {
                            Toast.makeText(getBaseContext(), "Please select from date first", Toast.LENGTH_LONG).show();
                        } else {

                            tyear = arg1;
                            tmonth = arg2+1;
                            tday = arg3;

                            boolean prev_date = false;

                            if(fyear > arg1) {
                                prev_date = true;
                            }
                            if (fmonth > arg2+1 && fyear == arg1) {
                                prev_date = true;
                            }
                            if (fday > arg3 && fmonth == arg2+1 && fyear == arg1) {
                                prev_date =true;
                            }


                            if(prev_date){
                                Toast.makeText(getBaseContext(), "Please do not select a date that is lower than from date.", Toast.LENGTH_LONG).show();
                            } else {
                                showDate(arg1, arg2+1, arg3);
                            }
                        }
                    }



                }
            };

    private void showDate(int year, int month, int day) {
        if(clicked.equals("from")) {
            tv_From.setText(new StringBuilder().append("From date: ").append(day).append("/")
                    .append(month).append("/").append(year));
            tv_To.setText("");
        } else if(clicked.equals("to")){
            tv_To.setText(new StringBuilder().append("To date: ").append(day).append("/")
                    .append(month).append("/").append(year));
        }
    }


    public void searchVehicle(View view) {

        if(tv_From.equals("")
                || tv_To.equals("")
                || type.equals("Select Vehicle Type")
                || company.equals("Select Vehicle Company")
                || cities.equals("Select Vehicle City")
                || state.equals("Select Vehicle State")) {
            Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_LONG).show();
        } else {

            String from = fday + "/" + fmonth + '/' + fyear;
            String to = tday + "/" + tmonth + '/' + tyear;

            searchVehicles add = new searchVehicles(this);
            add.execute(from, to, type, company, model, cities, state);
        }

    }


    public void goback(View view) {
        finish();
    }
}
