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
import android.widget.Toast;

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
        text = findViewById(R.id.txt);

        tfc1 = Typeface.createFromAsset(getAssets(), "fonts/frozen_ice.ttf");
        text.setTypeface(tfc1);

    }

    public void lavia_start(View view) {
        laviaStart();
    }

    public void laviaStart() {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

        public void onRadioButtonClicked (View view){
            // Is the button now checked?
            boolean checked = ((RadioButton) view).isChecked();

            // Check which radio button was clicked
            switch (view.getId()) {
                case R.id.comfort:
                    if (checked) {

                        Intent intent = new Intent(this, Home.class);

                        Toast.makeText(this, "// Pirates are the best", Toast.LENGTH_SHORT).show();
                    }

                    break;
                case R.id.kasa:
                    if (checked) {
                        Intent intent = new Intent(this, Home_1.class);
                        Toast.makeText(this, "// Ninjas rule", Toast.LENGTH_SHORT).show();
                    }

                    break;
            }
        }
    }

