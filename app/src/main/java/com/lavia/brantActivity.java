package com.lavia;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.multidex.MultiDex;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class brantActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    String store;
    static DatabaseReference db;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener AuthListener ;
    ProgressDialog progressDialog;
    Intent camshot;
    DrawerLayout drawer;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brant);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Thread.setDefaultUncaughtExceptionHandler(new MyExceptionHandler(this));
        if (getIntent().getBooleanExtra("crash", false)) {
            Intent n = new Intent(brantActivity.this, firstActivity.class);
            startActivity(n);
        }

        mAuth= FirebaseAuth.getInstance();
    AuthListener = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

            if (firebaseUser == null) {
                // User is signed out
                Toast.makeText(brantActivity.this,"Please Sign In or Sign Up",Toast.LENGTH_SHORT).show();

                Intent i = new Intent(brantActivity.this,firstActivity.class);
                startActivity(i);
            }
        }
    };


    mRecyclerView = findViewById(R.id.recyclerView);
    mRecyclerView.setHasFixedSize(true);
    layoutManager = new LinearLayoutManager(brantActivity.this);
    mRecyclerView.setLayoutManager(layoutManager);

        pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        String ebu= pref.getString(outletActivity.gani, null);

        if (ebu== null){
        Toast.makeText(brantActivity.this,"Please Select Store",Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(brantActivity.this,outletActivity.class);
        startActivity(intent);
    }else {
            String store=ebu.trim();
            db=FirebaseDatabase.getInstance().getReference("lavia/Nairobi/"+store+"/Liquor/Brandy/");
        }


        progressDialog=ProgressDialog.show(this,"Chill","Loading");
        firebaseClient helper = new firebaseClient(brantActivity.this, db, mRecyclerView);
        helper.refreshData(progressDialog);


    drawer = findViewById(R.id.drawer_layout);
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    drawer.addDrawerListener(toggle);
    toggle.syncState();

    NavigationView navigationView = findViewById(R.id.nav_view);
    navigationView.setNavigationItemSelectedListener(this);

     }

    @Override
    public void onResume() {
        super.onResume();

        mAuth.addAuthStateListener(AuthListener);
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        progressDialog.cancel();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.brant, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {


            case R.id.logOut:

                firstActivity.auth.signOut();

                startActivity(new Intent(brantActivity.this, firstActivity.class)); //Go back to home page
                finish();

                return true;

        }
        return super.onOptionsItemSelected(item);


}

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.whiskey) {
            camshot = new Intent(getApplicationContext(), whiskeyActivity.class);
            startActivity(camshot);
        } else if (id == R.id.vodka) {
            camshot = new Intent(getApplicationContext(), vodkaActivity.class);
            startActivity(camshot);
        } else if (id == R.id.home) {
            camshot = new Intent(getApplicationContext(), Home.class);
            startActivity(camshot);
        }else if (id == R.id.wine) {
            camshot = new Intent(getApplicationContext(), wineActivity.class);
            startActivity(camshot);
        } else if (id == R.id.brant) {
            camshot = new Intent(getApplicationContext(), brantActivity.class);
            startActivity(camshot);

        } else if (id == R.id.beer) {
            camshot = new Intent(getApplicationContext(), beerActivity.class);
            startActivity(camshot);
        } else if (id == R.id.gar) {
            camshot = new Intent(getApplicationContext(), garActivity.class);
            startActivity(camshot);
        }else if (id == R.id.refreshments) {
            camshot = new Intent(getApplicationContext(), refreshmentsActivity.class);
            startActivity(camshot);
        }

        camshot.setAction(Intent.ACTION_VIEW);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

