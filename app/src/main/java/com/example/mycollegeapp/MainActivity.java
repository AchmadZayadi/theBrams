package com.example.mycollegeapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mycollegeapp.Activity.Listrik.MenuListrik;
import com.example.mycollegeapp.Activity.PDAM.InputPdam;
import com.example.mycollegeapp.Activity.Pulsa.ChooseProvider;

public class MainActivity extends AppCompatActivity {

    Button btn_logout;
    TextView txt_id, txt_username;
    String id, username;
    String saldo;
    SharedPreferences sharedpreferences;

    public static final String TAG_ID = "id";
    public static final String TAG_USERNAME = "username";
    public static final String TAG_SALDO = "saldo";

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

        sharedpreferences = getSharedPreferences(Login.my_shared_preferences, Context.MODE_PRIVATE);

        id = getIntent().getStringExtra(TAG_ID);
        username = getIntent().getStringExtra(TAG_USERNAME);
        saldo = getIntent().getStringExtra(TAG_SALDO);

        txt_id.setText(username);
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

    }
}
