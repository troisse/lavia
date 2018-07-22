package com.example.home.lavia;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class first_page extends AppCompatActivity {
    private Button next_page;
    RadioButton radioButton;
    RadioGroup radioGroup;
    TextView text;
    Typeface tfc1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);
        next_page = findViewById(R.id.starter);
        radioGroup = findViewById(R.id.radioGroup);
        text= findViewById(R.id.txt);

        tfc1 = Typeface.createFromAsset(getAssets(),"fonts/frozen_ice.ttf");
        text.setTypeface(tfc1);

    }

    public void lavia_start(View view) {
        laviaStart();
    }
    public void laviaStart(){
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }
}
