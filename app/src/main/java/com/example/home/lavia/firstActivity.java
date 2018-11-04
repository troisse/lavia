package com.example.home.lavia;
import android.annotation.SuppressLint;
import android.app.Dialog;
    import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
    import android.graphics.Color;
    import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
    import android.os.Bundle;
    import android.support.v7.widget.Toolbar;
    import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
    import android.widget.EditText;
    import android.widget.ImageView;
import android.widget.TextView;
    import android.widget.Toast;

    import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
    import com.google.firebase.auth.AuthResult;
    import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class firstActivity extends AppCompatActivity implements View.OnClickListener{
        Dialog epicDialog;
        ImageView close;
        EditText inputName, inputPassword,user,email,contact,password1, password2;
        Button btnSignIn, btnSignUp, btnResetPassword;
        TextView txty;
        private FirebaseAuth auth ;
        DatabaseReference ref;
//        ProgressDialog progressBar;

        @SuppressLint("NewApi")
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_first_page);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
//            progressBar = new ProgressDialog(firstActivity.this);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(ContextCompat.getColor(firstActivity.this, R.color.colorPrimaryDark));
                }
//            Window window = ac.getWindow();
//
//// clear FLAG_TRANSLUCENT_STATUS flag:
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//
//// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//
//// finally change the color
//            window.setStatusBarColor(getParent().getResources().getColor(R.color.colorPrimaryDark));

            epicDialog= new Dialog(firstActivity.this);


            txty = (TextView) findViewById(R.id.textView9);


            auth = FirebaseAuth.getInstance();

            inputName = (EditText) findViewById(R.id.email);
            btnSignIn = (Button) findViewById(R.id.button);
            inputPassword = (EditText) findViewById(R.id.password);

//            if (auth.getCurrentUser() != null) {
//                finish();
//                startActivity(new Intent(firstActivity.this, outletActivity.class));
//                finish();
//            }

        }

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }
    public void checkConnection(){
        if(isOnline()){
            Intent intent = new Intent(getApplicationContext(), outletActivity.class);
            startActivity(intent);
        }else{
            Toast.makeText(firstActivity.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }
        @Override
        public void onClick(View view) {
            if (view == txty) {
                showPopup();
            }else if (view == btnSignUp){
                save();
            }else if (view == btnSignIn){
                login();
            }else if (view == close){
                epicDialog.dismiss();
            }
        }

        private void showPopup(){
            epicDialog.setContentView(R.layout.sign_dialog);
//            epicDialog.setContentView(progressBar.getProgress());
            close = (ImageView)epicDialog.findViewById(R.id.close);
            btnSignUp = (Button)epicDialog.findViewById(R.id.sign_up);
            user =(EditText)epicDialog.findViewById(R.id.mUser);
            email =(EditText)epicDialog.findViewById(R.id.mEmail);
            contact=(EditText)epicDialog.findViewById(R.id.mContact);
            password1 =(EditText)epicDialog.findViewById(R.id.mPassword1);
            password2=(EditText)epicDialog.findViewById(R.id.mPassword2);

            epicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            epicDialog.show();
        }

        private void post(){
        final String pUser= user.getText().toString().trim();
        String pContact= contact.getText().toString().trim();
        String pEmail = email.getText().toString().trim();

        ref = FirebaseDatabase.getInstance().getReference().child("Nairobi/").child("Users/");
        ref.child(pEmail).child(pUser).setValue(pContact)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                            checkConnection();
//
//                                Intent intent = new Intent(epicDialog.getContext(), outletActivity.class);
//                                startActivity(intent);
                        } else {
                            Toast.makeText(epicDialog.getContext(), "Oops! Sign Up Again", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

        private void save() {

            final String pEmail= email.getText().toString().trim();
            final String pPassword= password1.getText().toString().trim();
            final String pUser= user.getText().toString().trim();
            String pContact= contact.getText().toString().trim();
            final String cPassword =password2.getText().toString().trim();

            if (pUser.isEmpty())
            {
                user.setError("Fill This Field");
            }else if (pEmail.isEmpty())
            {
                email.setError("Fill This Field");
            }else if (!isEmailValid(pEmail)){
                email.setError("Your Email Address is Invalid");
            }else if (pContact.isEmpty())
            {
                contact.setError("Fill This Field");
            }else if (pPassword.isEmpty())
            {
                    password1.setError("Fill in Password ");
            }else if (!cPassword.equals(pPassword))
            {
                Toast.makeText(this, "Passwords Don't Match", Toast.LENGTH_SHORT).show();
            }else if (pPassword.length() <  6) {
                    password1.setError("Password Should be 6 or more Characters.");
            }else {
//                DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("Nairobi").child("User/");
////                db.addListenerForSingleValueEvent(new ValueEventListener()
//                        ValueEventListener eventListener = new ValueEventListener(){
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                if (dataSnapshot.exists()) {
//                                    Log.v(pEmail, " Account Already Exists");
//                                } else {

                                    try {
                                        auth.createUserWithEmailAndPassword(pEmail, pPassword)
                                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                                        if (task.isSuccessful()) {
                                                            post();
                                                        }
                                                    }
                                                })

                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(epicDialog.getContext(), " Oops! Sign Up Again", Toast.LENGTH_LONG).show();

                                                    }
                                                });

                                    }
                                    catch (Exception e) {
                                        /* This is a generic Exception handler which means it can handle
                                         * all the exceptions. This will execute if the exception is not
                                         * handled by previous catch blocks.
                                         */
                                        System.out.println("Oops! Try Again");
                                    }

                                }
                            }
//
//                            @Override
//                            public void onCancelled(DatabaseError databaseError) {
//                                Toast.makeText(getApplicationContext(),"Oops! Try Again!",Toast.LENGTH_SHORT).show();
//                            }
//                        };
//                db.addListenerForSingleValueEvent(eventListener);
//                progressBar.setMessage("Saving Data...");
//                progressBar.show();

//                progressBar.dismiss();





    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

        private void login() {
            checkConnection();

            final String name = inputName.getText().toString();
                final String password = inputPassword.getText().toString();

                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(getApplicationContext(), "Enter Email Address", Toast.LENGTH_SHORT).show();
                    //return;
                } else  if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password", Toast.LENGTH_SHORT).show();
                    //return;
                }else if (password.length() <  6) {
                    inputPassword.setError("Password Should be 6 or more Characters.");
//                    return;
                }else if (!isEmailValid(name)){
                        inputName.setError("Your Email Address is Invalid");
                    }
                else
                {


                    //authenticate user
                    auth.signInWithEmailAndPassword(name, password)
                            .addOnCompleteListener(firstActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.

                                    if (task.isSuccessful()) {
                                        Intent intent = new Intent(firstActivity.this, outletActivity.class);
                                        startActivity(intent);
                                    }else {
                                        checkConnection();
                                    }
                                }
                            });
                }
            }

        }


