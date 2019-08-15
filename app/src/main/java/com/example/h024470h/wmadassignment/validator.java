package com.example.h024470h.wmadassignment;


import android.util.Log;

public class validator {


    public boolean checkUsername(String username){
        if (username.equals("")){
            Log.d("[Validator]:", "Username null");
            return false;
        }else{
            return true;
        }
    }

    public boolean checkPassword(String password, String passwordConf) {
        if (passwordConf.equals(password) == false) {
            Log.d("[Validator]:", "Passwords don't match");
            return false;
        } else {
            return true;
        }
    }

    public boolean checkAddressLine1(String addressLine1){
        if (addressLine1.equals("")){
            Log.d("[Validator]:", "AddressLine1 null");
            return false;
        }else{
            return true;
        }
    }

    public boolean checkAddressLine2(String addressLine2){
        if (addressLine2.equals("")){
            Log.d("[Validator]:", "AddressLine2 null");
            return false;
        }else{
            return true;
        }
    }

    public boolean checkTown(String town){
        if (town.equals("")){
            Log.d("[Validator]:", "town null");
            return false;
        }else{
            return true;
        }
    }

    public boolean checkCounty(String county){
        if (county.equals("")){
            Log.d("[Validator]:", "county null");
            return false;
        }else{
            return true;
        }
    }

    public boolean checkPostcode(String postcode){
        if (postcode.equals("")){
            Log.d("[Validator]:", "postcode null");
            return false;
        }else{
            return true;
        }
    }

    public boolean checkDatebirth(String datebirth){
        if (datebirth.equals("")){
            Log.d("[Validator]:", "datebirth null");
            return false;
        }
        else{
            return true;
        }
    }






}
