package com.e.pulsa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.e.pulsa.Activity.Listrik.MenuListrik;
import com.e.pulsa.Activity.PDAM.InputPdam;
import com.e.pulsa.Activity.Pulsa.ChooseProvider;

public class MainActivity extends AppCompatActivity {



    RelativeLayout llPulsa;
    RelativeLayout llListrik;
    RelativeLayout llpdam;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialaze();
        onClick();
    }

    void initialaze(){

        llPulsa = findViewById(R.id.rlPulsa);
        llListrik = findViewById(R.id.rlPln);
        llpdam = findViewById(R.id.rlPDAM);

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
