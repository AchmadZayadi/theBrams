package com.example.mycollegeapp.Activity.Pulsa;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mycollegeapp.Activity.adapter.CustomArrayAdapter;
import com.example.mycollegeapp.R;

public class InputPulsa extends AppCompatActivity {

    EditText etPulsa;
    EditText etPhoneNumber;
    ImageView ivClearPhone;

    TextView tvTotalPulsa;
    RelativeLayout rlTotalPulsa;

    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_pulsa);

        init();
        onClick();

    }


    void init() {

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Pulsa");
        toolbar.setTitleTextColor(Color.BLACK);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        etPulsa = findViewById(R.id.etPulsa);
        ivClearPhone = findViewById(R.id.ivClearPhone);
        tvTotalPulsa = findViewById(R.id.tvTotalPulsa);
        rlTotalPulsa = findViewById(R.id.rlTotalPulsa);


        etPulsa.setFocusableInTouchMode(false);
        etPulsa.setFocusable(false);

        etPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 3){
                    ivClearPhone.setVisibility(View.VISIBLE);
                }
            }
        });

    }



    void onClick() {
        etPulsa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPulsa();
            }
        });

        ivClearPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etPhoneNumber.getText().clear();
                ivClearPhone.setVisibility(View.GONE);
            }
        });

    }

    private void showPulsa() {
        final String[] saldo = {"Rp 10.000", "Rp.25.000", "Rp.50.000", "Rp.100.000"};
        CustomArrayAdapter adapter = new CustomArrayAdapter(InputPulsa.this, R.layout.list_item_adapter, saldo);

        AlertDialog.Builder builder = new AlertDialog.Builder(InputPulsa.this);
        builder.setTitle("Pulsa")
                .setAdapter(adapter, new DialogInterface.OnClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (which == 0) {
                            rlTotalPulsa.setVisibility(View.VISIBLE);
                            tvTotalPulsa.setText("Rp.11.500");
                        } else if (which == 1) {
                            rlTotalPulsa.setVisibility(View.VISIBLE);
                            tvTotalPulsa.setText("Rp.26.500");
                        } else if (which == 2) {
                            rlTotalPulsa.setVisibility(View.VISIBLE);
                            tvTotalPulsa.setText("Rp.52.000");
                        } else if (which == 3) {
                            rlTotalPulsa.setVisibility(View.VISIBLE);
                            tvTotalPulsa.setText("Rp.99.000");
                        }

                        etPulsa.setText(saldo[which]);
                    }
                });


        builder.create().show();


    }
}