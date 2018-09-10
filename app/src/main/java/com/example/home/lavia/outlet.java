package com.example.home.lavia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class outlet extends AppCompatActivity {
    RadioGroup radioGroup;
    RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outlet);
        radioGroup = findViewById(R.id.radioGroup);
    }
public void checkButton (View v) {
    int radioId = radioGroup.getCheckedRadioButtonId();
    radioButton = findViewById(radioId);
}
    public void shop(View view) {
        Intent intent= new Intent(this,Home.class);
        startActivity(intent);
    }
}
