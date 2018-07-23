package com.example.home.lavia;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
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
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class Add_1 extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener {
    ImageView imageView;

    // Root Database Name for Firebase Database.
    EditText imageGroup;
    EditText imageName;
    EditText imagePrice;

    // Creating StorageReference and DatabaseReference object.
    StorageReference storageReference;
    DatabaseReference databaseReference;

    // Image request code for onActivityResult() .
    ProgressBar progressBar;
    ImageButton imageButton;
    Button Upload;

    Uri fileUri;
    private static final int PICK_IMAGE_REQUEST = 234;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        storageReference = FirebaseStorage.getInstance().getReference("Images");
        databaseReference = FirebaseDatabase.getInstance().getReference("Liquor");

        imageView = (ImageView) findViewById(R.id.imageView);
        imageGroup = (EditText) findViewById(R.id.liq_group);
        imageName = (EditText)findViewById(R.id.liq_name);
        imagePrice = (EditText) findViewById(R.id.liq_price);
        imageButton = (ImageButton) findViewById(R.id.button_image);
        Upload = (Button)findViewById(R.id.upload);

        Upload.setOnClickListener((View.OnClickListener) Add_1.this);
        imageButton.setOnClickListener((View.OnClickListener) Add_1.this);
// Assigning Id to ProgressBar.
        progressBar = new ProgressBar(Add_1.this);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }
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
            //StorageReference filePath = storageReference.child("liquor").child(uri.getLastPathSegment());
        }

    }
    private String getFileExtension(Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime =  MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
    private void uploadFile(){
        String Group = imageGroup.getText().toString().trim();
        String Name = imageName.getText().toString().trim();
        String Price = imagePrice.getText().toString().trim();
        if (TextUtils.isEmpty(Group)){
            Toast.makeText(Add_1.this, "Add liquor group", Toast.LENGTH_LONG).show();
        }else if (TextUtils.isEmpty(Name)){
            Toast.makeText(Add_1.this, "Add liquor name", Toast.LENGTH_LONG).show();
        }else if (TextUtils.isEmpty(Price)){
            Toast.makeText(Add_1.this, "Add liquor price", Toast.LENGTH_LONG).show();
        }else {
            String id = databaseReference.push().getKey();
            ImageUploadInfo imageUploadInfo = new ImageUploadInfo(id, Group, Name, Price);
            databaseReference.child(Group).child(Name).child("Price").setValue(Price);
            //databaseReference.child().child("Brand").setValue(Name);
            //databaseReference.child(Name).child("Price").setValue(Price);
            Toast.makeText(Add_1.this, "Product Added", Toast.LENGTH_LONG).show();
            Cleartxt();
        }
        if (fileUri != null){
            if (fileUri != null){
                final StorageReference filePath = storageReference.child(System.currentTimeMillis()
                        + "." + getFileExtension(fileUri));
                filePath.putFile(fileUri);
                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setProgress(0);
                            }
                        }, 5000);
                        Toast.makeText(Add_1.this, "Upload Successful", Toast.LENGTH_LONG).show();
                        //ImageUploadInfo imageUploadInfo = new ImageUploadInfo();
                        String uploadId = databaseReference.push().getKey();
                        databaseReference.child(uploadId).setValue(uri);
                    }


                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Add_1.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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
            uploadFile();
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
            Intent camshot = new Intent(getApplicationContext(), Add_1.class);
            startActivity(camshot);
        }else if (id == R.id.whiskey) {
            Intent camshot = new Intent(getApplicationContext(), Whiskey_1.class);
            startActivity(camshot);
        }else if (id == R.id.vodka) {
            Intent camshot = new Intent(getApplicationContext(), Vodka_1.class);
            startActivity(camshot);
        }else if (id == R.id.wine) {
            Intent camshot = new Intent(getApplicationContext(), Wine_1.class);
            startActivity(camshot);
        } else if (id == R.id.cart) {
            Intent camshot = new Intent(getApplicationContext(), Cart_1.class);
            startActivity(camshot);
        }else if (id == R.id.home) {
            Intent camshot = new Intent(getApplicationContext(), Home_1.class);
            startActivity(camshot);
        }else if (id == R.id.brandy) {
            Intent camshot = new Intent(getApplicationContext(), Brandy_1.class);
            startActivity(camshot);
        }else if (id == R.id.rum) {
            Intent camshot = new Intent(getApplicationContext(), Rum_1.class);
            startActivity(camshot);
        }else if (id == R.id.gin) {
            Intent camshot = new Intent(getApplicationContext(), Gin_1.class);
            startActivity(camshot);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }





}






