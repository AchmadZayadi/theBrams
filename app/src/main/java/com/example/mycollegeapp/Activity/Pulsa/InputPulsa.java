package com.example.mycollegeapp.Activity.Pulsa;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.mycollegeapp.Activity.AppController;
import com.example.mycollegeapp.Login;
import com.example.mycollegeapp.R;
import com.example.mycollegeapp.Register;
import com.example.mycollegeapp.Server;
import com.example.mycollegeapp.adapter.CustomArrayAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class InputPulsa extends AppCompatActivity {

    private String url = Server.URL + "input_pulsa.php";

    static final int REQUEST_SELECT_PHONE_NUMBER = 1;

    EditText etPulsa;
    EditText etPhoneNumber;
    ImageView ivClearPhone;
    Button btnbayar, btncancel;
    TextView tvTotalPulsa;
    RelativeLayout rlTotalPulsa;
    TextView rlTotalsaldo;

    ConnectivityManager conMgr;

    String id, telp, total_harga;
    String saldo;
    SharedPreferences sharedpreferences;
    Intent intent;
    int success;
    ProgressDialog pDialog;

    public static final String TAG_SALDO = "saldo";
    public static final String TAG_USERNAME = "username";

    private static final String TAG = Register.class.getSimpleName();
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    String tag_json_obj = "json_obj_req";

    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_pulsa);
        ImageView btPick=(ImageView) findViewById(R.id.btpick_contact);
        btPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, REQUEST_SELECT_PHONE_NUMBER);
                }

            }
        });


        conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        {
            if (conMgr.getActiveNetworkInfo() != null
                    && conMgr.getActiveNetworkInfo().isAvailable()
                    && conMgr.getActiveNetworkInfo().isConnected()) {
            } else {
                Toast.makeText(getApplicationContext(), "No Internet Connection",
                        Toast.LENGTH_LONG).show();
            }
        }



        init();
        onClick();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SELECT_PHONE_NUMBER && resultCode == RESULT_OK) {
            // Get the URI and query the content provider for the phone number
            Uri contactUri = data.getData();
            String[] projection = new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER};
            Cursor cursor = getContentResolver().query(contactUri, projection,
                    null, null, null);
            // If the cursor returned is valid, get the phone number
            if (cursor != null && cursor.moveToFirst()) {
                int numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String number = cursor.getString(numberIndex);
                etPhoneNumber.setText(number);
            }
        }
    }


    void init() {

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Pulsa");
        toolbar.setTitleTextColor(Color.BLACK);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        {
            if (conMgr.getActiveNetworkInfo() != null
                    && conMgr.getActiveNetworkInfo().isAvailable()
                    && conMgr.getActiveNetworkInfo().isConnected()) {
            } else {
                Toast.makeText(getApplicationContext(), "No Internet Connection",
                        Toast.LENGTH_LONG).show();
            }
        }

        Button btn_submit = (Button) findViewById(R.id.btn_submitpls);

        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        etPulsa = findViewById(R.id.etPulsa);
        ivClearPhone = findViewById(R.id.ivClearPhone);
        tvTotalPulsa = findViewById(R.id.tvTotalPulsa);
        rlTotalPulsa = findViewById(R.id.rlTotalPulsa);
        rlTotalsaldo = findViewById(R.id.tsaldo);

        sharedpreferences = getSharedPreferences(Login.my_shared_preferences, Context.MODE_PRIVATE);


        saldo = getIntent().getStringExtra(TAG_SALDO);


        rlTotalsaldo.setText(" Rp : " + saldo);


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
        btn_submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String id_pel = etPhoneNumber.getText().toString();
                String total_harga = tvTotalPulsa.getText().toString();


                if (conMgr.getActiveNetworkInfo() != null
                        && conMgr.getActiveNetworkInfo().isAvailable()
                        && conMgr.getActiveNetworkInfo().isConnected()) {
                    checkinput(id_pel, total_harga);
                } else {
                    Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void checkinput(final String telepon,final String total_harga) {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Membayar ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Transaction Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Check for error node in json
                    if (success == 1) {

                        Log.e("Successfully!", jObj.toString());

                        Toast.makeText(getApplicationContext(),
                                jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                        etPhoneNumber.setText("");

                    } else {
                        Toast.makeText(getApplicationContext(),
                                jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();

                hideDialog();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("telepon", telepon);
                params.put("total_tagihan", total_harga);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(InputPulsa.this, Login.class);
        finish();
        startActivity(intent);
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
        final String[] saldo = {"10.000", "25.000", "50.000", "100.000"};
        CustomArrayAdapter adapter = new CustomArrayAdapter(InputPulsa.this, R.layout.list_item_adapter, saldo);

        AlertDialog.Builder builder = new AlertDialog.Builder(InputPulsa.this);
        builder.setTitle("Pulsa")
                .setAdapter(adapter, new DialogInterface.OnClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (which == 0) {
                            rlTotalPulsa.setVisibility(View.VISIBLE);
                            tvTotalPulsa.setText("11500");
                        } else if (which == 1) {
                            rlTotalPulsa.setVisibility(View.VISIBLE);
                            tvTotalPulsa.setText("26500");
                        } else if (which == 2) {
                            rlTotalPulsa.setVisibility(View.VISIBLE);
                            tvTotalPulsa.setText("52000");
                        } else if (which == 3) {
                            rlTotalPulsa.setVisibility(View.VISIBLE);
                            tvTotalPulsa.setText("99000");
                        }

                        etPulsa.setText(saldo[which]);
                    }
                });


        builder.create().show();


    }
}