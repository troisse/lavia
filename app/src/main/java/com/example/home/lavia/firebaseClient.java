package com.example.home.lavia;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.widget.Toast.LENGTH_SHORT;

public class firebaseClient {

//    ProgressBar progressBar;
    Context c;
//    DatabaseReference fire;
    DatabaseReference db;
    RecyclerView rv;
    ArrayList<liquor> data=new ArrayList<>();
    customAdapter adapter;

    public firebaseClient(Context c, DatabaseReference db, RecyclerView mRecyclerView) {
        this.c = c;
        this.db = db;
        this.rv = mRecyclerView;

        //INITIALIZE
        Firebase.setAndroidContext(c);

//        fire =new Firebase(db_Url)
    }

    private void fetchData(DataSnapshot dataSnapshot) {
        data.clear();

        for (DataSnapshot ds : dataSnapshot.getChildren()) {
//            String  = dataSnapshot.child("Description").getValue(UploadInfo.class);
//            Long quantity = dataSnapshot.child("Quantity").getValue(Long.class);
//            data.add(description+" ("+quantity+")");
//            myArrayAdapter.notifyDataSetChanged();
//            liquor drink= new liquor();
//            drink.setLiqName((ds.getValue(liquor.class)).getLiqName().toString());
//            drink.setLiqPrice(data.toString());
//            drink.setImageUrl((ds.getValue(liquor.class)).getImageUrl());


//            drink.setLiqGroup(ds.getValue(liquor.class).toString());
//            data.add(drink);
//            for (liquor aData : data) {
//                System.out.println(aData);

            liquor drink = new liquor();
            drink.setLiqName(ds.getValue(liquor.class).getLiqName());
            drink.setLiqPrice(ds.getValue(liquor.class).getLiqPrice());
            drink.setimageUrl(ds.getValue(liquor.class).getimageUrl());

            data.add(drink);
        }

        if (data.size() > 0) {
            adapter = new customAdapter(c, data) {
            };
            rv.setAdapter(adapter);
        } else {
            Toast.makeText(c, "Try Again", LENGTH_SHORT).show();
        }
    }


    //READ BY HOOKING ONTO DATABASE OPERATION CALLBACKS
    public void refreshData() {
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    fetchData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(c,"Error",LENGTH_SHORT).show();
            }
        });
        }
}
