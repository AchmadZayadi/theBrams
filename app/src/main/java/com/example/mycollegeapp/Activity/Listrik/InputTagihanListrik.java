package com.example.mycollegeapp.Activity.Listrik;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mycollegeapp.Activity.helper.DbHelper;
import com.example.mycollegeapp.MainActivity;
import com.example.mycollegeapp.R;

public class InputTagihanListrik extends AppCompatActivity {

    EditText txt_id,txt_address,txt_name;
    Button btn_submit, btn_cancel;
    DbHelper SQLite = new DbHelper(this);
    String id, name, address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_tagihan_listrik);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txt_id = (EditText) findViewById(R.id.etIdPelanggan);
        txt_name = (EditText) findViewById(R.id.etnama);
        txt_address = (EditText) findViewById(R.id.etalamat);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);

        id = getIntent().getStringExtra(MainActivity.TAG_ID);

        if (id == null || id == "") {
            setTitle("Add Data");
        } else {
            setTitle("Edit Data");
            txt_id.setText(id);
        }

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (txt_id.getText().toString().equals("")) {
                        save();
                    }
                } catch (Exception e){
                    Log.e("Submit", e.toString());
                }
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                blank();
                finish();
            }
        });
    }
    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                blank();
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Make blank all Edit Text
    private void blank() {
        txt_name.requestFocus();
        txt_id.setText(null);
        txt_name.setText(null);
        txt_address.setText(null);
    }

    // Save data to SQLite database
    private void save() {
        if (String.valueOf(txt_name.getText()).equals(null) || String.valueOf(txt_name.getText()).equals("") ||
                String.valueOf(txt_address.getText()).equals(null) || String.valueOf(txt_address.getText()).equals("")) {
            Toast.makeText(getApplicationContext(),
                    "Please input name or address ...", Toast.LENGTH_SHORT).show();
        } else {
            SQLite.insert(txt_name.getText().toString().trim(), txt_address.getText().toString().trim());
            blank();
            finish();
        }
    }
}
