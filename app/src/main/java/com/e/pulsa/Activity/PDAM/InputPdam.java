package com.e.pulsa.Activity.PDAM;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.e.pulsa.R;

public class InputPdam extends AppCompatActivity {
    Spinner spinner;
    String [] NominalPulsa = {
            "JAWA BARAT",
            "JAWA TIMUR",
            "JAWA TENGAH",
            "DKI JAKARTA"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_pdam);

        init();
    }

    void init(){

        spinner = findViewById(R.id.spNominal);
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, NominalPulsa);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }
}
