package com.example.crudsqlite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.crudsqlite.helpers.DbHelper;

public class EditActivity extends AppCompatActivity implements View.OnClickListener {
    EditText eId, eName, eAddress;
    Button btnSubmit, btnCancel;
    DbHelper db = new DbHelper(this);
    String id, name, address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        eId = (EditText) findViewById(R.id.eId);
        eName = (EditText) findViewById(R.id.eName);
        eAddress = (EditText) findViewById(R.id.eAddress);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnCancel = (Button) findViewById(R.id.btnCancel);

        id = getIntent().getStringExtra(MainActivity.TAG_ID);
        name = getIntent().getStringExtra(MainActivity.TAG_NAME);
        address = getIntent().getStringExtra(MainActivity.TAG_ADDRESS);

        if (id == null || id == ""){
            setTitle("Add Data");
        }else{
            setTitle("Edit Data");
            eId.setText(id);
            eName.setText(name);
            eAddress.setText(address);
        }

        btnSubmit.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSubmit:
                try {
                    if (eId.getText().toString().equals("")){
                        save();
                    }else{
                        edit();
                    }
                }catch (Exception e){
                    Log.e("Submit", e.toString());
                }
                break;
            case R.id.btnCancel:
                blank();
                finish();
                break;
        }
    }

    private void blank(){
        eName.requestFocus();
        eId.setText(null);
        eName.setText(null);
        eAddress.setText(null);
    }

    private void save(){
        if (String.valueOf(eName.getText().toString()).equals("") || String.valueOf(eAddress.getText().toString()).equals("")){
            Toast.makeText(this, "Please input name or address...", Toast.LENGTH_SHORT).show();
        }else{
            db.insert(eName.getText().toString().trim(), eAddress.getText().toString().trim());
            blank();
            finish();
        }
    }

    private void edit(){
        if (String.valueOf(eName.getText().toString()).equals("") || String.valueOf(eAddress.getText().toString()).equals("")){
            Toast.makeText(this, "Please input name or address...", Toast.LENGTH_SHORT).show();
        }else{
            db.update(Integer.parseInt(eId.getText().toString().trim()),eName.getText().toString().trim(), eAddress.getText().toString().trim());
            blank();
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                blank();
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
