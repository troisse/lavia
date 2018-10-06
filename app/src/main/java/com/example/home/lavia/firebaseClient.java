package com.example.home.lavia;

import android.widget.ArrayAdapter;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class firebaseClient {
    DatabaseReference db;
    Boolean saved=null;
    ArrayList<ImageUploadInfo> data=new ArrayList<>();
    ArrayAdapter<customAdapter> adapter;

    public firebaseClient (DatabaseReference db) {
        this.db = db;
    }
    private void fetchData(DataSnapshot dataSnapshot)
    {
        data.clear();

        for (DataSnapshot ds : dataSnapshot.getChildren())
        {
            ImageUploadInfo spacecraft=ds.getValue(ImageUploadInfo.class);
            data.add(spacecraft);
            adapter.notifyDataSetChanged();
        }

    }
    //READ BY HOOKING ONTO DATABASE OPERATION CALLBACKS
    public ArrayList<ImageUploadInfo> retrieve() {
        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return data;
    }
}
