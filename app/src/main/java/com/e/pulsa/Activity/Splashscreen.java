package com.e.pulsa.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.e.pulsa.MainActivity;
import com.e.pulsa.R;

public class Splashscreen extends AppCompatActivity {

    int loading = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent home=new Intent(Splashscreen.this, MainActivity.class);
                startActivity(home);
                finish();

            }
        },loading);
    }
}
