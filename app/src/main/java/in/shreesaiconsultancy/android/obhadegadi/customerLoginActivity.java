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

public class customerLoginActivity extends AppCompatActivity {

    EditText phone, password;
    TextView appName, page_title, registerText;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_login);

        appName = (TextView) findViewById(R.id.app_name);
        page_title = (TextView) findViewById(R.id.tv_page_title);
        registerText = (TextView) findViewById(R.id.tv_register_text);

        register = (Button) findViewById(R.id.btn_register);

        Typeface comfortaa = Typeface.createFromAsset(getAssets(), "fonts/Comfortaa-Bold.ttf");
        appName.setTypeface(comfortaa);
//        page_title.setTypeface(comfortaa);
//        registerText.setTypeface(comfortaa);
//        register.setTypeface(comfortaa);

        phone = (EditText) findViewById(R.id.et_phone);
        password = (EditText) findViewById(R.id.et_password);


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

    public void register(View view) {
        Intent intent = new Intent(this, cutomerRegisterActivity.class);
        startActivity(intent);
    }


}
