package com.example.home.lavia;
import android.app.Dialog;
    import android.app.ProgressDialog;
    import android.content.Intent;
    import android.graphics.Color;
    import android.graphics.drawable.ColorDrawable;
    import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
    import android.os.Bundle;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

    public class firstActivity extends AppCompatActivity implements View.OnClickListener{
        Dialog epicDialog;
        ImageView close;
        EditText inputName, inputPassword,user,email,contact,password1, password2;
        Button btnSignIn, btnSignUp, btnResetPassword;
        TextView txty;
        private FirebaseAuth auth ;
        DatabaseReference ref;
//        ProgressDialog progressBar;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_first_page);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
//            progressBar = new ProgressDialog(firstActivity.this);

            epicDialog= new Dialog(firstActivity.this);


            txty = (TextView) findViewById(R.id.textView9);

            ref = FirebaseDatabase.getInstance().getReference();
            auth = FirebaseAuth.getInstance();

            inputName = (EditText) findViewById(R.id.email);
            btnSignIn = (Button) findViewById(R.id.button);
            inputPassword = (EditText) findViewById(R.id.password);

            if (auth.getCurrentUser() != null) {
                finish();
                startActivity(new Intent(firstActivity.this, outletActivity.class));
                finish();
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
        private void save() {

            final String pEmail= email.getText().toString().trim();
            String pPassword= password1.getText().toString().trim();
            final String pUser= user.getText().toString().trim();
            String pContact= contact.getText().toString().trim();
            final String cPassword =password2.getText().toString().trim();

            if (pPassword.equals(cPassword))
            {
            if (pUser.isEmpty())
            {
                user.setError("Kindly Fill This Field");
            return;
            }else if (pEmail.isEmpty())
            {
                email.setError("Kindly Fill This Field");
                return;
            }else if (pContact.isEmpty())
            {
                contact.setError("Kindly Fill This Field");
            return;
            }else if (pPassword.isEmpty())
            {
                    password1.setError("Kindly Fill in Password Correctly");
            return;
            }

//                progressBar.setMessage("Saving Data...");
//                progressBar.show();

//                progressBar.dismiss();


            }else {
                Toast.makeText(this, "Passwords Don't Match", Toast.LENGTH_SHORT).show();
            }
            auth.createUserWithEmailAndPassword(pEmail,pPassword)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                ref .child("Nairobi/").child("User/").child(pUser).setValue(contact)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()){

                                                    Intent intent = new Intent(epicDialog.getContext(),outletActivity.class);
                                                    startActivity(intent);
                                                }else {
                                                    Toast.makeText(epicDialog.getContext(),"Oops! Sign Up Again",Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        });
                            }
                        }
                    })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(epicDialog.getContext(),pEmail+"Oops! Sign Up Again",Toast.LENGTH_LONG).show();

                }
            });


        }
        private void login() {

            Intent intent = new Intent(firstActivity.this,outletActivity.class);
            startActivity(intent);

                String name = inputName.getText().toString();
                final String password = inputPassword.getText().toString();

                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(getApplicationContext(), "Enter Email Address", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password", Toast.LENGTH_SHORT).show();
                    return;
                }


                //authenticate user
                Task<AuthResult> authResultTask = auth.signInWithEmailAndPassword(name, password)
                        .addOnCompleteListener(firstActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.

                                if (!task.isSuccessful()) {
                                    // there was an error
                                    if (password.length() < 5) {
                                        inputPassword.setError(getString(R.string.minimum_password));
                                    } else {
                                        Toast.makeText(firstActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Intent intent = new Intent(firstActivity.this, outletActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
            }

        }


