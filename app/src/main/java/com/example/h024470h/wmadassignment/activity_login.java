package com.example.h024470h.wmadassignment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import DTO.userDTO;
import Operations.Post;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;


public class activity_login extends AppCompatActivity {

    String baseUrl = "http://localhost:8080/cinemaProjectNew/webresources/ws/";  // This is the API base URL (GitHub API)
    String url;
    InputStream inputStream;
    String ResponseData;
    final StringBuilder builder = new StringBuilder();
    StringBuilder sb = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText txtUsername = (EditText)findViewById(R.id.txtUsername);
        final EditText txtPassword = (EditText)findViewById(R.id.txtPassword);

        final TextView txtCurrentUser = (TextView)findViewById(R.id.txtCurrentUser);
        final TextView activeUser = (TextView)findViewById(R.id.txtActiveUser);


        Button button= (Button)findViewById(R.id.btnLogin);
        button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view)  {

                @SuppressLint("StaticFieldLeak") AsyncTask asyncTask = new AsyncTask() {
                    String username = txtUsername.getText().toString();
                    String password = txtPassword.getText().toString();
                    String urlString;
                    @SuppressLint("WrongThread")
                    @Override
                    protected Object doInBackground(Object[] objects) {

                        String hashpass;

                        try {
                            byte[] hash = MessageDigest.getInstance("SHA-256").digest(password.getBytes(StandardCharsets.UTF_8));
                            hashpass = Base64.getEncoder().encodeToString(hash);
                            System.out.println("Hashed." + hashpass);
                        } catch (NoSuchAlgorithmException ex) {
                            return null;
                        }

                        URL url = null;
                        try {
                            //url = new URL("http://10.0.2.2:8080/cinemaProjectNew/webresources/ws/login/" + username + "/" + hashpass + "?");
                            url = new URL("http://10.0.2.2:8080/cinemaProjectNew/webresources/ws/validate/");
                            urlString = url.toString();

                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                        System.out.println(url.toString());

                        userDTO sendUser = new userDTO(username, hashpass,"","","","","","",false);

                        try{
                            Gson gson = new Gson();
                            String jsonString = gson.toJson(sendUser);
                            Post post = new Post(url,jsonString, builder);
                            String res = post.getResponseData();
                            System.out.println(res);


                            userDTO user = gson.fromJson(res, userDTO.class);
                            System.out.println(user.getUsername() + " found user.");



                            SharedPreferences sharedPref =	getSharedPreferences(getString(R.string.storedPreferenceName), 	Context.MODE_PRIVATE);
                            SharedPreferences.Editor prefsEditor= sharedPref.edit();
                            prefsEditor.putString("username", user.getUsername());
                            prefsEditor.putString("hashedPass", user.getPassword());
                            prefsEditor.putString("address1",user.getAddress1());
                            prefsEditor.putString("address2", user.getAddress2());
                            prefsEditor.putString("town", user.getTown());
                            prefsEditor.putString("county", user.getCounty());
                            prefsEditor.putString("postcode",user.getPostcode());
                            prefsEditor.putString("regdate", user.getRegdate());
                            prefsEditor.putBoolean("isAdmin",user.getIsAdmin());
                            prefsEditor.apply();


                            String retrieved = sharedPref.getString("username", "failed");
                            txtCurrentUser.setText(retrieved);

                            startActivity(new Intent(activity_login.this, MainActivity.class));

                        }catch(Exception ex){

                            Log.d("FetchError", ex.toString());
                            txtCurrentUser.setText("User not found!");
                            txtCurrentUser.setTextColor(Color.RED);
                        }
                        return null;
                    }


                }.execute();
            }
        });
    }
}
