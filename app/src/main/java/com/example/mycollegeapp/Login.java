package com.example.mycollegeapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mycollegeapp.Activity.Register;


public class Login extends AppCompatActivity {

    String Phone = "087885010608";
    String password = "123456";
    EditText etPhone;
    EditText etPassword;
    Button btnLogin;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        initialize();
        onClick();
    }

    void initialize() {

        etPhone = findViewById(R.id.et_identifier);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_register);


    }

    void onClick() {

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Phone = etPhone.getText().toString().trim();
                password = etPassword.getText().toString().trim();

                if (Phone.equals("")) {
                    showDialog();
                } else if (password.equals("")) {
                    showDialog();
                } else if (!Phone.equals("087885010608")) {
                    showDialogFalseData();
                } else if (!password.equals("123456")) {
                    showDialogFalseData();
                } else {

                    Toast.makeText(getApplicationContext(),"Selamat Datang Bramantyo",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);

                }

            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });

    }


    private void showDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        alertDialogBuilder.setTitle("Peringatan");

        alertDialogBuilder
                .setMessage(getString(R.string.warning_login))
                .setCancelable(false)
                .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void showDialogFalseData() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        alertDialogBuilder.setTitle("Peringatan");

        alertDialogBuilder
                .setMessage(getString(R.string.warning_login2))
                .setCancelable(false)
                .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
