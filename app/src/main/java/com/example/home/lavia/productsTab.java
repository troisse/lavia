package com.example.home.lavia;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;

import static android.app.Activity.RESULT_OK;


public class productsTab extends Fragment implements View.OnClickListener {

    private static final int PICK_IMAGE_REQUEST = 234;
    private DatabaseReference ref;
    private StorageReference storageReference;
    int position;
    private StorageTask mUploadTask;
    Button imageButton;
    Button Upload;
    ImageView imageView;
    EditText liqName, liqPrice, liqGroup;
    Uri fileUri;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt("pos");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.products_tab, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageButton = (Button) view.findViewById(R.id.button_image);
        Upload = (Button) view.findViewById(R.id.upload);
        Upload.setOnClickListener(productsTab.this);
        imageButton.setOnClickListener(productsTab.this);
//        textView.setText("Fragment " + (position + 1));

        liqGroup = (EditText) view.findViewById(R.id.liq_group);
        liqName = (EditText)view.findViewById(R.id.liq_name);
        liqPrice = (EditText) view.findViewById(R.id.liq_price);
        imageView = (ImageView) view.findViewById(R.id.imageView);

        storageReference = FirebaseStorage.getInstance().getReference("Images/" );

    }
    public void onClick(View view) {
        if (view == imageButton) {
            showFileChooser();

        } else if (view == Upload) {
            if (mUploadTask !=null &&mUploadTask.isInProgress()){
                Toast.makeText(getActivity(),"Please Wait...",Toast.LENGTH_LONG). show();
            }else {
                save();
            }
        }
    }
    public void save() {
//        liq.setLiqGroup(liqGroup.getText().toString());

      final String Group = liqGroup.getText().toString().trim();
        final String Name = liqName.getText().toString().trim();
        final String Price = liqPrice.getText().toString().trim();


        if (Name.isEmpty())
        {
            liqName.setError("Kindly Fill This Field");
            return;
        }else if (Group.isEmpty())
        {
            liqGroup.setError("Kindly Fill This Field");
            return;
        }else if (Price.isEmpty())
        {
            liqPrice.setError("Kindly Fill This Field");
            return;
        }
        long time = System.currentTimeMillis();

//        liq.setLiqGroup(liqGroup.getText().toString().trim());
//        liq.setLiqName(liqName.getText().toString().trim());
//        liq.setLiqPrice(liqPrice.getText().toString().trim());
//        Intent intent = getIntent();
//        store.setText(intent.getStringExtra("store"));
//        String store =intent.getStringExtra("store");
//       selectedType.setSelectedType(intent.getStringExtra("store"));
//        String outlet= getText(Integer.parseInt(intent.getStringExtra("store"))).toString().trim();
//        liq.setSelectedType(store.getText().toString().trim());
//        liq.setImageUrl(getFileExtension(fileUri));

//        SharedPreferences prefs = getActivity().getSharedPreferences("db",MODE_PRIVATE);
//        DatabaseReference ref = databaseReference;
//        outlet store= new outlet();
//        String Outlet = outletActivity.selectedIype.toLowerCase().trim();

        if (fileUri != null){
                final StorageReference filePath = storageReference;
                filePath.putFile(fileUri);
                filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                         Toast.makeText(getActivity(), "Upload Successful", Toast.LENGTH_LONG).show();
                        //UploadInfo imageUploadInfo = new UploadInfo();
//                        String uploadId = ref.push().getKey();
//                        ref.child(Group).child(Name).setValue(uri);
                        final UploadInfo liq = new UploadInfo();
                        liq.setLiqName(liqName.getText().toString());
                        liq.setLiqPrice(liqPrice.getText().toString());
                        liq.setImageUrl(uri.toString());

                        ref = FirebaseDatabase.getInstance().getReference().child("Nairobi/").child("Liquor/");
                        ref.child(Group).setValue(liq)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(getActivity(), "Product Added Successfully", Toast.LENGTH_LONG).show();
                                            Cleartxt();
                                        } else {
                                            Toast.makeText(getActivity(), "Failed. Try Again", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                    }


                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getActivity(), "Capture Image", Toast.LENGTH_SHORT).show();
                            }
                        });
//                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                            @Override
//                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
//                                double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
//                                progressBar.setProgress((int) progress);
//                            }
//                        })
            }else {
                Toast.makeText(getActivity(),"No Image Selected",Toast.LENGTH_SHORT).show();
            }
        }
//        imageView.setImageDrawable(null);


    protected void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.INTERNAL_CONTENT_URI);
//        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select an Image"), PICK_IMAGE_REQUEST);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data !=null && data.getData() != null){
            fileUri =data.getData();
            if (fileUri!=null ) {
                Picasso.get().load(fileUri).placeholder(R.drawable.gucci).into(imageView);
            }else {
                Picasso.get().load(R.drawable.gucci).into(imageView);
            }
            //StorageReference filePath = storageReference.child("liquor").child(uri.getLastPathSegment());
        }

    }
    private void Cleartxt(){
        liqGroup.setText("");
        liqName.setText("");
        liqPrice.setText("");
    }

}
