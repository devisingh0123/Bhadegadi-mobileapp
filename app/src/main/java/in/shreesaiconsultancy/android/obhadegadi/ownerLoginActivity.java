package in.shreesaiconsultancy.android.obhadegadi;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

        isStoragePermissionGranted();

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


    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.v("permission","Permission is granted");
                return true;
            } else {
                Log.v("permission","Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else {
            Log.v("permission","Permission is granted");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        try {
            Log.d("Request Code", Integer.toString(requestCode));
            Log.d("grant Code", (grantResults.toString()));
            switch (requestCode) {
                case 1: {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        Log.v("permission", "Permission: " + permissions[0] + "was " + grantResults[0]);
                    } else {
                        isStoragePermissionGranted();
                    }
                }
            }
        } catch (Exception e) {
            Log.d("error","erroe");
        }
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
