package com.example.h024470h.wmadassignment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import DTO.bookingDTO;
import DTO.screenDTO;
import DTO.showingDTO;
import DTO.userDTO;
import Operations.CustomAlert;
import Operations.CustomAlertInterface;
import Operations.Get;
import Operations.Put;

public class activity_showings extends AppCompatActivity{

    StringBuilder builder;
    JSONArray array;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;
    ArrayList<showingDTO> showings;
    List<String> id;
    public SharedPreferences sharedPref;
    String username,password,address1,address2,town,county,postcode,regdate;
    Boolean isAdmin;
    userDTO user;
    Boolean loginCheck = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showings);
        builder = new StringBuilder();


        sharedPref = getSharedPreferences(getString(R.string.storedPreferenceName), 	Context.MODE_PRIVATE);


        username = sharedPref.getString("username", "Not logged in");
        password = sharedPref.getString("hashedPass", "Not logged in");
        address1 = sharedPref.getString("address1", "Not logged in");
        address2 = sharedPref.getString("address2", "Not logged in");
        town = sharedPref.getString("town", "Not logged in");
        county = sharedPref.getString("county", "Not logged in");
        postcode = sharedPref.getString("postcode", "Not logged in");
        address2 = sharedPref.getString("username", "Not logged in");
        regdate =  sharedPref.getString("regdate", "Not logged in");
        isAdmin = sharedPref.getBoolean("isAdmin", false);

        user = new userDTO(username, password,address1,address2,town,county,postcode,regdate,isAdmin);

        mRecyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (username == "Not logged in"){
            loginCheck = true;
        }

        Button btnDeleteShowing = (Button)findViewById(R.id.btnDeleteShowing);

        System.out.println("Admin check");
        if (isAdmin != true){
            btnDeleteShowing.setVisibility(View.GONE);
        }

        showings = new ArrayList<>();
        id = new ArrayList<>();
        @SuppressLint("StaticFieldLeak") AsyncTask asyncTask = new AsyncTask() {
            @SuppressLint("WrongThread")
            protected Object doInBackground(Object[] objects) {
                try{
                    URL url = new URL("http://10.0.2.2:8080/cinemaProjectNew/webresources/ws/getShowings/");
                    System.out.println(url.toString());
                    //getData(url,builder);
                    Get getter = new Get(url,builder);
                    String res = getter.getResult();
                    //array = new JSONArray(((StringBuilder)builder).toString());
                    array = new JSONArray(res);
                    System.out.println("Array size:::" + array.length());
                }catch(Exception ex){
                    System.out.println(ex);
                }
                for (int i = 0; i < array.length(); i++){
                    try {
                        JSONObject showing = array.getJSONObject(i);
                        System.out.println(showing.toString());
                        Gson gson = new Gson();
                        showingDTO showingDTO = gson.fromJson(showing.toString(), DTO.showingDTO.class);
                        System.out.println("tester:" + showingDTO.getScreen().getScreenID());
                        showings.add(showingDTO);
                        id.add(String.valueOf(showingDTO.getShowingID()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }
            protected void onPostExecute(Object output) {
                mAdapter.notifyDataSetChanged();
            }
        }.execute();

        mAdapter = new MainAdapter(showings);
        mRecyclerView.setAdapter(mAdapter);

        final EditText txtShowingID = (EditText)findViewById(R.id.txtShowingID);
        Button btnBookShowing = (Button)findViewById(R.id.btnBookShowing);

        btnBookShowing.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (loginCheck == true){
                    Toast.makeText(getApplicationContext(),"Please login to book.",Toast.LENGTH_SHORT).show();
                }else{
                    if (!id.contains(txtShowingID.getText().toString())){
                        Toast.makeText(getApplicationContext(),"No showing exists that ID!",Toast.LENGTH_SHORT).show();
                    }else{
                        final Integer showingID = Integer.valueOf(txtShowingID.getText().toString());
                        //createAlert("Make booking?", "Are you sure you want to book showing " + txtShowingID.getText().toString(),showingID);
                        CustomAlert alert = new CustomAlert("Make booking?","Are you sure you want to book showing " + showingID + "?", activity_showings.this ,"Yes","No", new CustomAlertInterface() {

                            @Override
                            public void PositiveMethod(final DialogInterface dialog, final int id) {
                                AsyncTask asyncTask = new AsyncTask() {
                                    @SuppressLint("WrongThread")
                                    protected Object doInBackground(Object[] objects) {
                                        builder.setLength(0);


                                        try {
                                            URL url = new URL("http://10.0.2.2:8080/cinemaProjectNew/webresources/ws/getShowing/" + showingID + "?");
                                            System.out.println(url.toString());
                                            System.out.println("Looking for item by ID");
                                            Get getter = new Get(url,builder);
                                            String res = getter.getResult();
                                            System.out.println(builder.toString());
                                            System.out.println("attempting get of showing from ID");
                                            Gson gson = new Gson();
                                            showingDTO showingDTO = gson.fromJson(builder.toString(), DTO.showingDTO.class);
                                            System.out.println("tester:" + showingDTO.getScreen().getScreenID());
                                            System.out.println("attempting make booking");
                                            url = new URL("http://10.0.2.2:8080/cinemaProjectNew/webresources/ws/makeBooking/");
                                            System.out.println(url);
                                            screenDTO screen = new screenDTO(showingDTO.getScreen().getScreenID(), null);
                                            bookingDTO booking = new bookingDTO(0,user,showingDTO,screen);
                                            System.out.println(user.getUsername());
                                            String jsonString = gson.toJson(booking);
                                            Put put = new Put(url,jsonString,builder);
                                            Intent intent = new Intent(activity_showings.this, activity_bookings.class);
                                            startActivity(intent);
                                            finish();
                                        } catch (Exception ex) {
                                            System.out.println(ex);
                                        }

                                        return null;
                                    }
                                }.execute();
                            }

                            @Override
                            public void NegativeMethod(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                    }
                }
            }
        });

        btnDeleteShowing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final int showingID = Integer.valueOf(txtShowingID.getText().toString());
                if (!id.contains(txtShowingID.getText().toString())){
                    Toast.makeText(getApplicationContext(),"No showing exists that ID!",Toast.LENGTH_SHORT).show();
                }else{
                    CustomAlert alert = new CustomAlert("Cancel showing?", "Are you sure you want to book showing " + showingID + "?", activity_showings.this, "Yes", "No", new CustomAlertInterface() {

                        @Override
                        public void PositiveMethod(final DialogInterface dialog, final int id) {
                            AsyncTask AsyncTask = new AsyncTask() {
                                @SuppressLint("WrongThread")
                                @Override
                                protected Object doInBackground(Object[] objects) {
                                    try{
                                        URL url = new URL( "http://10.0.2.2:8080/cinemaProjectNew/webresources/ws/deleteShowing/" + showingID);
                                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                                        connection.setRequestMethod("DELETE");
                                        int responseCode = connection.getResponseCode();
                                        System.out.println(responseCode);
                                        Toast.makeText(getApplicationContext(),"Showing deleted.",Toast.LENGTH_SHORT).show();
                                    }catch (Exception ex){
                                        System.out.println(ex);
                                    }
                                    return null;
                                }
                            }.execute();
                        }

                        @Override
                        public void NegativeMethod(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
                }

            }

        });
    }
}
