package com.lavia;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDex;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    FirebaseAuth mAuth;
    static FirebaseAuth.AuthStateListener AuthListener;
    public final static String gani="store";
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    String ebu;
    TextView openMenu;
    Intent camshot;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hom);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        editor = pref.edit();

        ebu= pref.getString(gani, null);

        Thread.setDefaultUncaughtExceptionHandler(new MyExceptionHandler(getParent()));
        if (getIntent().getBooleanExtra("crash", false)) {
            Intent n = new Intent(Home.this, firstActivity.class);
            startActivity(n);
        }

        openMenu=findViewById(R.id.menu);
        openMenu.setOnClickListener(this);
        mAuth= FirebaseAuth.getInstance();

        AuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

                if (firebaseUser == null) {
                    // User is signed out
                    Toast.makeText(Home.this,"Please Sign In or Sign Up",Toast.LENGTH_LONG).show();

                    Intent i = new Intent(Home.this,firstActivity.class);
                    startActivity(i);
                }
            }
        };


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
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
    public void onClick(View v) {
        if(v==openMenu){
            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            drawer.openDrawer(GravityCompat.START);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }
    public void checkConnection(){
        if(!isOnline()){
            Toast.makeText(Home.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
            camshot= new Intent(getApplicationContext(),Home.class);
            startActivity(camshot);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.hom, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logOut) {

            FirebaseAuth auth=FirebaseAuth.getInstance();
            auth.signOut();

            startActivity(new Intent(Home.this, firstActivity.class)); //Go back to home page
            finish();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
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

        checkConnection();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
