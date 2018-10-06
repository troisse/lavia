package com.example.home.lavia;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


public class salesActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    int store;


    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

//    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        selectedType = getIntent().getStringExtra("store");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener( salesActivity.this);
    }

//    private String getFileExtension(Uri uri){
//        ContentResolver cR = getContentResolver();
//        MimeTypeMap mime =  MimeTypeMap.getSingleton();
//        return mime.getExtensionFromMimeType(cR.getType(uri));
//    }

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

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    //Call Leader
//    @SuppressLint("MissingPermission")
//    public void call(View view) {
//        String number = "0714359957";
//        Intent callIntent = new Intent(Intent.ACTION_CALL);
//        callIntent.setData(Uri.parse("tel:" + number));
//        startActivity(callIntent);
//    }
}
