package com.example.home.lavia;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;

public class add_Product extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    ImageView imageView;
    EditText imageName, imagePrice, imageGroup;
    ProgressDialog progress;
    ImageButton imageButton;
    Button Upload;
    Uri fileUri;
    String selectedType;
    private static final int PICK_IMAGE_REQUEST = 234;
    private StorageTask mUploadTask;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        selectedType = getIntent().getStringExtra("store");
        imageGroup = (EditText) findViewById(R.id.liq_group);
        imageName = (EditText)findViewById(R.id.liq_name);
        imagePrice = (EditText) findViewById(R.id.liq_price);
        imageButton = (ImageButton) findViewById(R.id.button_image);
        Upload = (Button)findViewById(R.id.upload);

        DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Nairobi/" +selectedType +"/Liquor/");

        Upload.setOnClickListener(add_Product.this);
        imageButton.setOnClickListener(add_Product.this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener( add_Product.this);
    }
    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.INTERNAL_CONTENT_URI);
//        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select an Image"), PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data !=null && data.getData() != null){
            fileUri =data.getData();
            Picasso.get().load(fileUri).into(imageView);
            //StorageReference filePath = storageReference.child("liquor").child(uri.getLastPathSegment());
        }

    }
    private String getFileExtension(Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime =  MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
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
                Toast.makeText(add_Product.this,"Please Wait...",Toast.LENGTH_LONG). show();
            }else {
                save();
            }
        }
    }



    public void save() {
        String Group = imageGroup.getText().toString().trim();
        String Name = imageName.getText().toString().trim();
        String Price = imagePrice.getText().toString().trim();
        progress = new ProgressDialog(this);
        progress.setMessage("Posting your notice...");

        if (Name.isEmpty())
        {
            imageName.setError("Kindly Fill This Input");
            return;
        }else if (Group.isEmpty())
        {
            imageGroup.setError("Kindly Fill This Input");
            Toast.makeText(this, "Fill All Inputs", Toast.LENGTH_LONG).show();
            return;
        }else if (Price.isEmpty())
        {
            imagePrice.setError("Kindly Fill This Input");
            Toast.makeText(this, "Fill All Inputs", Toast.LENGTH_LONG).show();
            return;
        }
        long time = System.currentTimeMillis();
        long unique = System.currentTimeMillis();
        String uniq = String.valueOf(unique);


        DatabaseReference ref = databaseReference;
        ref.child(Group);
        ref.child(time + "/" + Name);
        ref.child("Price");
        ref.setValue(Price);
        SharedPreferences prefs =getSharedPreferences("db",MODE_PRIVATE);
        String imei=prefs.getString("phone","");
        ImageUploadInfo x = new ImageUploadInfo(Name,Group,Price,imei,uniq);
        progress.show();




            ref.setValue(x).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    progress.dismiss();
                    if (task.isSuccessful()) {
                        Toast.makeText(add_Product.this, "Product Added Successfully", Toast.LENGTH_LONG).show();
                        Cleartxt();
                    } else {
                        Toast.makeText(add_Product.this, "Failed. Try Again", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        if (fileUri != null){
            if (fileUri != null){
                final StorageReference filePath = storageReference.child(imageName
                        + "." + getFileExtension(fileUri));
                filePath.putFile(fileUri);
                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Toast.makeText(add_Product.this, "Upload Successful", Toast.LENGTH_LONG).show();
                        //ImageUploadInfo imageUploadInfo = new ImageUploadInfo();
                        String uploadId = databaseReference.push().getKey();
                        databaseReference.child(uploadId).setValue(uri);
                    }


                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(add_Product.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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
//    @Override
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

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    //Call Leader
//    @SuppressLint("MissingPermission")
//    public void call(View view) {
//        String number = "0714359957";
//        Intent callIntent = new Intent(Intent.ACTION_CALL);
//        callIntent.setData(Uri.parse("tel:" + number));
//        startActivity(callIntent);
//    }
}
