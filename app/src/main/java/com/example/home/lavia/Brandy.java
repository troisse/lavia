package com.example.home.lavia;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class Brandy extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    firebaseClient helper;
    DatabaseReference db;
    customAdapter adapter;
    ListView lv;
    EditText nameEditTxt,priceLiq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_view_group22);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setContentView(R.layout.activity_brandy);
        lv = (ListView) findViewById(R.id.listy);

        //ADAPTER

        lv.setAdapter(adapter);


        //INITIALIZE FIREBASE DB
        db = FirebaseDatabase.getInstance().getReference().child("Nairobi/").child("Liquor/").child("Brandy");

        helper=new firebaseClient(db);
        adapter=new customAdapter(this,helper.retrieve());
//        helper.adapter=new customAdapter(this,R.layout.liq_list,data);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener( Brandy.this);


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
        getMenuInflater().inflate(R.menu.add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
//    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.leakage) {
            Intent camshot = new Intent(getApplicationContext(), salesActivity.class);
            startActivity(camshot);
        } else if (id == R.id.whiskey) {
            Intent camshot = new Intent(getApplicationContext(), whiskeyActivity.class);
            startActivity(camshot);
        } else if (id == R.id.vodka) {
            Intent camshot = new Intent(getApplicationContext(), vodkaActivity.class);
            startActivity(camshot);
        } else if (id == R.id.home) {
            Intent camshot = new Intent(getApplicationContext(), Home.class);
            startActivity(camshot);
        }else if (id == R.id.wine) {
            Intent camshot = new Intent(getApplicationContext(), wineActivity.class);
            startActivity(camshot);
        } else if (id == R.id.brandy) {
            Intent camshot = new Intent(getApplicationContext(), Brandy.class);
            startActivity(camshot);
        } else if (id == R.id.cart) {
            Intent camshot = new Intent(getApplicationContext(), cartActivity.class);
            startActivity(camshot);
        } else if (id == R.id.rum) {
            Intent camshot = new Intent(getApplicationContext(), rumActivity.class);
            startActivity(camshot);
        } else if (id == R.id.gin) {
            Intent camshot = new Intent(getApplicationContext(), ginActivity.class);
            startActivity(camshot);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
