package com.example.crudsqlite;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.crudsqlite.adapters.Adapter;
import com.example.crudsqlite.helpers.DbHelper;
import com.example.crudsqlite.models.Data;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    AlertDialog.Builder dialog;
    List<Data> itemList = new ArrayList<Data>();
    Adapter adapter;
    DbHelper db = new DbHelper(this);

    public static final String TAG_ID = "id";
    public static final String TAG_NAME = "name";
    public static final String TAG_ADDRESS = "address";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = new DbHelper(getApplicationContext());
        listView = (ListView) findViewById(R.id.list_view);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EditActivity.class);
                startActivity(intent);
            }
        });

        adapter = new Adapter(MainActivity.this, itemList);
        listView.setAdapter(adapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long idx) {
                final String id = itemList.get(position).getId();
                final String name = itemList.get(position).getName();
                final String address = itemList.get(position).getAddress();

                final CharSequence[] dialogItem = {"Edit", "Delete"};
                dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setCancelable(true);
                dialog.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                Intent intent_edit = new Intent(MainActivity.this, EditActivity.class);
                                intent_edit.putExtra(TAG_ID, id);
                                intent_edit.putExtra(TAG_NAME, name);
                                intent_edit.putExtra(TAG_ADDRESS, address);
                                startActivity(intent_edit);
                                break;
                            case 1:
                                db.delete(Integer.parseInt(id));
                                itemList.clear();
                                getAllData();
                                break;
                        }
                    }
                }).show();
                return false;

            }
        });
    }

    private void getAllData(){
        ArrayList<HashMap<String, String>> row = db.getAllData();
        for (int i=0; i<row.size(); i++){
            String id = row.get(i).get(TAG_ID);
            String name = row.get(i).get(TAG_NAME);
            String address = row.get(i).get(TAG_ADDRESS);

            Data data = new Data();
            data.setId(id);
            data.setName(name);
            data.setAddress(address);
            itemList.add(data);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        itemList.clear();
        getAllData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {
            Intent intent = new Intent(MainActivity.this, EditActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
