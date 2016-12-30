package com.jonnymac.androidsandbox;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by jonnymac on 12/27/16.
 */

public class HttpOperation extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... params) {

        try {
            String method = params[0];
            URL url = new URL(params[1]);
            String json_out = params[2];
            int content_length = (method == "GET" ) ? 0 : json_out.length();

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            String response = new String();

            try {
                urlConnection.setRequestMethod(method);
                urlConnection.setRequestProperty("Content-Length", Integer.toString(content_length));
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("Accept", "application/json");

                switch(method) {
                    case "POST":
                    case "PUT":
                    case "PATCH":
                        urlConnection.setDoOutput(true);
                        urlConnection.setFixedLengthStreamingMode(content_length);

                        OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
                        writeStream(out, json_out);

                        InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                        response = readStream(in);
                        break;
                    case "GET":
                        //TODO handle json responses
                        urlConnection.connect();
                        urlConnection.getResponseCode();
                        break;
                }

            } catch (IOException e) {
                if (urlConnection.getResponseCode() == 401) {
                    response = null;
                } else {
                    throw new RuntimeException(e);
                }

            } finally {
                urlConnection.disconnect();
                return response;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void onPostExecute(String result) {
        //super.onPostExecute(result);
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected void onProgressUpdate(Void... values) {
    }

    private void writeStream(OutputStream os, String json_out) {
        try {
            os.write(json_out.getBytes("UTF-8"));
            os.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
            //Log.e(TAG, "IOException", e);
        }
    }

    private static String readStream(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
            //Log.e(TAG, "IOException", e);
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                //Log.e(TAG, "IOException", e);
            }
        }
        return sb.toString();
    }

}