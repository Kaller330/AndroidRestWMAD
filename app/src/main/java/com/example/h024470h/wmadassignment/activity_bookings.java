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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import DTO.bookingDTO;
import DTO.showingDTO;
import Operations.CustomAlert;
import Operations.CustomAlertInterface;
import Operations.Delete;
import Operations.Get;

public class activity_bookings extends AppCompatActivity {

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;
    ArrayList<bookingDTO> bookings;
    List<String> id;
    StringBuilder builder;
    JSONArray array;
    SharedPreferences sharedPref;
    String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookings);
        builder = new StringBuilder();
        mRecyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        sharedPref = getSharedPreferences(getString(R.string.storedPreferenceName), 	Context.MODE_PRIVATE);
        username = sharedPref.getString("username", "Not logged in");
        bookings = new ArrayList<>();
        id = new ArrayList<>();
        @SuppressLint("StaticFieldLeak") AsyncTask asyncTask = new AsyncTask() {
            @SuppressLint("WrongThread")
            protected Object doInBackground(Object[] objects) {
                try {
                    URL url = new URL("http://10.0.2.2:8080/cinemaProjectNew/webresources/ws/getBookingsUser/" + username + "?");
                    System.out.println(url.toString());
                    Get getter = new Get(url,builder);
                    String res = getter.getResult();
                    //array = new JSONArray(((StringBuilder)builder).toString());
                    array = new JSONArray(res);
                    System.out.println("Array size:::" + array.length());

                } catch (Exception ex) {
                    System.out.println(ex);
                }
                                for (int i = 0; i < array.length(); i++){
                    try {
                        JSONObject showing = array.getJSONObject(i);
                        System.out.println(showing.toString());
                        Gson gson = new Gson();
                        bookingDTO bookingDTO = gson.fromJson(showing.toString(), DTO.bookingDTO.class);
                        System.out.println("tester:" + bookingDTO.getScreen().getScreenID());
                        bookings.add(bookingDTO);
                        id.add(String.valueOf(bookingDTO.getBookingID()));
                    } catch (Exception ex) {
                        System.out.println(ex);;
                    }
                }
                return null;
            }
            protected void onPostExecute(Object output) {
                mAdapter.notifyDataSetChanged();
            }
        }.execute();

        mAdapter = new bookingAdapter(bookings);
        mRecyclerView.setAdapter(mAdapter);

        final EditText txtBookingID = (EditText)findViewById(R.id.txtBookingID);
        Button btnCancelBooking= (Button)findViewById(R.id.btnCancelBooking);

        btnCancelBooking.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (!id.contains(txtBookingID.getText().toString())){
                    Toast.makeText(getApplicationContext(),"No showing booked with that ID!",Toast.LENGTH_SHORT).show();
                }else{
                    final Integer bookingID = Integer.valueOf(txtBookingID.getText().toString());
                    System.out.println(bookingID);
                    CustomAlert alert = new CustomAlert("Cancel Booking?", "Are you sure you want to cancel booking" + bookingID + "?", activity_bookings.this, "Yes", "No", new CustomAlertInterface() {
                        @Override
                        public void PositiveMethod(DialogInterface dialog, int id) {
                            AsyncTask asyncTask = new AsyncTask() {
                                @SuppressLint("WrongThread")
                                protected Object doInBackground(Object[] objects) {
                                    builder.setLength(0);

                                    try {
                                       URL url = new URL("http://10.0.2.2:8080/cinemaProjectNew/webresources/ws/deleteBooking/" + bookingID);
                                        System.out.println(url.toString());
                                        System.out.println("attempting delete");
                                        Delete delete = new Delete(url);
                                        Intent intent = new Intent(activity_bookings.this, activity_bookings.class);
                                        startActivity(intent);
                                        finish();
                                    } catch (Exception ex) {
                                        System.out.println(ex);;
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
