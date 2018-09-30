package com.example.home.lavia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class outlet extends AppCompatActivity {
    RadioGroup radioGroup;
    RadioButton radioButton;
    private RadioButton radioOne;
//    private String selectedType="";
    private RadioButton radioTwo;
    public static String selectedIype;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outlet);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        radioGroup = findViewById(R.id.radioGroup);
        radioOne= (RadioButton) findViewById(R.id.radioOne);
        radioTwo = (RadioButton) findViewById(R.id.radioTwo);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i==R.id.radioOne){
                    radioOne.getText().toString();
                }else if(i==R.id.radioTwo){
                     radioTwo.getText().toString();
                }
            }
        });

    }
public void checkButton (View v) {
    int radioId = radioGroup.getCheckedRadioButtonId();
    ImageUploadInfo store = new ImageUploadInfo();
    radioButton = findViewById(radioId);
//    store.setSelectedType(radioButton.getText().toString());
selectedIype = radioButton.getText().toString();

}
    public void shop(View view) {
        Intent intent= new Intent(this,Home.class);
//        intent.putExtra("store",selectedType);
        startActivity(intent);

    }
}
