//package com.example.lenovo.pecs;
//
//import android.content.Intent;
//import android.database.Cursor;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//public class ThirdActivity extends AppCompatActivity {
//
//    ImageView imageView;
//    Button btnUpdate, btnDelete;
//    TextView textName;
//    String rowId,rowId2;
//    DatabaseAccess databaseAccess;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_third);
//
//        imageView = (ImageView) findViewById(R.id.imageView2);
//        textName =(TextView)findViewById(R.id.textView3);
//        btnUpdate = (Button) findViewById(R.id.button7);
//        btnDelete = (Button) findViewById(R.id.button6);
//
//        // Define final variables since they have to be accessed from inner class
//        final DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
//
//        // Open the database
//        databaseAccess.open();
//
//        // Close the database
//        databaseAccess.close();
//
//        //header start at the top
//        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
//        actionBar.setTitle("PECS");
//
//        Bundle data = getIntent().getExtras();
//        rowId = data.getString("keyid");
//        rowId2 = data.getString("keyid1");
//
//        Cursor cursor = databaseAccess.getInfo(rowId, rowId2);
//
//        if (cursor.moveToFirst()) {
//            do {
//                textName.setText(cursor.getString(0).toString());
//
//
//            } while (cursor.moveToNext());
//        }
//
//        btnUpdate.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//                // TODO Auto-generated method stub
//
//                Bundle data = getIntent().getExtras();
//                rowId = data.getString("keyid1");
//
//                Intent intent = getIntent();
//                databaseAccess.updateData(rowId2, textName.getText().toString());
//                Toast.makeText(getApplicationContext(),
//                        "Account Successfully Updated ", Toast.LENGTH_LONG)
//                        .show();
//                finish();
//            }
//        });
//
//    }
//
////        public void viewImage(View view)
////        {
////            Cursor c = databaseAccess.getInfo(rowId,rowId2);
////            if(c.moveToNext())
////            {
////                byte[] image = c.getBlob(0);
////                Bitmap bmp= BitmapFactory.decodeByteArray(image, 0 , image.length);
////                imageView.setImageBitmap(bmp);
////                Toast.makeText(this,"Done", Toast.LENGTH_SHORT).show();
////            }
////        }
//
//
//    }
//
