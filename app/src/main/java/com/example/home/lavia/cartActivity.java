package com.example.home.lavia;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.multidex.MultiDex;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class cartActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;
    String ebu,message,phoneNo;
    Intent camshot;
    Uri call;
    Button checkOut,pay;
    DatabaseReference databaseUser;
    RecyclerView.LayoutManager layoutManager;
    protected RecyclerView.Adapter<cart_Adapter.cartHolder> adapter;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener AuthListener;
    String store;
    RecyclerView mRecyclerView;
    String delivery;
    ProgressDialog progressDialog;
    SharedPreferences pref;
    FirebaseUser firebaseUser;
    public final static String tag_contact="stuff";
    static ArrayList<userPurchase> cartArray=new ArrayList<>();


    static ArrayList<userPurchase> products() {

//        for (int i=1;i>=p;i++){}
            return cartArray;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Thread.setDefaultUncaughtExceptionHandler(new MyExceptionHandler(this));
        if (getIntent().getBooleanExtra("crash", false)) {
            Intent n = new Intent(cartActivity.this,firstActivity.class);
            startActivity(n);
        }

        mAuth= FirebaseAuth.getInstance();
        AuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                firebaseUser = firebaseAuth.getCurrentUser();

                if (firebaseUser == null) {
                    // User is signed out
                    Toast.makeText(cartActivity.this,"Please Sign In or Sign Up",Toast.LENGTH_LONG).show();

                    Intent i = new Intent(cartActivity.this,firstActivity.class);
                    startActivity(i);
                }else {
                    pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                }
            }
        };

        databaseUser= FirebaseDatabase.getInstance().getReference("lavia/Nairobi");

        checkOut = findViewById(R.id.call);
        checkOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUser();
            }
        });
//        pay = findViewById(R.id.pay_out);
//        pay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                display_dialog(getApplicationContext());
//            }
//        });

        mRecyclerView = findViewById(R.id.listy);
        mRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(cartActivity.this);
        mRecyclerView.setLayoutManager(layoutManager);

//        helper = new detailActivity();
//        cartArray= detailActivity.cartArray;
//        =detailActivity.products();

        cartArray.trimToSize();

            adapter = new cart_Adapter(cartActivity.this,mRecyclerView, new cart_Adapter.OnRecyclerViewItemClickedListener() {
                @Override
                public void OnItemDelete(userPurchase item) {
                    cartArray.remove(item);
                    cartArray.trimToSize();
                    adapter.notifyDataSetChanged();
                }
            });
            mRecyclerView.setAdapter(adapter);


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

    private void addUser() {
        ebu= pref.getString(Home.gani, null);

        if (ebu == null) {
            Toast.makeText(cartActivity.this, "Please Select Store", Toast.LENGTH_LONG).show();

            Intent intent = new Intent(cartActivity.this, outletActivity.class);
            startActivity(intent);
        } else {
            store = ebu.trim();
        }

        progressDialog=ProgressDialog.show(cartActivity.this,"Chill","Loading...");

        databaseUser.child(store).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                System.out.println(dataSnapshot);
                sale nunu = dataSnapshot.getValue(sale.class);
                assert nunu != null;
                delivery = nunu.getServiceContact();

                saveThis();



            }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(cartActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }

        });

    }

    void saveThis(){
        if (cartArray == null) {
            Toast.makeText(cartActivity.this, "cart Empty, purchase something", Toast.LENGTH_LONG).show();
            finish();
        } else {
            databaseUser.child(store).child("Users").setValue(cartArray).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    Toast.makeText(cartActivity.this, "Sold!!", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    call();
//                    sendSMSMessage();
                }
            });
            }
        }

    @SuppressLint("MissingPermission")
    private void call()
    {
        if (ActivityCompat.checkSelfPermission(cartActivity.this.getApplicationContext(), Manifest.permission.CALL_PHONE) ==
                PackageManager.PERMISSION_GRANTED) {

             call= Uri.parse("tel:" + delivery);

             Intent tent = new Intent(Intent.ACTION_CALL);
             tent.setData(call);

             startActivity(tent);
        }
        else
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 0);
        }
        PhoneCallListener phoneListener = new PhoneCallListener();
        TelephonyManager telephonyManager = (TelephonyManager) this
                .getSystemService(Context.TELEPHONY_SERVICE);
        assert telephonyManager != null;
        telephonyManager.listen(phoneListener,
                PhoneStateListener.LISTEN_CALL_STATE);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
