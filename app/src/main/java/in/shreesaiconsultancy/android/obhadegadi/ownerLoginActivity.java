package in.shreesaiconsultancy.android.obhadegadi;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ownerLoginActivity extends AppCompatActivity {

    TextView pageTitle;
    EditText phone, password;
    Button signin, register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_login);

        pageTitle = (TextView) findViewById(R.id.tv_page_title);
        signin = (Button) findViewById(R.id.btn_signin);
        register = (Button) findViewById(R.id.btn_register);
        phone = (EditText) findViewById(R.id.et_phone);
        password = (EditText) findViewById(R.id.et_password);


        Typeface montserrat = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Light.otf");
        Typeface montserratBold = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-ExtraBold.otf");

        pageTitle.setTypeface(montserrat);
        signin.setTypeface(montserrat);
        register.setTypeface(montserratBold);

    }


    public void register(View view)
    {
        Intent intent = new Intent(this, ownerRegistrationActivity.class);
        startActivity(intent);
    }

    public void login(View view) {
        String sphone = phone.getText().toString();
        String spassword = password.getText().toString();

        String phonePattern = "^[789]\\d{9}$";





        if (sphone.length() == 0 || spassword.length() == 0) {
            Toast.makeText(this, "Please fill the form.",
                    Toast.LENGTH_LONG).show();
        } else {
            String type = "login";
            login log = new login(this);
            log.execute(type, sphone, spassword);
        }



    }


}
