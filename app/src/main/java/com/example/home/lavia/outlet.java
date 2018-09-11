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
    private RadioButton radioOne;
    private String selectedType="";
    private RadioButton radioTwo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outlet);
        radioGroup = findViewById(R.id.radioGroup);
        radioOne= (RadioButton) findViewById(R.id.radioOne);
        radioTwo = (RadioButton) findViewById(R.id.radioTwo);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i==R.id.radioOne){
                    selectedType = radioOne.getText().toString();
                }else if(i==R.id.radioTwo){
                    selectedType = radioTwo.getText().toString();
                }
            }
        });
    }
public void checkButton (View v) {
    int radioId = radioGroup.getCheckedRadioButtonId();
    radioButton = findViewById(radioId);
}
    public void shop(View view) {
        Intent intent= new Intent(this,Home.class);
        intent.putExtra("store",selectedType);
        startActivity(intent);
    }
}
