package com.example.home.lavia;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.widget.Toast.LENGTH_SHORT;

public class firebaseClient {

    @SuppressLint("StaticFieldLeak")
    private static Context c;
    private static DatabaseReference db;
    private RecyclerView rv;
    static ArrayList<liquor> purchase= new ArrayList<>();
    static ProgressDialog progressDialog;

    firebaseClient(Context c, DatabaseReference db, RecyclerView mRecyclerView) {
        firebaseClient.c = c;
        firebaseClient.db = db;
        this.rv = mRecyclerView;

        //INITIALIZE
        Firebase.setAndroidContext(c);

    }


    private void fetchData(DataSnapshot ds) {

        purchase.clear();

        for (DataSnapshot snap:ds.getChildren()) {
            String liq=snap.getKey();
            UploadInfo value = snap.getValue(UploadInfo.class);

            assert value != null;

//            String liqName = value.getLiqName();
            String liqPrice = value.getLiqPrice();
            String imageUrl = value.getImageUrl();

        final liquor fire = new liquor();

        fire.setliqName(liq);
        fire.setliqPrice(liqPrice);
        fire.setimageUrl(imageUrl);

        purchase.add(fire);
    }
        if (purchase != null) {
            customAdapter adapter = new customAdapter(c, purchase, new customAdapter.OnRecyclerViewItemClickedListener() {
                @Override
                public void OnItemClick(liquor item) {

                    Intent i = new Intent(c.getApplicationContext(), detailActivity.class);

                    String liqName = item.getliqName();
                    String liqPrice = item.getliqPrice();
                    String url = item.getimageUrl();

                    i.putExtra(detailActivity.EXTRA_NAME,liqName);
                    i.putExtra(detailActivity.EXTRA_PRICE,liqPrice);
                    i.putExtra(detailActivity.EXTRA_URL,url);

                    c.startActivity(i);


                }
                });
            rv.setAdapter(adapter);
            } else {
            checkConnection();
            }
    progressDialog.dismiss();
    }

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager)c.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public void checkConnection(){
        if(!isOnline()){
            Toast.makeText(c, "Check Internet Connection", Toast.LENGTH_SHORT).show();
            }else {
            Toast.makeText(c, "Service Unavailable", LENGTH_SHORT).show();
        }
    }

    public static void remove(final String buyName) {

        db.child(buyName).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        dataSnapshot.getRef().removeValue();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(c, databaseError.getMessage(), LENGTH_SHORT).show();
                    }

                });

            }



//UploadInfo niw= new UploadInfo();
    //READ BY HOOKING ONTO DATABASE OPERATION CALLBACKS
ArrayList<liquor> refreshData(final ProgressDialog progressDialog) {
        firebaseClient.progressDialog = progressDialog;
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                System.out.println(dataSnapshot);

                fetchData(dataSnapshot);
                }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(c, databaseError.getMessage(), LENGTH_SHORT).show();
            }
        });

        return purchase;
    }

}
