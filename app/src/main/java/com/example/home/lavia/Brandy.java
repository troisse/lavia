package com.example.home.lavia;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.server.converter.StringToIntConverter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;

public class Brandy extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ListView listView;
    ProgressBar progressBar;
    private DatabaseReference db;
    FirebaseHelper helper;
    ArrayAdapter adapter;
//    CustomAdapter adapter;

    ImageUploadInfo imageUploadInfo;
String ImageUploadInfo;
   private ArrayList<String> liqs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brandy);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        adapter = new ArrayAdapter<>(this,R.layout.liq_list, this.liqs);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        listView = (ListView) findViewById(R.id.listview);
        //INITIALIZE FIREBASE DB
        db = FirebaseDatabase.getInstance().getReference().child("Comfort/Liquor");
        helper = new FirebaseHelper(db);
        imageUploadInfo = new ImageUploadInfo();

db.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

     String string = String.valueOf(dataSnapshot.getValue(ImageUploadInfo.class));

     liqs.add(string);
   adapter.notifyDataSetChanged();
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
});
        //ADAPTER

        listView.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCart();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }



    public void openCart() {
        Intent intent = new Intent(this, Cart.class);
        startActivity(intent);
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
        getMenuInflater().inflate(R.menu.brandy, menu);
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
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.leakage) {
            Intent camshot = new Intent(getApplicationContext(), Add.class);
            startActivity(camshot);
        }else if (id == R.id.whiskey) {
            Intent camshot = new Intent(getApplicationContext(), Whiskey.class);
            startActivity(camshot);
        }else if (id == R.id.vodka) {
            Intent camshot = new Intent(getApplicationContext(), Vodka.class);
            startActivity(camshot);
        }else if (id == R.id.wine) {
            Intent camshot = new Intent(getApplicationContext(), Wine.class);
            startActivity(camshot);
        }else if (id == R.id.home) {
            Intent camshot = new Intent(getApplicationContext(), Home.class);
            startActivity(camshot);
        }else if (id == R.id.brandy) {
            Intent camshot = new Intent(getApplicationContext(), Brandy.class);
            startActivity(camshot);
        }else if (id == R.id.rum) {
            Intent camshot = new Intent(getApplicationContext(), Rum.class);
            startActivity(camshot);
        }else if (id == R.id.gin) {
            Intent camshot = new Intent(getApplicationContext(), Gin.class);
            startActivity(camshot);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
