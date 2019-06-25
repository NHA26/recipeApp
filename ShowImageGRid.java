package com.example.lenovo.pecs;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ShowImageGRid extends AppCompatActivity {

    EditText edtName;
    Button btnCamera, btnAdd;
    ImageView imageView;
    final int REQUEST_CODE_GALLERY = 999;
    Integer REQUEST_CAMERA = 0;
    Integer SELECT_FILE=1;
    private MediaRecorder myRecorder;
    private MediaPlayer myPlayer;
    private String outputFile = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image_grid);


        btnCamera= (Button) findViewById(R.id.button6);
        imageView = (ImageView) findViewById(R.id.imageView4);
        edtName = (EditText) findViewById(R.id.editText);
        btnAdd= (Button) findViewById(R.id.button4);
        // Define final variables since they have to be accessed from inner class
        final DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);

        // Open the database
        databaseAccess.open();

        // Close the database
        databaseAccess.close();

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Add");

        //Choose button image after click add from gallery
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ActivityCompat.requestPermissions(
//                        ShowImageGRid.this,
//                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
//                        REQUEST_CODE_GALLERY //to access gallery
//                );
                        SelectImage();
            }
        });

        //add button
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    databaseAccess.insertData(
                            edtName.getText().toString().trim(),
                            imageViewToByte(imageView)
                    );
                    Toast.makeText(getApplicationContext(), "Added successfully!", Toast.LENGTH_SHORT).show();
                    edtName.setText("");
                    imageView.setImageResource(R.mipmap.ic_launcher);
                    Intent intent = new Intent(ShowImageGRid.this, MainActivity.class);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public static byte[] imageViewToByte (ImageView image){
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    private void SelectImage() {

        //Icon for add image
        final CharSequence[] items = {"Add Image from Gallery", "Take Photo","Cancel"};
        AlertDialog.Builder mbuilder = new AlertDialog.Builder(ShowImageGRid.this);
        mbuilder.setTitle("Add Image:");
        mbuilder = mbuilder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {

                if (items[i].equals("Add Image from Gallery")) {
                    ActivityCompat.requestPermissions(
                            ShowImageGRid.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            REQUEST_CODE_GALLERY //to access gallery
                    );

//                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                    intent.setType("image/*");
//                    startActivityForResult(intent, SELECT_FILE);


            } else if (items[i].equals("Take Photo")){ //still not function
                    Intent takepicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takepicture, REQUEST_CAMERA);


                } else if (items[i].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        mbuilder.show();
    }

    //access gallery phone
    @Override
    public void onRequestPermissionsResult ( int requestCode, String[] permissions,
                                           int[] grantResults){

        if (requestCode == REQUEST_CODE_GALLERY) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            } else {
                Toast.makeText(getApplicationContext(), "You don't have permission to access file location!", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public  void onActivityResult(int requestCode, int resultCode, Intent data) {

//        ImageView imageViewFood = (ImageView)findViewById(R.id.imageViewUpdate);

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
//            return;
//        }

            if (requestCode == REQUEST_CODE_GALLERY) {
//            if (requestCode == REQUEST_CODE_GALLERY) {
                if (data != null) {
                    Uri uri = data.getData();
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(uri);
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        imageView.setImageBitmap(bitmap);
////                        Bitmap resized = Bitmap.createScaledBitmap(yourBitmap, 1200, 900, true)
//                        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, fos); //compressed bitmap to file

                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(ShowImageGRid.this, "Failed!", Toast.LENGTH_SHORT).show();
                    }
                }

            } else if (requestCode == REQUEST_CAMERA) {
//                Uri uri = data.getData();
//                InputStream inputStream;
//                try {
//                    inputStream = getContentResolver().openInputStream(uri);
                Bundle bundle = data.getExtras();
                final Bitmap bitmap = (Bitmap) bundle.get("data");
//                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap);
//                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
//                    byte[] byteArray = stream.toByteArray();
//                   data.insert(byteArray);
//            saveImage(bitmap);
//                    Toast.makeText(ShowImageGRid.this, "Image Saved!", Toast.LENGTH_SHORT).show();
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }

            }
        }

    }
}