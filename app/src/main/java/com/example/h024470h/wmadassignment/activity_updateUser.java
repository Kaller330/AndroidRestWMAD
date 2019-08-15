package com.example.h024470h.wmadassignment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import DTO.userDTO;
import Operations.Put;

public class activity_updateUser extends AppCompatActivity {
    public SharedPreferences sharedPref;
    String username, password, address1, address2, town,county,postcode,regdate;
    Boolean isAdmin, autoLog;
    URL url;
    StringBuilder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);

        TextView lblLoggedInAs = (TextView)findViewById(R.id.lblLoggedInAs);
        TextView lblAddressLine1 = (TextView)findViewById(R.id.lblAddressLine1);
        TextView lblAddressLine2 = (TextView)findViewById(R.id.lblAddressLine2);
        TextView lblTown = (TextView)findViewById(R.id.lblTown);
        TextView lblCounty = (TextView)findViewById(R.id.lblCounty);
        TextView lblPostcode = (TextView)findViewById(R.id.lblPostcode);
        Button btnUpdateUser = (Button)findViewById(R.id.btnUpdateUser);

        final EditText txtAddressLine1 = (EditText)findViewById(R.id.txtAddressLine1Edit);
        final EditText txtAddressLine2 = (EditText)findViewById(R.id.txtAddressLine2Edit);
        final EditText txtTown = (EditText)findViewById(R.id.txtTownEdit);
        final EditText txtCounty = (EditText)findViewById(R.id.txtCountyEdit);
        final EditText txtPostcode = (EditText)findViewById(R.id.txtPostcodeEdit);

        sharedPref = getSharedPreferences(getString(R.string.storedPreferenceName), 	Context.MODE_PRIVATE);

        if(sharedPref.contains("username")){
            username = sharedPref.getString("username", "Not logged in");
            password = sharedPref.getString("hashedPass", "Not logged in");
            address1 = sharedPref.getString("address1", "Not logged in");
            address2 = sharedPref.getString("address2", "Not logged in");
            town = sharedPref.getString("town", "Not logged in");
            county = sharedPref.getString("county", "Not logged in");
            postcode = sharedPref.getString("postcode", "Not logged in");
            regdate = sharedPref.getString("regdate", "Not logged in");
            isAdmin = sharedPref.getBoolean("isAdmin", false);
            autoLog = sharedPref.getBoolean("autoLog",false);

            lblLoggedInAs.setText("Editing user: " + username);
            lblAddressLine1.setText(address1);
            lblAddressLine2.setText(address2);
            lblTown.setText(town);
            lblCounty.setText(county);
            lblPostcode.setText(postcode);

            btnUpdateUser.setOnClickListener(new View.OnClickListener(){


                @Override
                public void onClick(View view) {
                    builder = new StringBuilder();
                    System.out.println("button clicked?");
                    @SuppressLint("StaticFieldLeak") AsyncTask asyncTask = new AsyncTask(){
                        @SuppressLint("WrongThread")
                        @Override
                        protected Object doInBackground(Object[] objects) {

                            address1 = txtAddressLine1.getText().toString();
                            address2 = txtAddressLine2.getText().toString();
                            town = txtTown.getText().toString();
                            county = txtCounty.getText().toString();
                            postcode = txtPostcode.getText().toString();

                            userDTO sendUser = new userDTO(username, password,address1,address2,town,county,postcode,regdate,isAdmin);

                            try {
                                url = new URL("http://10.0.2.2:8080/cinemaProjectNew/webresources/ws/edit/");
                                Gson gson = new Gson();
                                String jsonString = gson.toJson(sendUser);
                                Put put = new Put(url,jsonString,builder);
                            } catch (Exception ex) {
                                System.out.println(ex);
                            }
                            System.out.println("Sending data");
                            SharedPreferences sharedPref =	getSharedPreferences(getString(R.string.storedPreferenceName), 	Context.MODE_PRIVATE);
                            SharedPreferences.Editor prefsEditor= sharedPref.edit();
                            prefsEditor.putString("username", username);
                            prefsEditor.putString("hashedPass", password);
                            prefsEditor.putString("address1",address1);
                            prefsEditor.putString("address2", address2);
                            prefsEditor.putString("town", town);
                            prefsEditor.putString("county", county);
                            prefsEditor.putString("postcode",postcode);
                            prefsEditor.putString("regdate", regdate);
                            prefsEditor.putBoolean("isAdmin",isAdmin);
                            prefsEditor.apply();
                            return null;
                        }
                    }.execute();

                    Intent intent = new Intent(activity_updateUser.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }

            });
        }





    }

}
