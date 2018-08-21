package com.example.home.lavia;


import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;


import java.util.ArrayList;

/**
 * 1.SAVE DATA TO FIREBASE
 * 2. RETRIEVE
 * 3.RETURN AN ARRAYLIST
 */
public class FirebaseHelper {

    private DatabaseReference db;
    private ArrayList<CustomAdapter> liqs=new ArrayList<>();

    /*
 PASS DATABASE REFRENCE
  */
    public FirebaseHelper(DatabaseReference db) {
        this.db = db;
    }
    //WRITE IF NOT NULL
    public Boolean save(ImageUploadInfo liq)
    {
        Boolean saved;
        if(liq==null)
        {
            saved =false;
        }else
        {
            try
            {
                db.child("Comfort/").push().setValue(liq);
                saved =true;

            }catch (DatabaseException e)
            {
                e.printStackTrace();
                saved =false;
            }
        }

        return saved;
    }

    //IMPLEMENT FETCH DATA AND FILL ARRAYLIST
    private void fetchData(DataSnapshot dataSnapshot)
    {
        liqs.clear();

        for (DataSnapshot ds : dataSnapshot.getChildren())
        {
            CustomAdapter liq=ds.getValue(CustomAdapter.class);
            liqs.add(liq);
        }
    }

    //RETRIEVE
    public ArrayList<CustomAdapter> retrieve()
    {
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

        return liqs;
    }

}
