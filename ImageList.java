package com.example.lenovo.pecs;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class ImageList extends AppCompatActivity {

    GridView gridView;
    ArrayList<Recipe> imageList;
    CustomGridAdapter1 adapter;
    Cursor cursor;
    Integer REQUEST_CAMERA = 0;
    String rowId;
    ArrayList<byte[]> arraySound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_list);
        // Define final variables since they have to be accessed from inner class
        final DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);

        // Open the database
        databaseAccess.open();

        // Close the database
        databaseAccess.close();

        //header start at the top
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("PECS");

        //declaration for gridview and implement adapter
        gridView = (GridView) findViewById(R.id.gridView2);
        imageList = new ArrayList<>();
        adapter = new CustomGridAdapter1(this, R.layout.image_general, imageList);
        gridView.setAdapter(adapter);
        arraySound=new ArrayList<byte[]>();

        Bundle data = getIntent().getExtras();
        rowId = data.getString("keyid");
        cursor = databaseAccess.getImage(rowId);
//        imageList.clear();
        if (cursor.moveToFirst()) {
            do {
            String id = cursor.getString(0);
                String name = cursor.getString(1);
                byte[] image = cursor.getBlob(2);


                Recipe general = new Recipe(id, name, image);
                adapter.add(general);
            } while (cursor.moveToNext());
        }

//        //button to click to next page and play sound
//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
//                view.setSelected(true);
//                Recipe labels = imageList.get(position);
//                String no = labels.getId();
////                Cursor c = databaseAccess.get(no);
//                Intent passIntent = new Intent(ImageList.this, ThirdActivity.class);
//                passIntent.putExtra("keyid", no);//pass id to next activity
////                passIntent.putExtra("keyid1", (Serializable) c);
//                startActivity(passIntent);
//
//            }
//        });

        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                CharSequence[] items = {"Update Image","Delete Category", "Cancel"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(ImageList.this);

                dialog.setTitle("Choose an action");
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {

                        if (item == 0) {
                            // update image
                            Cursor c = databaseAccess.getData("SELECT id FROM RECIPE WHERE type_id=\""+ rowId+ "\" ");
                            ArrayList<String> arrID = new ArrayList<String>();
                            while (c.moveToNext()) {
                                arrID.add(c.getString(0));
                            }
                            showDialogUpdate(ImageList.this, arrID.get(position));

                        } else if (item == 1) {
                            // delete
                            Cursor c = databaseAccess.getData("SELECT id FROM RECIPE WHERE type_id=\""+ rowId+ "\" ");
                            ArrayList<String> arrID = new ArrayList<String>();
                            while (c.moveToNext()) {
                                arrID.add(c.getString(0));
                            }
                            showDialogDelete(arrID.get(position));

                        }

                        else {
                            dialog.dismiss();
                        }
                    }
                });
                dialog.show();
                return true;
            }
        });
    }

            ImageView imageViewFood;
            EditText edtText;
            private void showDialogUpdate(Activity activity, final String position){

                // Define final variables since they have to be accessed from inner class
                final DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);

                // Open the database
                databaseAccess.open();

                // Close the database
                databaseAccess.close();

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(ImageList.this);
                View mView = getLayoutInflater().inflate(R.layout.update_image, null);
                imageViewFood = (ImageView) mView.findViewById(R.id.imageView2);
                edtText=(EditText)mView.findViewById(R.id.editText2);

                mBuilder.setTitle("Update");

                mBuilder.setView(mView);
                imageViewFood.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // request photo library
                        ActivityCompat.requestPermissions(
                                ImageList.this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                888
                        );
                    }
                });
                mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            databaseAccess.updateData(
                                    edtText.getText().toString().trim(),
                                    ShowImageGRid.imageViewToByte(imageViewFood),
                                    position
                            );
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Update successfully!!!",Toast.LENGTH_SHORT).show();
                        }
                        catch (Exception error) {
                            Log.e("Update error", error.getMessage());
                        }
                        updateImageList();
                    }
                });
                mBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                mBuilder.show();
            }

            private void showDialogDelete(final String idFood){
                final AlertDialog.Builder dialogDelete = new AlertDialog.Builder(ImageList.this);

                // Define final variables since they have to be accessed from inner class
                final DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);

                // Open the database
                databaseAccess.open();

                // Close the database
                databaseAccess.close();

                dialogDelete.setTitle("Warning!!");
                dialogDelete.setMessage("Are you sure you want to this delete?");
                dialogDelete.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            databaseAccess.deleteData1(idFood);
                            Toast.makeText(getApplicationContext(), "Delete successfully!!!",Toast.LENGTH_SHORT).show();
                        } catch (Exception e){
                            Log.e("error", e.getMessage());
                        }
                        updateImageList();
                    }
                });

                dialogDelete.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialogDelete.show();
            }
    private void updateImageList(){
        // Define final variables since they have to be accessed from inner class
        final DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);

        // Open the database
        databaseAccess.open();

        // Close the database
        databaseAccess.close();

        // get all data from sqlite
        cursor = databaseAccess.getData("SELECT * FROM RECIPE WHERE type_id=\""+ rowId+ "\" ");
        imageList.clear();
        while (cursor.moveToNext()) {
            String id = cursor.getString(0);
            String name = cursor.getString(1);
            byte[] image = cursor.getBlob(2);


            imageList.add(new Recipe(id,name, image));
        }
        adapter.notifyDataSetChanged();
    }

}
