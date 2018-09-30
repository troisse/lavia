package com.example.home.lavia;


import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.ImageButton;
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
import static android.content.Context.MODE_PRIVATE;


public class productsTab extends Fragment implements View.OnClickListener {

    private static final int PICK_IMAGE_REQUEST = 234;
    private DatabaseReference ref;
    private StorageReference storageReference;
    int position;
    private StorageTask mUploadTask;
    Button imageButton;
    Button Upload;
    ImageView imageView;
    EditText imageName, imagePrice, imageGroup;
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

        imageGroup = (EditText) view.findViewById(R.id.liq_group);
        imageName = (EditText)view.findViewById(R.id.liq_name);
        imagePrice = (EditText) view.findViewById(R.id.liq_price);
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
        final ImageUploadInfo liq = new ImageUploadInfo();
        final String Group = imageGroup.getText().toString().trim();
        final String Name = imageName.getText().toString().trim();
        String Price = imagePrice.getText().toString().trim();


        if (Name.isEmpty())
        {
            imageName.setError("Kindly Fill This Input");
            return;
        }else if (Group.isEmpty())
        {
            imageGroup.setError("Kindly Fill This Input");
            Toast.makeText(getActivity(), "Fill All Inputs", Toast.LENGTH_LONG).show();
            return;
        }else if (Price.isEmpty())
        {
            imagePrice.setError("Kindly Fill This Input");
            Toast.makeText(getActivity(), "Fill All Inputs", Toast.LENGTH_LONG).show();
            return;
        }
        long time = System.currentTimeMillis();

//        liq.setImageGroup(imageGroup.getText().toString().trim());
//        liq.setImageName(imageName.getText().toString().trim());
//        liq.setImagePrice(imagePrice.getText().toString().trim());
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
        String Outlet = outlet.selectedIype.toLowerCase().trim();
        ref = FirebaseDatabase.getInstance().getReference().child("Nairobi/").child("Liquor/").child(Outlet);
        ref.child(Group).child(time + "/" + Name).child("Price").setValue(Price)
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

        if (fileUri != null){
            if (fileUri != null){
                final StorageReference filePath = storageReference.child(Group).child(Name);
                filePath.putFile(fileUri);
                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Toast.makeText(getActivity(), "Upload Successful", Toast.LENGTH_LONG).show();
                        //ImageUploadInfo imageUploadInfo = new ImageUploadInfo();
//                        String uploadId = ref.push().getKey();
//                        ref.child(Group).child(Name).child(uploadId).setValue(uri);
                    }


                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
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
        imageView.setImageDrawable(null);

    }
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
            Picasso.get().load(fileUri).into(imageView);

            //StorageReference filePath = storageReference.child("liquor").child(uri.getLastPathSegment());
        }

    }
    private void Cleartxt(){
        imageGroup.setText("");
        imageName.setText("");
        imagePrice.setText("");
    }

}
