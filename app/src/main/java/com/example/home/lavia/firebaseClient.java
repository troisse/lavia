package com.example.home.lavia;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class firebaseClient {
    public DatabaseReference db;
    Boolean saved=null;
    public ArrayList<liquor> data =new ArrayList<>();
    customAdapter adapter;

    public firebaseClient () {

    }
    public firebaseClient (DatabaseReference db) {
        this.db = db;
    }
    public void fetchData(DataSnapshot dataSnapshot)
    {
        data.clear();

        for (DataSnapshot ds : dataSnapshot.getChildren())
        {
//            String  = dataSnapshot.child("Description").getValue(UploadInfo.class);
//            Long quantity = dataSnapshot.child("Quantity").getValue(Long.class);
//            data.add(description+" ("+quantity+")");
//            myArrayAdapter.notifyDataSetChanged();

            liquor spacecraft=ds.getValue(liquor.class);
            data.add(spacecraft);
            adapter.notifyDataSetChanged();
        }

    }

    //READ BY HOOKING ONTO DATABASE OPERATION CALLBACKS
    public ArrayList<liquor> retrieve() {
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
