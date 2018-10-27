package com.example.home.lavia;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
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
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Brandy extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

//    FirebaseDatabase firebaseDatabase;
    DatabaseReference db;
ArrayList<liquor>data;

//customAdapter adapter;
//    ListView lv;
//    EditText nameEditTxt,priceLiq;
//TextView nameLiq, priceLiq;
//    ImageView imageLiq;
//    LinearLayoutManager mLayoutManager; //for sorting
    SharedPreferences mSharedPref; //for saving sort settings
    RecyclerView mRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_view_group22);
//        FirebaseRecyclerAdapter firebaseRecycler;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setContentView(R.layout.activity_brandy);
//        ListView lv = findViewById(R.id.listy);

        //        Firebase.setAndroidContext(this);
        db = FirebaseDatabase.getInstance().getReference().child("Nairobi/").child("Liquor/").child("Brandy");
//        db.keepSynced(true);
//        nameLiq = (TextView) findViewById(R.id.name_liq);
//        priceLiq = (TextView) findViewById(R.id.price_liq);
//        imageLiq = (ImageView) findViewById(R.id.image_liq);
//        //ADAPTER
//
//        adapter = new ArrayAdapter(Brandy.this,R.layout.liq_list,data);
        mRecyclerView =(RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(Brandy.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
//        mRecyclerView.setAdapter();

        layoutManager.setReverseLayout(false);
        layoutManager.setStackFromEnd(false);
        //RecyclerView


//        customAdapter adapter=new customAdapter(this,data);

//        customAdapter adapter = new customAdapter(Brandy.this,data);
//        mRecyclerView.setAdapter(adapter);

//        customAdapter adapter=new customAdapter(this,helper.retrieve());
//        adapter=new customAdapter(this,android.R.layout.list_content,helper.data);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(Brandy.this);

//        db.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                String value = dataSnapshot.getValue(String.class);
//
//                //                Log.d(TAG, "Value is: " + value);
//
//            }

//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//        customAdapter adapter = new customAdapter(Brandy.this,data);
//        mRecyclerView.setAdapter(adapter);
//
//    @Override
//    public void onStart() {
//            super.onStart();
            FirebaseRecyclerAdapter<liquor, ViewHolder> adapter =
                    new FirebaseRecyclerAdapter<liquor, ViewHolder>(
                            liquor.class,
                            R.layout.liq_list,
                            ViewHolder.class,
                            db
                    ) {
                        @Override
                        protected void populateViewHolder(final ViewHolder viewHolder, liquor liq, int position) {
                            db.addValueEventListener(new ValueEventListener() {

                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                        liquor detail = postSnapshot.getValue(liquor.class);

                                        String liqName = detail.getLiqName();
                                        String liqPrice = detail.getLiqPrice();
                                        String imageUrl = detail.getImageUrl();

                                        viewHolder.nameLiq.setText(liqName);
                                        viewHolder.priceLiq.setText(liqPrice);
                                        Picasso.get().load(imageUrl).into(viewHolder.imageUrl);

                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                    // [START_EXCLUDE]
                                    System.out.println("The read failed: " + databaseError.getMessage());
                                }
                            });
                        }

                    };
//     lv.setAdapter((ListAdapter) new customAdapter(this,mRecyclerView));
        mRecyclerView.setAdapter(adapter);
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
