package com.example.mycollegeapp.Activity.Listrik;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import android.support.v7.app.AppCompatActivity;

import com.example.mycollegeapp.R;

public class MenuListrik extends AppCompatActivity {

    RelativeLayout rlToken;
    RelativeLayout rlTagihan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_listrik);
        init();
    }

    void init(){
        rlToken = findViewById(R.id.rlTokenListrik);
        rlTagihan =findViewById(R.id.rlTagihanListik);

        rlToken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MenuListrik.this,InputTokenListrik.class);
                startActivity(intent);

            }
        });

        rlTagihan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MenuListrik.this,InputTagihanListrik.class);
                startActivity(intent);

            }
        });

    }
}
