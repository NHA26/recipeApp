package com.example.lenovo.pecs;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    GridView gridView;
    ArrayList<Food> imageList;
    CustomGridAdapter adapter;
    Cursor cursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        // Define final variables since they have to be accessed from inner class
        final DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);

        // Open the database
        databaseAccess.open();

        // Close the database
        databaseAccess.close();

        //header start at the top
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Start");

        //declaration for gridview and implement adapter
        gridView = (GridView) findViewById(R.id.gridView1);
        imageList = new ArrayList<>();
        adapter = new CustomGridAdapter(this, R.layout.image_item, imageList);
        gridView.setAdapter(adapter);


        // get all data from sqlite
        Cursor cursor = databaseAccess.getData("SELECT * FROM TYPE");
        imageList.clear();
        while (cursor.moveToNext()) {
            String id = cursor.getString(0);
            String name = cursor.getString(1);
            byte[] image = cursor.getBlob(2);


            imageList.add(new Food(id, name, image));
        }
        adapter.notifyDataSetChanged();

        //button to click to next page and play sound
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
                view.setSelected(true);
                Food labels = imageList.get(position);
                String no = labels.getId();
                Intent passIntent = new Intent(MainActivity.this, ImageList.class);
                passIntent.putExtra("keyid", no);//pass id to next activity
                startActivity(passIntent);

            }
        });

    }
    //method to show top right menu
    private void SelectImage() {

        //Icon for add image
        final CharSequence[] items = {"Add Category", "Cancel"};
        AlertDialog.Builder mbuilder = new AlertDialog.Builder(MainActivity.this);
        mbuilder.setTitle("Add Category:");
        mbuilder = mbuilder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {

                if (items[i].equals("Add Category")) {
                    Intent intent = new Intent(MainActivity.this, ShowImageGRid.class);
                    startActivity(intent);

                } else if (items[i].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        mbuilder.show();
    }

        //showing toolbar menu
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu, menu);
            return true;
        }

    //item yg dipilih akan guna method ni utk act kan function dia
        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addimage:
                SelectImage();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}


