package com.e.pulsa.Activity.Pulsa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.e.pulsa.R;

public class ChooseProvider extends AppCompatActivity {


    RelativeLayout rlIndosat;
    RelativeLayout rlXl;
    RelativeLayout rlTelkomsel;
    RelativeLayout rlTheree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_provider);

        init();
    }

    void init() {

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
