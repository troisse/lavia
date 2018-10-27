package com.example.home.lavia;


import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class signDialog extends DialogFragment {

    Button login;
    EditText mEmail,mUser,mContact, mPassword, mConfirm;

    public signDialog(){

    }
    public static signDialog newInstance(String title) {
        signDialog frag = new signDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup promptView,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.sign_dialog, null);

    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);





//        LayoutInflater layoutInflater = LayoutInflater.from(signDialog.this.getActivity());
//        View promptView = layoutInflater.inflate(R.layout.sign_dialog,null);
//        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
//                signDialog.this.getActivity());
//        alertDialogBuilder.setView(promptView);
//        alertDialogBuilder
//                .setCancelable(false)
//                .setPositiveButton("sign up!", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        Toast.makeText(getActivity().getApplicationContext(),"OK CLICKED",Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .setNegativeButton("Exit",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                dialog.cancel();
//                            }
//                        });
//
//        AlertDialog alert = alertDialogBuilder.create();
//        alert.show();




//        login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!mEmail.getText().toString().isEmpty()&& !mUser.getText().toString().isEmpty()&&
//                        !mContact.getText().toString().isEmpty() && !mPassword.getText().toString().isEmpty() &&
//                        !mConfirm.getText().toString().isEmpty() && mPassword==mConfirm ){
//                    Intent intent = new Intent(getContext(),outletActivity.class);
//                    startActivity(intent);
//
//                }else {
//                    Toast.makeText(getContext(),"Fill in all Fields",Toast.LENGTH_LONG);
//                }
//
//            }
//
//        });
//        getDialog().getWindow().setSoftInputMode(
//                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
//        getDialog().setTitle(txt.getText());
//        getDialog().setContentView(R.layout.sign_dialog);

    }



}
