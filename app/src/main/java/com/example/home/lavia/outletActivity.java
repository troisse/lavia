package com.example.home.lavia;

import android.content.Intent;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class outletActivity extends AppCompatActivity {
    RadioGroup radioGroup;
    private RadioButton radioOne;
//    private String selectedType="";
    private RadioButton radioTwo;
    String store;
//    public static String selectedIype;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outlet);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(outletActivity.this, R.color.colorPrimaryDark));
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        radioGroup = findViewById(R.id.radioGroup);
        radioOne= (RadioButton) findViewById(R.id.radioOne);
        radioTwo = (RadioButton) findViewById(R.id.radioTwo);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i==R.id.radioOne){
                    radioOne.getText().length();
                }else if(i==R.id.radioTwo){
                     radioTwo.getText().length();
                }
            }
        });

    }
public void checkButton (View v) {
    int radioId = radioGroup.getCheckedRadioButtonId();
//    UploadInfo store = new UploadInfo();
    store = findViewById(radioId).toString();
//    store.setSelectedType(radioButton.getText().toString());
//selectedIype = radioButton.getText().toString();
    if (radioId == radioOne.getId()) {
     store = radioOne.toString();
    }else if (radioId == radioTwo.getId()){
        store = radioTwo.toString();
    }
}
    public void shop(View view) {
        Intent intent= new Intent(this,Home.class);
        intent.putExtra("store",store);
        startActivity(intent);

    }
}
