package com.example.home.lavia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class firstActivity extends AppCompatActivity {

//    RadioButton radioButton;
//    RadioGroup radioGroup;
//    TextView text;
//    Typeface tfc1;
//    String str;
//    ViewFlipper v_flipper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
//        int images[]= {R.drawable.grapey,R.drawable.delivery,R.drawable.lavia_icon};

//        v_flipper = findViewById(R.id.v_flipper);
//        radioGroup = findViewById(R.id.radioGroup);
//        text = findViewById(R.id.txt);
//
//
//        tfc1 = Typeface.createFromAsset(getAssets(), "fonts/frozen_ice.ttf");
//        text.setTypeface(tfc1);
//        for (int image:images){
//            flipperImages(image);
//        }

//    }
//    public void flipperImages(int image){
//        ImageView imageView = new ImageView(this);
//        imageView.setBackgroundResource(image);
//
//        v_flipper.addView(imageView);
//        v_flipper.setFlipInterval(4000);
//        v_flipper.setAutoStart(true);
//        v_flipper.setInAnimation(this,android.R.anim.slide_in_left);
//        v_flipper.setOutAnimation(this,android.R.anim.slide_out_right);
//    }
    public void outlet (View view){
        Intent intent = new Intent(this, outletActivity.class);
        startActivity(intent);
    }

//    ConnectivityManager cm =
//            (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
//
//    NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
//    boolean isConnected = activeNetwork != null &&
//            activeNetwork.isConnectedOrConnecting();


//
//    public void checkButton(View view) {
//        // Is the button now checked?
//        boolean checked = ((RadioButton) view).isChecked();
//        // Check which radio button was clicked
//        switch (view.getId()) {
//            case R.id.comfort:
//                if (checked) str = "button1Text";
////                if (!isConnected){
////                    Toast.makeText(this, "Kindly Check your internet Connectivity", Toast.LENGTH_LONG).show();
////                }
//
////                else{
//                Intent intent = new Intent(this, Home.class);
//                startActivity(intent);
//                break;
//
//
//
//
//            case R.id.kasa:
//                if (checked) str = "button2Text";
////                if (!isConnected){
////                    Toast.makeText(this, "Kindly Check your internet Connectivity", Toast.LENGTH_LONG).show();
////                }
//
////                else{
//                    Intent intenty = new Intent(this, Home_1.class);
//                    startActivity(intenty);
//
//
//                break;
//
//        }
//    }
//


    }


