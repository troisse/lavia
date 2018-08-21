package com.example.home.lavia;

import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class Branddy extends AppCompatActivity {

    ListView list;
    Custom2Adapter adapter;
    ArrayList<ImageUploadInfo> users;
    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_view_group22);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setContentView(R.layout.activity_brandy);

        list = findViewById(R.id.listview);
        users = new ArrayList();
        adapter = new Custom2Adapter(this,users);
        list.setAdapter(adapter);

        progress = new ProgressDialog(this);
        progress.setMessage("Loading Data...");
        progress.show();

        DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Comfort/Liquor/Brandy/");

//            final ImageUploadInfo liq = new ImageUploadInfo();
//            liq.setImageName;
//            liq.setImagePrice(imagePrice.getText().toString().trim());

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                users.clear();
                for (DataSnapshot snap: dataSnapshot.getChildren()){

                    final ImageUploadInfo liq = new ImageUploadInfo();
                    /*ImageUploadInfo liq=snap.getValue(ImageUploadInfo.class);*/

                    liq.setImageGroup(snap.getKey());
                    users.add(liq);
                }
                progress.dismiss();
                Collections.reverse(users);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
