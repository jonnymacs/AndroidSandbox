package com.jonnymac.androidsandbox;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.Map;

public class SportsBookActivity extends AppCompatActivity {

    private SharedPreferences mPreferences;
    private Button sign_out;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sports_book);

        setupVariables();
    }

    private void setupVariables() {
        Gson gson = new Gson();

        mPreferences = getApplicationContext().getSharedPreferences("CurrentUser", MODE_PRIVATE);
        Map user = gson.fromJson(mPreferences.getString("user", null), Map.class);

        TextView display_welcome = (TextView) findViewById (R.id.display_welcome);
        display_welcome.setText(getString(R.string.display_username, user.get("name")));

        sign_out = (Button) findViewById(R.id.sign_outButton);
        sign_out.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent intent = new Intent(getApplicationContext(), SessionActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


}
