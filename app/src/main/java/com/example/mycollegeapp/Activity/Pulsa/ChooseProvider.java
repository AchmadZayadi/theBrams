package com.example.mycollegeapp.Activity.Pulsa;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.mycollegeapp.Login;
import com.example.mycollegeapp.R;

public class ChooseProvider extends AppCompatActivity {


    String id, username, nama;
    String saldo;
    SharedPreferences sharedpreferences;

    public static final String TAG_ID = "id";
    public static final String TAG_NAMA = "nama";
    public static final String TAG_SALDO = "saldo";
    public static final String TAG_USERNAME = "username";

    RelativeLayout rlIndosat;
    RelativeLayout rlXl;
    RelativeLayout rlTelkomsel;
    RelativeLayout rlTheree;

    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_provider);

        sharedpreferences = getSharedPreferences(Login.my_shared_preferences, Context.MODE_PRIVATE);

        id = getIntent().getStringExtra(TAG_ID);
        nama = getIntent().getStringExtra(TAG_NAMA);
        saldo = getIntent().getStringExtra(TAG_SALDO);

        init();
    }

    void init() {

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rlIndosat = findViewById(R.id.rlIndosat);
        rlTelkomsel = findViewById(R.id.rlTelkomsel);
        rlTheree = findViewById(R.id.rlTheree);
        rlXl = findViewById(R.id.rlXl);

        rlXl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseProvider.this, InputPulsa.class);
                intent.putExtra(TAG_ID, id);
                intent.putExtra(TAG_USERNAME, username);
                intent.putExtra(TAG_SALDO, saldo);
                intent.putExtra(TAG_NAMA, nama);
                startActivity(intent);
            }
        });
        rlTheree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseProvider.this, InputPulsa.class);
                startActivity(intent);
            }
        });
        rlTelkomsel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseProvider.this, InputPulsa.class);
                startActivity(intent);
            }
        });
        rlIndosat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseProvider.this, InputPulsa.class);
                startActivity(intent);
            }
        });

    }
}