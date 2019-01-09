package com.example.home.lavia;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.multidex.MultiDex;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.home.lavia.cartActivity.products;

public class detailActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
    ProgressDialog progressDialog;
    Intent camshot,i;
    ImageView wanPic;
    EditText amount;
    TextView wanTitle,wanDetail;
    Button cart,cancel;
    Toolbar toolbar;
    String buyName;
    String buyPrice;
    String url;
    String store;
    String time;
    String buyGroup ;
    public static final String EXTRA_URL="url";
    public static final String EXTRA_NAME="cartArray";
    public static final String EXTRA_PRICE="price";
    public static final String EXTRA_GROUP="group";
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener AuthListener;
    String userName;
    SharedPreferences pref;
    FirebaseUser firebaseUser;
    String dAmount;
    String contact;

    @SuppressLint({"SetTextI18n", "CommitPrefEdits"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        toolbar = findViewById(R.id.toolbarDet);
        setSupportActionBar(toolbar);

        Thread.setDefaultUncaughtExceptionHandler(new MyExceptionHandler(this));
        if (getIntent().getBooleanExtra("crash", false)) {
            Intent n = new Intent(detailActivity.this,firstActivity.class);
            startActivity(n);
        }

        mAuth= FirebaseAuth.getInstance();
        AuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                firebaseUser = firebaseAuth.getCurrentUser();

                if (firebaseUser == null) {
                    // User is signed out
                    Toast.makeText(detailActivity.this,"Please Sign In or Sign Up",Toast.LENGTH_LONG).show();

                    Intent i = new Intent(detailActivity.this,firstActivity.class);
                    startActivity(i);
                }
            }
        };

        wanPic = findViewById(R.id.pic);
        wanTitle= findViewById(R.id.titl);
        wanDetail=findViewById(R.id.detail);
        cart = findViewById(R.id.cart);
        cancel =findViewById(R.id.cancel);
        amount= findViewById(R.id.amount);

        pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        String ebu= pref.getString(Home.gani, null);

        if (ebu== null){
            Toast.makeText(detailActivity.this,"Please Select Store",Toast.LENGTH_LONG).show();

            Intent intent = new Intent(detailActivity.this,outletActivity.class);
            startActivity(intent);
        }else {
            store= ebu.trim();
        }
        url= getIntent().getStringExtra(EXTRA_URL);
        buyName= getIntent().getStringExtra(EXTRA_NAME);
        buyPrice = getIntent().getStringExtra(EXTRA_PRICE);
        buyGroup = getIntent().getStringExtra(EXTRA_GROUP);

        wanTitle.setText(buyName+ ":  ksh."+ buyPrice);
        picassoClient.downloading(detailActivity.this,url,wanPic);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        cart.setOnClickListener(detailActivity.this);
        cancel.setOnClickListener(detailActivity.this);

        userName= firstActivity.firebaseUser.getDisplayName();


    }

    @Override
    public void onResume() {
        super.onResume();
        mAuth.addAuthStateListener(AuthListener);
    }


    void collectDetails(final ArrayList<userPurchase> cartArray){

        progressDialog= ProgressDialog.show(this,"Chill","loading...");

        String uid = firebaseUser.getUid();
        DatabaseReference userDetails = FirebaseDatabase.getInstance().getReference("lavia/Nairobi/").child("Users").child(uid);

        userDetails.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User few =dataSnapshot.getValue(User.class);

                assert few != null;
                userName=few.getUsername();
                contact=few.getContact();

                userPurchase u = new userPurchase();

                u.setBuyName(buyName);
                u.setBuyPrice(buyPrice);
                u.setAmount(dAmount);
                u.setUserName(userName);
                u.setContact(contact);

                cartArray.add(u);

                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    @Override
    public void onClick(View v) {
        dAmount = amount.getText().toString().trim();

        if (dAmount.isEmpty()) {
            amount.setError("Kindly Fill This Field");

        } else {

//            int p=  products().size();
//            int i=  0;
//                collectDetails(products());
//                System.out.println(p);
//            do {
//                if(p==i){
//                    System.out.println(p);
//                    }else{
//                    i=p;
//                    System.out.println(i);
//                    i++;
//                }
                collectDetails(products());
//                System.out.println(p);
//                p++;

//            } while (p < i);

            if (v == cart) {

                if (userName.equals("comfort001")) {

                    firebaseClient.remove(buyName);
                    wanPic.setImageResource(R.drawable.sld3);
                    wanTitle.setText("");

                    Toast.makeText(getApplicationContext(),"Product Removed",Toast.LENGTH_SHORT).show();
                } else {

                        Intent pig = new Intent(detailActivity.this, cartActivity.class);
                        pig.putExtra(cartActivity.tag_contact,contact);
                        startActivity(pig);

                }

            } else if (v == cancel) {

                DrawerLayout drawer = findViewById(R.id.drawer_layout);
                if (!drawer.isDrawerVisible(GravityCompat.START)) {
//                    collectDetails(products());

                    drawer.openDrawer(GravityCompat.START);
                } else {
                    super.onBackPressed();
                }
            }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail, menu);
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

            firstActivity.auth.signOut();

            startActivity(new Intent(detailActivity.this, firstActivity.class)); //Go back to home page
            finish();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
