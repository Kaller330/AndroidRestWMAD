package com.example.h024470h.wmadassignment;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import DTO.userDTO;
import Operations.CustomAlert;
import Operations.CustomAlertInterface;
import Operations.Put;

public class activity_register extends AppCompatActivity {

    URL url;
    StringBuilder builder = new StringBuilder();

    String username,password,address1,address2,town,county,postcode,regdate;
    Boolean isAdmin = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button button= (Button)findViewById(R.id.btnRegister);
        button.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View view) {

                final TextView txtUsername = (TextView)findViewById(R.id.txtUsername) ;
                final TextView txtPassword = (TextView)findViewById(R.id.txtPassword);
                final TextView txtConfPwd = (TextView)findViewById(R.id.txtConfPwd);
                final TextView txtAddressLine1= (TextView)findViewById(R.id.txtAddressLine1Edit);
                final TextView txtAddressLine2 = (TextView)findViewById(R.id.lblAddressLine2);
                final TextView txtTown = (TextView)findViewById(R.id.txtTownEdit);
                final TextView txtCounty = (TextView)findViewById(R.id.txtCountyEdit);
                final TextView txtPostcode = (TextView)findViewById(R.id.txtPostcodeEdit);
                final TextView txtDatebirth = (TextView)findViewById(R.id.txtDatebirth);


                validator validator = new validator();

                boolean usernamev,passwordv,address1v,address2v,townv,countyv,postcodev,dobv;
                boolean errorFlag = false;



                if(usernamev = validator.checkUsername(txtUsername.getText().toString()) == false){
                    txtUsername.setBackgroundResource(R.drawable.alert_border);
                    errorFlag = true;
                }if (passwordv = validator.checkPassword(txtConfPwd.getText().toString(), txtPassword.getText().toString()) == false) {
                    txtPassword.setBackgroundResource(R.drawable.alert_border);
                    txtConfPwd.setBackgroundResource(R.drawable.alert_border);
                    errorFlag = true;
                }if (address1v = validator.checkAddressLine1(txtAddressLine1.getText().toString()) == false){
                    txtAddressLine1.setBackgroundResource(R.drawable.alert_border);
                    errorFlag = true;
                }if (address2v = validator.checkAddressLine2(txtAddressLine2.getText().toString()) == false){
                    txtAddressLine2.setBackgroundResource(R.drawable.alert_border);
                    errorFlag = true;
                }if (townv = validator.checkTown(txtTown.getText().toString()) == false){
                    txtTown.setBackgroundResource(R.drawable.alert_border);
                    errorFlag = true;
                }if (countyv = validator.checkCounty(txtCounty.getText().toString()) == false){
                    txtCounty.setBackgroundResource(R.drawable.alert_border);
                    errorFlag = true;
                }if (postcodev= validator.checkPostcode(txtPostcode.getText().toString()) == false){
                    txtPostcode.setBackgroundResource(R.drawable.alert_border);
                    errorFlag = true;
                }if (dobv = validator.checkDatebirth(txtDatebirth.getText().toString()) == false){
                    txtDatebirth.setBackgroundResource(R.drawable.alert_border);
                    errorFlag = true;
                }

                if (errorFlag == true){
                    CustomAlert alert = new CustomAlert("Validator","Please check highlighted fields", activity_register.this ,"Ok", new CustomAlertInterface() {

                                @Override
                                public void PositiveMethod(final DialogInterface dialog, final int id) {
                                    dialog.cancel();
                                }

                                @Override
                                public void NegativeMethod(DialogInterface dialog, int id) {
                                }
                            });
                    //CustomAlert alert = new CustomAlert("Validator", "Please check highlighted fields", activity_register.this);
                    errorFlag = false;

                }else{
                    AsyncTask asyncTask = new AsyncTask() {
                        @SuppressLint("WrongThread")
                        protected Object doInBackground(Object[] objects) {


                            username = txtUsername.getText().toString();
                            password = txtPassword.getText().toString();
                            address1 = txtAddressLine1.getText().toString();
                            address2 = txtAddressLine2.getText().toString();
                            town = txtTown.getText().toString();
                            county = txtCounty.getText().toString();
                            postcode = txtPostcode.getText().toString();
                            regdate = txtDatebirth.getText().toString();


                            try{
                                userDTO sendUser = new userDTO(username, password,address1,address2,town,county,postcode,regdate,isAdmin);
                                url = new URL("http://10.0.2.2:8080/cinemaProjectNew/webresources/ws/register/");
                                //putData(url,sendUser,builder);

                                Gson gson = new Gson();
                                String jsonString = gson.toJson(sendUser);

                                Put put = new Put(url,jsonString,builder);

                                Intent intent = new Intent(activity_register.this, activity_login.class);
                                startActivity(intent);
                                finish();
                            }catch(Exception ex){
                                System.out.println(ex);
                            }


                            return true;
                        }
                    }.execute();
                }


            }
        });
    }



}

