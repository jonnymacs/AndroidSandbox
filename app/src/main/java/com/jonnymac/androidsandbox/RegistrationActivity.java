package com.jonnymac.androidsandbox;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {

    private EditText name;
    private EditText email;
    private EditText password;
    private EditText password_confirmation;
    private Button sign_up;
    private Button sign_in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setupVariables();
    }

    private void setupVariables() {
        name = (EditText) findViewById(R.id.userName);
        email = (EditText) findViewById(R.id.userEmail);
        password = (EditText) findViewById(R.id.userPassword);
        password_confirmation = (EditText) findViewById(R.id.userPasswordConfirmation);

        sign_up = (Button) findViewById(R.id.sign_upButton);
        sign_up.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Map<String, String> user = new HashMap<String, String>();
                user.put("name", name.getText().toString());
                user.put("email", email.getText().toString());
                user.put("password", password.getText().toString());
                user.put("password_confirmation", password_confirmation.getText().toString());

                Map<String, Map<String, String>> credentials = new HashMap<String, Map<String, String>>();
                credentials.put("user", user);

                String json = new GsonBuilder().create().toJson(credentials, Map.class);
                String url = "http://10.0.2.2:8080/register";
                new RegistrationHttpOperation().execute("POST", url, json);
            }
        });

        sign_in = (Button) findViewById(R.id.sign_inButton);
        sign_in.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent intent = new Intent(getApplicationContext(), SessionActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private class RegistrationHttpOperation extends HttpOperation {

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            String message = new String();
            message = "Invalid Entry";
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

            //TextView txt = (TextView) findViewById(R.id.output);
            //txt.setText("Executed"); // txt.setText(result);
            // might want to change "executed" for the returned string passed
            // into onPostExecute() but that is upto you
            // our async task is completed! let's take care of this activity
        }

    }
}
