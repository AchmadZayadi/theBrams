package com.example.mycollegeapp.Activity.Pulsa;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.mycollegeapp.R;

public class ChooseProvider extends AppCompatActivity {


    RelativeLayout rlIndosat;
    RelativeLayout rlXl;
    RelativeLayout rlTelkomsel;
    RelativeLayout rlTheree;

    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_provider);

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
