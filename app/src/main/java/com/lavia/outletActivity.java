package com.lavia;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDex;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class outletActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
    RadioGroup radioGroup;
    protected RadioButton Comfort;
//    protected RadioButton Migos;
    protected RadioButton radioButton;
    Button puton;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    String tag,you;
    FirebaseAuth mAuth;
    public final static String gani="store";
    static FirebaseAuth.AuthStateListener AuthListener;

    @SuppressLint({"CommitPrefEdits", "NewApi"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outlet);

        pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        editor = pref.edit();


//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            getWindow().setStatusBarColor(ContextCompat.getColor(outletActivity.this, R.color.colorPrimaryDark));
//        }else{
//            getWindow().setStatusBarColor(ContextCompat.getColor(outletActivity.this, R.color.colorPrimaryDark));
//        }

        Toolbar toolbar =findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        puton =findViewById(R.id.shopButton);
        puton.setOnClickListener(this);
        radioGroup = findViewById(R.id.radioGroup);
        Comfort= findViewById(R.id.radioOne);
//        Migos = findViewById(R.id.radioTwo);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(RadioGroup group, int checkedId) {
               radioButton = findViewById(checkedId);
               checkButton();
           }
       });


        mAuth=FirebaseAuth.getInstance();
        AuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

                if (firebaseUser == null) {
                    // User is signed out
                    Toast.makeText(outletActivity.this,"Please Sign In or Sign Up",Toast.LENGTH_SHORT).show();

                    Intent i = new Intent(outletActivity.this,firstActivity.class);
                    startActivity(i);
                } else {
                you=firebaseUser.getDisplayName();
                }
            }
        };
    }

    @Override
    public void onResume() {
        super.onResume();
        mAuth.addAuthStateListener(AuthListener);
    }

    public void checkButton() {

        int checkedId=radioButton.getId();
   switch(checkedId) {
            case R.id.radioOne:
                final liquor store1 = new liquor();
                store1.setStore(Comfort.getText().toString());
                tag = store1.getStore();

                break;
//            case R.id.radioTwo:
//                final liquor store2 = new liquor();
//                store2.setStore(Migos.getText().toString());
//                tag = store2.getStore();
//
//                break;
        }
    }
    @Override
    public void onClick(View v) {
        if(v==puton){
            editor.putString(gani,tag);
            editor.apply();

            if (tag==null) {

                showDialog();
            }else {

                Intent in= new Intent(outletActivity.this,Home.class);
                in.putExtra(Home.gani,tag);
                startActivity(in);
            }

        }
    }

    private void showDialog(){
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this)
                .setTitle("Hey "+you).setMessage("Pick a Store Please");
        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
            }
        });
        final AlertDialog alert = dialog.create();
        alert.show();

// Hide after some seconds
        final Handler handler  = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (alert.isShowing()) {
                    alert.dismiss();
                }
            }
        };

        alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                handler.removeCallbacks(runnable);
            }
        });

        handler.postDelayed(runnable, 3000);
    }

    @Override
    public void onBackPressed() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this)
                .setTitle("Lavia").setMessage("Log Out?");
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                mAuth.signOut();

                startActivity(new Intent(outletActivity.this, firstActivity.class)); //Go back to home page

            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        final AlertDialog alert = dialog.create();
        alert.show();

// Hide after some seconds
        final Handler handler  = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (alert.isShowing()) {
                    alert.dismiss();
                }
            }
        };

        alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                handler.removeCallbacks(runnable);
            }
        });

        handler.postDelayed(runnable, 3000);

    }
}
