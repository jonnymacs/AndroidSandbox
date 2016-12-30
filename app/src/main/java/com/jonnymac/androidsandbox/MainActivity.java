package com.jonnymac.androidsandbox;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    private SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPreferences = getSharedPreferences("CurrentUser", MODE_PRIVATE);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mPreferences.contains("user")) {
            Intent intent = new Intent(this, SportsBookActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void get_sign_in(View view) {
        Intent intent = new Intent(this, SessionActivity.class);
        startActivity(intent);
    }

    public void get_sign_up(View view) {
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }
}
