package com.example.h024470h.wmadassignment;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import DTO.filmDTO;
import DTO.screenDTO;
import DTO.showingDTO;
import DTO.userDTO;
import Operations.CustomAlert;
import Operations.CustomAlertInterface;
import Operations.Delete;
import Operations.Get;
import Operations.Put;

public class activity_admin extends AppCompatActivity {
    JSONArray array;
    JSONArray screenArray;
    JSONArray userArray;
    ArrayList<filmDTO> showings;
    ArrayAdapter<filmDTO> adapter;
    ArrayList<screenDTO> screens;
    ArrayList<userDTO> users;
    ArrayAdapter<userDTO> userAdapter;
    ArrayAdapter<screenDTO> screenAdapter;
    ArrayAdapter<CharSequence> bbfcAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);


        array = new JSONArray();
        screenArray = new JSONArray();
        userArray = new JSONArray();
        showings = new ArrayList<>();
        screens = new ArrayList<>();
        users = new ArrayList<>();
        bbfcAdapter = ArrayAdapter.createFromResource(this, R.array.admin_bbfcArray, android.R.layout.simple_spinner_item);
        adapter = new ArrayAdapter<filmDTO>(activity_admin.this, android.R.layout.simple_spinner_item, showings);
        userAdapter = new ArrayAdapter<userDTO>(activity_admin.this, android.R.layout.simple_spinner_item, users);
        screenAdapter = new ArrayAdapter<screenDTO>(this, android.R.layout.simple_spinner_dropdown_item, screens);

        bbfcAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        screenAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final Spinner cbUsers = (Spinner)findViewById(R.id.cbUsers);
        final Spinner cbFilms = (Spinner) findViewById(R.id.cbFilms);
        final Spinner cbBbfc = (Spinner) findViewById(R.id.cbBbfc);
        final Spinner cbScreens = (Spinner) findViewById(R.id.cbScreens);
        cbFilms.setAdapter(adapter);
        cbBbfc.setAdapter(bbfcAdapter);
        cbUsers.setAdapter(userAdapter);
        cbScreens.setAdapter(screenAdapter);

        final AsyncTask asyncTask = new AsyncTask() {
            @SuppressLint("WrongThread")
            @Override
            protected Object doInBackground(Object[] objects) {
                StringBuilder filmBuilder = new StringBuilder();
                StringBuilder screenBuilder = new StringBuilder();
                StringBuilder userBuilder = new StringBuilder();

                try {
                    URL url = new URL("http://10.0.2.2:8080/cinemaProjectNew/webresources/ws/getFilms/");
                    Get filmGetter = new Get(url, filmBuilder);
                    String res = filmGetter.getResult();
                    array = new JSONArray(res);
                    System.out.println("Array size:::" + array.length());
                } catch (Exception ex) {
                    System.out.println(ex);
                }
                for (int i = 0; i < array.length(); i++) {
                    try {
                        JSONObject showing = array.getJSONObject(i);
                        System.out.println(showing.toString());
                        Gson gson = new Gson();
                        filmDTO filmDTO = gson.fromJson(showing.toString(), DTO.filmDTO.class);
                        System.out.println("tester:" + filmDTO.getTitle());
                        showings.add(filmDTO);
                        System.out.println("added: " + filmDTO.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                try {
                    URL url = new URL("http://10.0.2.2:8080/cinemaProjectNew/webresources/ws/getScreens/");
                    Get screenGetter = new Get(url, screenBuilder);
                    String res = screenGetter.getResult();
                    screenArray = new JSONArray(res);
                    System.out.println("Array size:::" + screenArray.length());
                } catch (Exception ex) {
                    System.out.println(ex);
                }
                for (int i = 0; i < screenArray.length(); i++) {
                    try {
                        JSONObject screenObj = screenArray.getJSONObject(i);
                        System.out.println(screenObj.toString());
                        Gson gson = new Gson();
                        screenDTO screenDTO = gson.fromJson(screenObj.toString(), DTO.screenDTO.class);
                        System.out.println("tester:" + screenDTO.getScreenID());
                        screens.add(screenDTO);
                        System.out.println("added: " + screenDTO.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                try {
                    URL url = new URL("http://10.0.2.2:8080/cinemaProjectNew/webresources/ws/find/");
                    Get userGetter = new Get(url, userBuilder);
                    String res = userGetter.getResult();
                    userArray = new JSONArray(res);
                    System.out.println("Array size:::" + userArray.length());
                } catch (Exception ex) {
                    System.out.println("Users broke");
                    System.out.println(ex);
                }
                for (int i = 0; i < userArray.length(); i++) {
                    try {
                        JSONObject userObj = userArray.getJSONObject(i);
                        System.out.println(userObj.toString());
                        Gson gson = new Gson();
                        userDTO userDTO = gson.fromJson(userObj.toString(), DTO.userDTO.class);
                        System.out.println("tester:" + userDTO.getUsername());
                        users.add(userDTO);
                        System.out.println("added: " + userDTO.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                return null;
            }

            protected void onPostExecute(Object output) {
                adapter.notifyDataSetChanged();
                screenAdapter.notifyDataSetChanged();
                userAdapter.notifyDataSetChanged();
                System.out.println("Done notifying");
            }
        }.execute();


        //Button code to submit new showing
        Button btnAddShowing = (Button) findViewById(R.id.btnAddNewShowing);
        final EditText txtTimeslot = (EditText) findViewById(R.id.txtTimeslot);
        btnAddShowing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AsyncTask submitNewShowing = new AsyncTask() {
                    @SuppressLint("WrongThread")
                    @Override
                    protected Object doInBackground(Object[] objects) {
                        try {
                            StringBuilder builder = new StringBuilder();
                            screenDTO screen = (screenDTO) cbScreens.getSelectedItem();
                            filmDTO film = (filmDTO) cbFilms.getSelectedItem();
                            showingDTO showing = new showingDTO(0, screen, film, txtTimeslot.getText().toString());
                            Gson gson = new Gson();
                            String jsonString = gson.toJson(showing);
                            URL url = new URL("http://10.0.2.2:8080/cinemaProjectNew/webresources/ws/addShowing/");
                            Put put = new Put(url, jsonString, builder);
                            System.out.println("Done adding showing");
                        } catch (Exception ex) {
                            System.out.println(ex);
                        }

                        return null;
                    }
                }.execute();
            }
        });


        final EditText txtTitle = (EditText) findViewById(R.id.txtTitle);
        final EditText txtRuntime = (EditText) findViewById(R.id.txtRuntime);
        final EditText txtCast = (EditText) findViewById(R.id.txtCast);

        Button btnAddFilm = (Button) findViewById(R.id.btnAddNewFilm);
        btnAddFilm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Adding Film");
                AsyncTask submitNewFilm = new AsyncTask() {
                    @SuppressLint("WrongThread")
                    @Override
                    protected Object doInBackground(Object[] objects) {
                        try {
                            URL url = new URL("http://10.0.2.2:8080/cinemaProjectNew/webresources/ws/addFilm/");
                            StringBuilder builder = new StringBuilder();


                            filmDTO film = new filmDTO(0, txtTitle.getText().toString(), cbBbfc.getSelectedItem().toString(), txtRuntime.getText().toString(), txtCast.getText().toString());
                            Gson gson = new Gson();
                            String jsonString = gson.toJson(film);
                            Put put = new Put(url, jsonString, builder);
                        } catch (Exception ex) {
                            System.out.println(ex);
                        }
                        return null;
                    }

                    protected void onPostExecute(Object output) {
                        adapter.notifyDataSetChanged();
                        screenAdapter.notifyDataSetChanged();
                        Intent intent = new Intent(activity_admin.this, activity_admin.class);
                        startActivity(intent);
                        finish();
                    }
                }.execute();
            }
        });

        Button btnDeleteFilm = (Button) findViewById(R.id.btnDeleteFilm);
        btnDeleteFilm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final filmDTO film = (filmDTO) cbFilms.getSelectedItem();
                final int filmID = film.getFilmID();
                CustomAlert alert = new CustomAlert("Delete Film?", "Are you sure you want to delete film " + filmID + "?", activity_admin.this, "Yes", "No", new CustomAlertInterface() {
                    @Override
                    public void PositiveMethod(DialogInterface dialog, int id) {
                        @SuppressLint("StaticFieldLeak") AsyncTask deleteFilm = new AsyncTask() {
                            @SuppressLint("WrongThread")
                            @Override
                            protected Object doInBackground(Object[] objects) {
                                try {
                                    URL url = new URL("http://10.0.2.2:8080/cinemaProjectNew/webresources/ws/deleteFilm/" + filmID);
                                    Delete delete = new Delete(url);
                                } catch (Exception ex) {
                                    System.out.println(ex);
                                }
                                return null;
                            }

                            protected void onPostExecute(Object output) {
                                adapter.notifyDataSetChanged();
                                screenAdapter.notifyDataSetChanged();
                                Intent intent = new Intent(activity_admin.this, activity_admin.class);
                                startActivity(intent);
                                finish();
                            }
                        }.execute();
                    }
                    @Override
                    public void NegativeMethod(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
            }
        });

        Button btnElevateUser = (Button)findViewById(R.id.btnElevateUser);
        btnElevateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final userDTO user = (userDTO)cbUsers.getSelectedItem();
                final String username = user.getUsername();
                if (user.getIsAdmin() == true){
                    Toast.makeText(getApplicationContext(),"That user is already an admin!",Toast.LENGTH_SHORT).show();
                }else{
                    CustomAlert alert = new CustomAlert("Elevate User?", "Are you sure you want to make " + username + " an admin?", activity_admin.this, "Yes", "No", new CustomAlertInterface() {
                        @Override
                        public void PositiveMethod(DialogInterface dialog, int id) {
                            @SuppressLint("StaticFieldLeak") AsyncTask elevateUser = new AsyncTask() {
                                @SuppressLint("WrongThread")
                                @Override
                                protected Object doInBackground(Object[] objects) {
                                    try{
                                        StringBuilder elevateBuilder = new StringBuilder();
                                        URL url = new URL("http://10.0.2.2:8080/cinemaProjectNew/webresources/ws/elevateUser/");
                                        System.out.println("Attempting elevate: " + url);
                                        Gson gson = new Gson();
                                        String jsonString = gson.toJson(user);
                                        Put put = new Put(url, jsonString,elevateBuilder);
                                    }catch(Exception ex){
                                        System.out.println(ex);
                                    }
                                    return null;
                                }protected void onPostExecute(Object output) {
                                    adapter.notifyDataSetChanged();
                                    screenAdapter.notifyDataSetChanged();
                                    Intent intent = new Intent(activity_admin.this, activity_admin.class);
                                    startActivity(intent);
                                    finish();
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
