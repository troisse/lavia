package com.example.home.lavia;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class first_page extends AppCompatActivity {

    RadioButton radioButton;
    RadioGroup radioGroup;
    TextView text;
    Typeface tfc1;
    String str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);

        radioGroup = findViewById(R.id.radioGroup);
        text = findViewById(R.id.txt);


        tfc1 = Typeface.createFromAsset(getAssets(), "fonts/frozen_ice.ttf");
        text.setTypeface(tfc1);



    }

//    ConnectivityManager cm =
//            (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
//
//    NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
//    boolean isConnected = activeNetwork != null &&
//            activeNetwork.isConnectedOrConnecting();



    public void checkButton(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.comfort:
                if (checked) str = "button1Text";
//                if (!isConnected){
//                    Toast.makeText(this, "Kindly Check your internet Connectivity", Toast.LENGTH_LONG).show();
//                }

//                else{
                Intent intent = new Intent(this, Home.class);
                startActivity(intent);
                break;




            case R.id.kasa:
                if (checked) str = "button2Text";
//                if (!isConnected){
//                    Toast.makeText(this, "Kindly Check your internet Connectivity", Toast.LENGTH_LONG).show();
//                }

//                else{
                    Intent intenty = new Intent(this, Home_1.class);
                    startActivity(intenty);


                break;
//            case R.id.radioButton3:
//                if (checked) str = "button3Text";
//                break;
        }
//        Intent intent = new Intent(this, first_page.class);
//        intent.putExtra("radioChosen", str);
    }



    }

