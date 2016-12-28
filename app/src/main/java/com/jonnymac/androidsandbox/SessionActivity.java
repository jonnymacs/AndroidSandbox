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

public class SessionActivity extends AppCompatActivity {


    private EditText email;
    private EditText password;
    private Button sign_in;
    private Button sign_up;
    private SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        setupVariables();
    }

    private void setupVariables() {
        email = (EditText) findViewById(R.id.userEmail);
        password = (EditText) findViewById(R.id.userPassword);

        sign_up = (Button) findViewById(R.id.sign_upButton);
        sign_up.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent intent = new Intent(getApplicationContext(), RegistrationActivity.class);
                startActivity(intent);
                finish();
            }
        });

        sign_in = (Button) findViewById(R.id.sign_inButton);
        sign_in.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Map<String, String> user = new HashMap<String, String>();
                user.put("email", email.getText().toString());
                user.put("password", password.getText().toString());

                Map<String, Map<String, String>> credentials = new HashMap<String, Map<String, String>>();
                credentials.put("user", user);

                String json = new GsonBuilder().create().toJson(credentials, Map.class);
                String url = "http://10.0.2.2:8080/sign_in";
                new SessionHttpOperation().execute("POST", url, json);
            }
        });

    }

    private class SessionHttpOperation extends HttpOperation {

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            String message = new String();
            if (result == null) {
                message = "Invalid Username or Password";
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            } else {
                Gson gson = new Gson();
                Map parsed_response = gson.fromJson(result, Map.class);
                mPreferences = getApplicationContext().getSharedPreferences("CurrentUser", MODE_PRIVATE);
                SharedPreferences.Editor editor = mPreferences.edit();
                editor.putString("session", new GsonBuilder().create().toJson(parsed_response.get("session"), Map.class));
                editor.commit();
                message = "Sign In Successful";
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                finish();
            }
            //TextView txt = (TextView) findViewById(R.id.output);
            //txt.setText("Executed"); // txt.setText(result);
            // might want to change "executed" for the returned string passed
            // into onPostExecute() but that is upto you
            // our async task is completed! let's take care of this activity
        }

    }

}
