package com.example.mycollegeapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mycollegeapp.Activity.History.List_transaction;
import com.example.mycollegeapp.Activity.Listrik.MenuListrik;
import com.example.mycollegeapp.Activity.PDAM.InputPdam;
import com.example.mycollegeapp.Activity.Pulsa.ChooseProvider;

public class MainActivity extends AppCompatActivity {

    ImageView btn_logout;
    TextView txt_id, txt_username;
    String id, username, nama;
    String saldo;
    SharedPreferences sharedpreferences;

    public static final String TAG_ID = "id";
    public static final String TAG_NAMA = "nama";
    public static final String TAG_SALDO = "saldo";
    public static final String TAG_USERNAME = "username";

    ImageView llPulsa;
    ImageView llListrik;
    ImageView llpdam;
    ImageView llhistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt_id = (TextView) findViewById(R.id.user_name);
        txt_username = (TextView) findViewById(R.id.saldo);
        btn_logout = (ImageView) findViewById(R.id.btn_logout);
        sharedpreferences = getSharedPreferences(Login.my_shared_preferences, Context.MODE_PRIVATE);

        id = getIntent().getStringExtra(TAG_ID);
        nama = getIntent().getStringExtra(TAG_NAMA);
        saldo = getIntent().getStringExtra(TAG_SALDO);

        txt_id.setText(nama);
        txt_username.setText(" Rp : " + saldo);

        initialaze();
        onClick();
    }
    void initialaze(){

        llPulsa = findViewById(R.id.rlPulsa);
        llListrik = findViewById(R.id.rlPln);
        llpdam = findViewById(R.id.rlPDAM);
        llhistory = findViewById(R.id.rlhistory);
        ///spadaaaaaa
    }

    void onClick(){
        llPulsa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(MainActivity.this, ChooseProvider.class);
                intent.putExtra(TAG_ID, id);
                intent.putExtra(TAG_USERNAME, username);
                intent.putExtra(TAG_SALDO, saldo);
                intent.putExtra(TAG_NAMA, nama);
                startActivity(intent);

            }
        });

        llListrik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MenuListrik.class);
                startActivity(intent);
            }
        });

        llpdam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, InputPdam.class);
                startActivity(intent);

            }
        });
        llhistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, List_transaction.class);
                startActivity(intent);

            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // update login session ke FALSE dan mengosongkan nilai id dan username
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putBoolean(Login.session_status, false);
                editor.putString(TAG_ID, null);
                editor.putString(TAG_USERNAME, null);
                editor.commit();

                Intent intent = new Intent(MainActivity.this, Login.class);
                finish();
                startActivity(intent);
            }
        });

    }
}
