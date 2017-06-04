package com.example.android.slides;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class ownerHistoryActivity extends AppCompatActivity {

    private sessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_history);
    }

    public void logout(View view)
    {
        session = new sessionManager(this);
        session.setFirstTimeLaunch(true);
        Intent intent = new Intent(ownerHistoryActivity.this, getStartedActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
