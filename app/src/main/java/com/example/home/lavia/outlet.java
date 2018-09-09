package com.example.home.lavia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class outlet extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outlet);
    }

    public void shop(View view) {
        Intent intent= new Intent(this,Home.class);
        startActivity(intent);
    }
}
