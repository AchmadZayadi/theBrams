package com.e.pulsa.Activity.Listrik;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.e.pulsa.R;

public class InputTokenListrik extends AppCompatActivity {
    Spinner spinner;
    String [] NominalPulsa = {
            "10.000",
            "25.000",
            "50.000",
            "100.000"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_token_listrik);

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
