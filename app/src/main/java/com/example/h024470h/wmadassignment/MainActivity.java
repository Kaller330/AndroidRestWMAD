package com.example.h024470h.wmadassignment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public SharedPreferences sharedPref;
    boolean loggedIn = false;
    boolean admin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        TextView txtTest = (TextView)findViewById(R.id.txtTest);


        sharedPref = getSharedPreferences(getString(R.string.storedPreferenceName), 	Context.MODE_PRIVATE);

        if(sharedPref.contains("username")) {
            String retrieved = sharedPref.getString("username", "Not logged in");
            admin = sharedPref.getBoolean("isAdmin", false);
            txtTest.setText(retrieved);
            loggedIn = true;


            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.nav_Login).setVisible(false);
            nav_Menu.findItem(R.id.nav_Register).setVisible(false);



            View hView =  navigationView.getHeaderView(0);
            TextView nav_user = (TextView)hView.findViewById(R.id.txtActiveUser);
            nav_user.setText(retrieved);

        } else{
            txtTest.setText("Please log in.");
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.nav_bookings).setVisible(false);
            nav_Menu.findItem(R.id.nav_Logout).setVisible(false);
            nav_Menu.findItem(R.id.nav_edit).setVisible(false);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        if (admin != true){
            menu.findItem(R.id.action_admin).setVisible(false);
        }


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_admin) {
            startActivity(new Intent(MainActivity.this, activity_admin.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_showings) {
            startActivity(new Intent(MainActivity.this, activity_showings.class));
        } else if (id == R.id.nav_bookings) {
            startActivity(new Intent(MainActivity.this, activity_bookings.class));
        } else if (id == R.id.nav_Login) {
            startActivity(new Intent(MainActivity.this, activity_login.class));
        } else if (id == R.id.nav_edit) {
            startActivity(new Intent(MainActivity.this, activity_updateUser.class));
        } else if (id == R.id.nav_Register) {
            startActivity(new Intent(MainActivity.this, activity_register.class));
        } else if (id == R.id.nav_Logout) {
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.remove("username");
            editor.remove("hashedPass");
            editor.remove("address1");
            editor.remove("address2");
            editor.remove("town");
            editor.remove("county");
            editor.remove("postcode");
            editor.remove("regdate");
            editor.remove("isAdmin");
            editor.apply();
            startActivity(new Intent(MainActivity.this, MainActivity.class));
        } else if (id == R.id.nav_Exit){
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
