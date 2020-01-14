package com.example.mycollegeapp.Activity;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;


import com.example.mycollegeapp.R;



public class Register extends AppCompatActivity {


    ImageView ivBackToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }

    void init (){

        ivBackToolbar = findViewById(R.id.ivBackToolbar);


        ivBackToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    void PostRegister(String name,String password, String email){




    }
}
