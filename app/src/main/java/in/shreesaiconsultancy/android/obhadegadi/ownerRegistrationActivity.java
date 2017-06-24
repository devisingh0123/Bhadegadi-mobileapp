package in.shreesaiconsultancy.android.obhadegadi;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class ownerRegistrationActivity extends AppCompatActivity {

        TextView pageTitle, signinText;
        EditText fullName, email, phone, password, cpassword;
        Button register, signinBtn;
        //    SharedPreferences sharedpreferences;
        ProgressBar progressBar;
        AlertDialog alertDialog;

        public static final String MyPREFERENCES = "userInfo";
        public static final String Name = "name";
        public static final String Phone = "email";
        public static final String Email = "phone";

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_owner_registration);


//        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);


            pageTitle = (TextView) findViewById(R.id.tv_title);
            fullName = (EditText) findViewById(R.id.et_name);
            email = (EditText) findViewById(R.id.et_email);
            phone = (EditText) findViewById(R.id.et_phone);
            password = (EditText) findViewById(R.id.et_password);
            cpassword = (EditText) findViewById(R.id.et_cpassword);
            register = (Button) findViewById(R.id.btn_register);
            signinText = (TextView) findViewById(R.id.tv_signin_text);
            signinBtn = (Button) findViewById(R.id.btn_signin);

            Typeface montserrat = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Light.otf");
            Typeface montserratBold = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-ExtraBold.otf");

            pageTitle.setTypeface(montserrat);
            register.setTypeface(montserrat);
            signinText.setTypeface(montserrat);
            signinBtn.setTypeface(montserratBold);

        }

        public void onRegister(View view) {


            String sname = fullName.getText().toString();
            String semail = email.getText().toString();
            String sphone = phone.getText().toString();
            String spassword = password.getText().toString();
            String scpassword = cpassword.getText().toString();

            String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
            String phonePattern = "^[789]\\d{9}$";


            if (sname.length() == 0 || sphone.length() == 0 || spassword.length() == 0 || scpassword.length() == 0) {
                Toast.makeText(ownerRegistrationActivity.this, "Please fill the form.",
                        Toast.LENGTH_LONG).show();
            } else if (spassword.equals(scpassword)) {
                if (spassword.length() >= 6) {
                    if (sphone.matches(phonePattern)) {
                        if (semail.matches(emailPattern)) {
                            String type = "register";
                            register reg = new register(this);
                            reg.execute(type, sname, semail, sphone, spassword, "operator");

                        } else {
                            Toast.makeText(ownerRegistrationActivity.this, "Invalid email address", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(ownerRegistrationActivity.this, "Invalid Phone Number", Toast.LENGTH_LONG).show();

                    }

                } else {
                    Toast.makeText(ownerRegistrationActivity.this,"Password is too short!", Toast.LENGTH_LONG).show();

                }

            } else {

                Toast.makeText(ownerRegistrationActivity.this, "Password don't match!",
                        Toast.LENGTH_LONG).show();

            }


        }


        public void loginClicked(View view) {




            Intent intent = new Intent(ownerRegistrationActivity.this, ownerLoginActivity.class);
            startActivity(intent);
        }
    }

