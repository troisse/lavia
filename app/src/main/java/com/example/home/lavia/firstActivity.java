package com.example.home.lavia;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDex;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
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
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class firstActivity extends AppCompatActivity implements View.OnClickListener{

        String pUser;
        String pContact;
        Dialog epicDialog;
        ImageView close;
        EditText inputName, inputPassword,user,email,contact,password1, password2;
        Button btnSignIn, btnSignUp, btnResetPassword;
        TextView txty;
        static FirebaseAuth auth ;
        static FirebaseUser firebaseUser;
        static DatabaseReference ref;
        ProgressDialog mProgress;
        static FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

        @SuppressLint("NewApi")
        @Override
        public void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_first_page);
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(ContextCompat.getColor(firstActivity.this, R.color.colorPrimaryDark));
                }else {
                getWindow().setStatusBarColor(ContextCompat.getColor(firstActivity.this, R.color.colorPrimaryDark));
            }
                epicDialog= new Dialog(firstActivity.this);


            txty =findViewById(R.id.textView9);

            mProgress = new ProgressDialog(firstActivity.this);
            mProgress.setTitle("Hey,There");
            mProgress.setMessage("Please wait...");
            mProgress.setCancelable(false);
            mProgress.setIndeterminate(true);

            auth = FirebaseAuth.getInstance();

            inputName = findViewById(R.id.email);
            btnSignIn = findViewById(R.id.button);
            inputPassword = findViewById(R.id.password);


            mAuthListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    firebaseUser = firebaseAuth.getCurrentUser();

                    if (firebaseUser != null) {
                            Intent i = new Intent(firstActivity.this,outletActivity.class);
                            startActivity(i);

                    }
                }
            };

        }


    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(mAuthListener);
    }


    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }
    public void Access(){
        final String name = inputName.getText().toString();
        final String password = inputPassword.getText().toString();
        //authenticate user
        auth.signInWithEmailAndPassword(name, password)
                .addOnCompleteListener(firstActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.

                        if (task.isSuccessful()) {

                            Intent intent = new Intent(getApplicationContext(), outletActivity.class);
                            startActivity(intent);

                        }}
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                }
        });
        mProgress.dismiss();
    }
        @Override
        public void onClick(View view) {
            if (view == txty) {
                showPopup();
            }else if (!isOnline()){
                Toast.makeText(firstActivity.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
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
            close = epicDialog.findViewById(R.id.close);
            btnSignUp = epicDialog.findViewById(R.id.sign_up);
            user = epicDialog.findViewById(R.id.mUser);
            email = epicDialog.findViewById(R.id.mEmail);
            contact= epicDialog.findViewById(R.id.mContact);
            password1 = epicDialog.findViewById(R.id.mPassword1);
            password2= epicDialog.findViewById(R.id.mPassword2);

            assert epicDialog.getWindow() !=null;
            epicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            epicDialog.show();
        }

         void post(){
        pUser= user.getText().toString().trim();
        pContact= contact.getText().toString().trim();
        final String pEmail = email.getText().toString().trim();
        final String pPassword= password1.getText().toString().trim();

             ref = FirebaseDatabase.getInstance().getReference("lavia/Nairobi/Users");

             auth.createUserWithEmailAndPassword(pEmail, pPassword)
                    .addOnCompleteListener(firstActivity.this,new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if (task.isSuccessful()) {

                                                    assert task.getResult() !=null;
                                                    String unique=task.getResult().getUser().getUid();
                                                    AddUserInfo(unique);

                                                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                                            .setDisplayName(pUser).build();

                                                    firebaseUser.updateProfile(profileUpdates);

                                                    Intent intent = new Intent(getApplicationContext(), outletActivity.class);
                                                    startActivity(intent);

                                                } else {
                                                    mProgress.dismiss();
                                                    Toast.makeText(epicDialog.getContext(), "Oops! Sign Up Again", Toast.LENGTH_LONG).show();
                                                }

                                                }
                    });

        }


    private void AddUserInfo(String id) {
        User user=new User();
        user.getUserId(id);
        user.setUsername(pUser);
        user.setContact(pContact);
        ref.child(id).setValue(user);
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
            }else if (pUser.length() >  12){
                user.setError("Sorry, Try less than 12 characters");
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
mProgress.show();

                isCheckEmail(pEmail,new OnEmailCheckListener(){
                    public void onSuccess(boolean isRegistered){

                        if(isRegistered){
                            mProgress.dismiss();

                            Toast.makeText(getApplicationContext(),"Try a Different Email Address Already Registered ",Toast.LENGTH_SHORT).show();
                        } else {
                            //The email not registered before
                            post();
                        }

                    }
                });

                                }
                            }




    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    interface OnEmailCheckListener{
        void onSuccess(boolean isRegistered);
        }

        private void login() {
            final String name = inputName.getText().toString();
                final String password = inputPassword.getText().toString();

                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(getApplicationContext(), "Enter Email Address", Toast.LENGTH_SHORT).show();
                } else  if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password", Toast.LENGTH_SHORT).show();
                }else if (password.length() <  6) {
                    inputPassword.setError("Password Should be 6 or more Characters.");
                }else if (!isEmailValid(name)){
                        inputName.setError("Your Email Address is Invalid");
                    }
                else
                {

                    mProgress.show();

                    isCheckEmail(name,new OnEmailCheckListener(){

                        public void onSuccess(boolean isRegistered){

                            if(isRegistered){
                                Access();
                            } else {
                                mProgress.dismiss();

                                Toast.makeText(firstActivity.this,"Email doen't exist, SignUp!",Toast.LENGTH_LONG).show();
                                //The email not registered before
                            }

                        }
                    });

                }
            }

    public void isCheckEmail(final String email,final OnEmailCheckListener listener){
        auth.fetchSignInMethodsForEmail(email).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
            @Override
            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                if(isEmailValid(email)) {

                    assert task.getResult()!=null;
                    assert task.getResult().getSignInMethods() != null;
                    boolean check = !task.getResult().getSignInMethods().isEmpty();

                    listener.onSuccess(check);
                }
            }

        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Lavia");
        alertDialogBuilder
                .setMessage("Leaving so soon?")
                .setCancelable(false)
                .setPositiveButton("Yes Please",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                moveTaskToBack(true);
                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(1);
                            }
                        })

                .setNegativeButton("Not Yet", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
        }


