//package com.kingwanyama.nuruapp;
//
//import android.annotation.SuppressLint;
//import android.app.ProgressDialog;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.net.Uri;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.v7.app.AppCompatActivity;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//
//public class Group22Activity extends AppCompatActivity {
//
//    EditText input_name, input_email, input_age;
//    ProgressDialog progress;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_group22);
//        input_name = findViewById(R.id.inputTitles);
//        input_email = findViewById(R.id.inputVerses);
//        input_age = findViewById(R.id.inputElaborate);
//    }
//
//    public void save(View view) {
//        String names = input_name.getText().toString();
//        String email = input_email.getText().toString();
//        String age = input_age.getText().toString();
//        progress = new ProgressDialog(this);
//        progress.setMessage("Posting your notice...");
//
//        if (names.isEmpty())
//        {
//            input_name.setError("Kindly Fill This Input");
//            return;
//        }else if (email.isEmpty())
//        {
//            input_email.setError("Kindly Fill This Input");
//            Toast.makeText(this, "Fill All Inputs", Toast.LENGTH_LONG).show();
//            return;
//        }else if (age.isEmpty())
//        {
//            input_age.setError("Kindly Fill This Input");
//            Toast.makeText(this, "Fill All Inputs", Toast.LENGTH_LONG).show();
//            return;
//        }
//        long time = System.currentTimeMillis();
//        long unique = System.currentTimeMillis();
//        String uniq = String.valueOf(unique);
//
//        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Group22/"+time);
//        SharedPreferences prefs =getSharedPreferences("db",MODE_PRIVATE);
//        String imei=prefs.getString("phone","");
//        Post x = new Post(names,email,age,imei,uniq);
//        progress.show();
//        ref.setValue(x).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                progress.dismiss();
//                if (task.isSuccessful())
//                {
//                    input_name.setText("");
//                    input_email.setText("");
//                    input_age.setText("");
//                    Toast.makeText(Group22Activity.this, "Notice Posted Successfully", Toast.LENGTH_LONG).show();
//                    Intent view = new Intent(getApplicationContext(),ViewGroup22Activity.class);
//                    startActivity(view);
//                }else
//                {
//                    Toast.makeText(Group22Activity.this, "Failed. Try Again", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//
//
//    }
//
//    public void fetch(View view) {
//        Intent intent = new Intent(getApplicationContext(),ViewGroup22Activity.class);
//        startActivity(intent);
//    }
//
//    //Call Leader
//    @SuppressLint("MissingPermission")
//    public void call(View view) {
//        String number = "0714359957";
//        Intent callIntent = new Intent(Intent.ACTION_CALL);
//        callIntent.setData(Uri.parse("tel:" + number));
//        startActivity(callIntent);
//    }
//}