package com.example.home.lavia;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

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
    }



}