//        switch (requestCode) {
//            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    SmsManager smsManager = SmsManager.getDefault();
//                    smsManager.sendTextMessage(phoneNo, null, message, null, null);
//                    Toast.makeText(getApplicationContext(), "SMS sent.",
//                            Toast.LENGTH_LONG).show();
//
//                    cartArray.clear();
//                }
//            }
//        }
        if (ActivityCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Intent i = new Intent(Intent.ACTION_CALL);
            i.setData(call);
            startActivity(i);
        }
    }

    public void display_dialog(final Context context) {
        final WindowManager manager = (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.alpha = 1.0f;
        layoutParams.packageName = context.getPackageName();
        layoutParams.buttonBrightness = 1f;
        layoutParams.windowAnimations = android.R.style.Animation_Dialog;

        final View view = View.inflate(context.getApplicationContext(),R.layout.payout_dialog, null);
        Button call = view.findViewById(R.id.call);
        ImageView close = view.findViewById(R.id.close);
        call.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
              call();

            }
        });
        close.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                assert manager != null;
                manager.removeView(view);
            }
        });
        assert manager != null;
        manager.addView(view, layoutParams);
    }

//    private void display_dialog() {
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
//                builder.setTitle(ebu+" \n Lipa na Mpesa ").setMessage("paybill: 256140 \nAccount no. 789512634578").setCancelable(false)
//                        .setPositiveButton("satisfied? call", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                saveThis();
//                            }
//                        }).setNegativeButton("no Thanks", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        dialog.cancel();
//                    }
//                });
//                final AlertDialog alert = builder.create();
//                alert.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
//                alert.show();
//            }
//        }, 60000);
//        startActivity(new Intent(this, cartActivity.class));
//        finish();
//    }

    private class PhoneCallListener extends PhoneStateListener {

        private boolean isPhoneCalling = false;

        String LOG_TAG = "LOGGING 123";

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {

            if (TelephonyManager.CALL_STATE_RINGING == state) {
                // phone ringing
                Log.i(LOG_TAG, "RINGING, number: " + incomingNumber);
            }

            if (TelephonyManager.CALL_STATE_OFFHOOK == state) {

                Log.i(LOG_TAG, "OFFHOOK");

                isPhoneCalling = true;
            }

            if (TelephonyManager.CALL_STATE_IDLE == state) {

                Log.i(LOG_TAG, "IDLE");

                if (isPhoneCalling) {

                    Log.i(LOG_TAG, "restart app");

                    // restart app
                    Intent i = getBaseContext().getPackageManager()
                            .getLaunchIntentForPackage(
                                    getBaseContext().getPackageName());
                    assert i != null;
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);

                    isPhoneCalling = false;
                }

            }
        }
    }

    protected void sendSMSMessage() {
        Intent user=new Intent();
        phoneNo = user.getStringExtra(tag_contact);
        message = "Lavia Liquor \n"+"Thankyou for shopping with Us. "+"\n Shake before use, \nDrink responsibly";

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
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
        getMenuInflater().inflate(R.menu.cart, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logOut) {

            firstActivity.auth.signOut();

            startActivity(new Intent(cartActivity.this, firstActivity.class)); //Go back to home page
            finish();

        }
        return true;
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

        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onStop() {
        super.onStop();
        if (firstActivity.mAuthListener != null) {
            mAuth.removeAuthStateListener(firstActivity.mAuthListener);
        }
    }
}
