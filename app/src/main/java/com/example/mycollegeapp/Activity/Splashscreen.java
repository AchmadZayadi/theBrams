package com.example.mycollegeapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.example.mycollegeapp.Login;
import com.example.mycollegeapp.R;

public class Splashscreen extends AppCompatActivity {

    int loading = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent home=new Intent(Splashscreen.this, Login.class);
                startActivity(home);
                finish();

            }
        },loading);
    }
}
