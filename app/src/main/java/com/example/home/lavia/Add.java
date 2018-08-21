package com.example.home.lavia;


import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;


public class Add extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,OnClickListener{
    ImageView imageView;

    // Root Database Name for Firebase Database.
    EditText imageGroup;
    EditText imageName;
    EditText imagePrice;
    TextView textView;
    // Creating StorageReference and DatabaseReference object.
    StorageReference storageReference;
    DatabaseReference databaseReference;
private StorageTask mUploadTask;
    // Image request code for onActivityResult() .
    ProgressBar progressBar;
    ImageButton imageButton;
    Button Upload;
    String radioChosen;
    Uri fileUri;
    RadioGroup radioGroup;
    private static final int PICK_IMAGE_REQUEST = 234;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        radioChosen = getIntent().getStringExtra("radioChosen");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView tv = (TextView)findViewById(R.id.tv);
        Intent intent = getIntent();
        radioChosen = intent.getStringExtra("radioChosen");
        tv.setText(radioChosen);


        storageReference = FirebaseStorage.getInstance().getReference("Images");
        databaseReference = FirebaseDatabase.getInstance().getReference();


        imageView = (ImageView) findViewById(R.id.imageView);
        imageGroup = (EditText) findViewById(R.id.liq_group);
        imageName = (EditText)findViewById(R.id.liq_name);
        imagePrice = (EditText) findViewById(R.id.liq_price);
        imageButton = (ImageButton) findViewById(R.id.button_image);
        Upload = (Button)findViewById(R.id.upload);

        Upload.setOnClickListener((OnClickListener) Add.this);
        imageButton.setOnClickListener((OnClickListener) Add.this);
// Assigning Id to ProgressBar.
        progressBar = new ProgressBar(Add.this);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

//    private Object findViewById(String radioChosen) {
//        int ra
//    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select an Image"), PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data !=null && data.getData() != null){
            fileUri = data.getData();
            Picasso.get().load(fileUri).into(imageView);

//            StorageReference filePath = storageReference.child("liquor").child(fileUri.getLastPathSegment());
        }

    }
    private String getFileExtension(Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime =  MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile(){
        final ImageUploadInfo liq = new ImageUploadInfo();


        final String Group = imageGroup.getText().toString().trim();
        String Name = imageName.getText().toString().trim();
        String Price = imagePrice.getText().toString().trim();
        Long timing = System.currentTimeMillis();
        String time = String.valueOf(timing);


        if (TextUtils.isEmpty(Group)){
            Toast.makeText(Add.this, "Add liquor group", Toast.LENGTH_LONG).show();
        }else if (TextUtils.isEmpty(Name)){
            Toast.makeText(Add.this, "Add liquor name", Toast.LENGTH_LONG).show();
        }else if (TextUtils.isEmpty(Price)){
            Toast.makeText(Add.this, "Add liquor price", Toast.LENGTH_LONG).show();
        }else {

            String id = databaseReference.push().getKey();


            liq.setImageGroup(imageGroup.getText().toString().trim());
            liq.setImageName(imageName.getText().toString().trim());
            liq.setImagePrice(imagePrice.getText().toString().trim());
            liq.setImageUrl(getFileExtension(fileUri));
//            String filePath =fileUri.getLastPathSegment();
            databaseReference.child("Liquor").child(Group).child(time+"/"+Name).child("Price").setValue(Price);
            //databaseReference.child().child("Brand").setValue(Name);
           //databaseReference.child(Name).child("Price").setValue(Price);
            Cleartxt();
        }
        if (fileUri != null){
            if (fileUri != null){
                final StorageReference filePath = storageReference.child(System.currentTimeMillis()
                        + "." + getFileExtension(fileUri));
                mUploadTask = filePath.putFile(fileUri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Toast.makeText(Add.this, "Product Added", Toast.LENGTH_LONG).show();
                                String uploadId = databaseReference.push().getKey();
                                databaseReference.child("Liquor").child(Group).child("Images").child(uploadId).setValue(liq);
                            }


                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Add.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
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
                Toast.makeText(this,"No Image Selected",Toast.LENGTH_SHORT).show();
            }
        }
        imageView.setImageDrawable(null);
    }
    private void Cleartxt(){
        imageGroup.setText("");
        imageName.setText("");
        imagePrice.setText("");
    }

                      public void onClick(View view) {
                        if (view == imageButton) {
                            showFileChooser();

                        } else if (view == Upload) {
                            if (mUploadTask !=null &&mUploadTask.isInProgress()){
                                Toast.makeText(Add.this,"Please Wait...",Toast.LENGTH_LONG). show();
                            }else {
                            uploadFile();
                        }
                    }
                    }
                    @Override
                    public void onBackPressed() {
                        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                        if (drawer.isDrawerOpen(GravityCompat.START)) {
                            drawer.closeDrawer(GravityCompat.START);
                        } else {
                            super.onBackPressed();
                        }
                    }

                    @Override
                    public boolean onCreateOptionsMenu(Menu menu) {
                        // Inflate the menu; this adds items to the action bar if it is present.
                        getMenuInflater().inflate(R.menu.add, menu);
                        return true;
                    }

                    @Override
                    public boolean onOptionsItemSelected(MenuItem item) {
                        // Handle action bar item clicks here. The action bar will
                        // automatically handle clicks on the Home/Up button, so long
                        // as you specify a parent activity in AndroidManifest.xml.
                        int id = item.getItemId();

                        //noinspection SimplifiableIfStatement
                        if (id == R.id.action_settings) {
                            return true;
                        }

                        return super.onOptionsItemSelected(item);
                    }

                    @SuppressWarnings("StatementWithEmptyBody")
                    @Override
                    public boolean onNavigationItemSelected(MenuItem item) {
                        // Handle navigation view item clicks here.
                        int id = item.getItemId();

                        if (id == R.id.leakage) {
                            Intent camshot = new Intent(getApplicationContext(), Add.class);
                            startActivity(camshot);
                        } else if (id == R.id.whiskey) {
                            Intent camshot = new Intent(getApplicationContext(), Whiskey.class);
                            startActivity(camshot);
                        } else if (id == R.id.vodka) {
                            Intent camshot = new Intent(getApplicationContext(), Vodka.class);
                            startActivity(camshot);
                        } else if (id == R.id.home) {
                            Intent camshot = new Intent(getApplicationContext(), Home.class);
                            startActivity(camshot);
                        }else if (id == R.id.wine) {
                            Intent camshot = new Intent(getApplicationContext(), Wine.class);
                            startActivity(camshot);
                        } else if (id == R.id.brandy) {
                            Intent camshot = new Intent(getApplicationContext(), Brandy.class);
                            startActivity(camshot);
                        } else if (id == R.id.cart) {
                            Intent camshot = new Intent(getApplicationContext(), Cart.class);
                            startActivity(camshot);
                        } else if (id == R.id.rum) {
                            Intent camshot = new Intent(getApplicationContext(), Rum.class);
                            startActivity(camshot);
                        } else if (id == R.id.gin) {
                            Intent camshot = new Intent(getApplicationContext(), Gin.class);
                            startActivity(camshot);
                        }

                        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                        drawer.closeDrawer(GravityCompat.START);
                        return true;
                    }





    }





