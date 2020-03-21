package com.example.mycollegeapp.Activity.Listrik;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.mycollegeapp.Activity.AppController;
import com.example.mycollegeapp.Login;
import com.example.mycollegeapp.MainActivity;
import com.example.mycollegeapp.R;
import com.example.mycollegeapp.Server;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class InputTokenListrik extends AppCompatActivity {
    Spinner spinner;
    public static final String TAG_ID = "id";
    String id;
    ProgressDialog pDialog;
    EditText txt_id,id_cus,etid_wall;
    TextView txt_harga;
    Button btn_submit, btn_cancel;
    Intent intent;
    SharedPreferences sharedpreferences;
    String [] NominalPulsa = {
            "10000",
            "25000",
            "50000",
            "100000"
    };

    int success;
    ConnectivityManager conMgr;

    private String url = Server.URL + "input_listrik_token.php";

    private static final String TAG = InputTagihanListrik.class.getSimpleName();

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    String tag_json_obj = "json_obj_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_token_listrik);

        init();

        sharedpreferences = getSharedPreferences(Login.my_shared_preferences, Context.MODE_PRIVATE);

        id = getIntent().getStringExtra(TAG_ID);


       // txt_id.setText(id);


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

        btn_cancel =  findViewById(R.id.btn_cancel_tok);
        btn_submit =  findViewById(R.id.btn_bayar_tok);
        txt_id =  findViewById(R.id.etIdPelanggan);
        txt_harga =  findViewById(R.id.tvTotalharga);
        id_cus =  findViewById(R.id.etIdPel);
        etid_wall =  findViewById(R.id.etIdwall);



        btn_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                intent = new Intent(InputTokenListrik.this, MainActivity.class);
                finish();
                startActivity(intent);
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String id_pel = id_cus.getText().toString();
                String id_wall = etid_wall.getText().toString();
                String nomer_pel = txt_id.getText().toString();
                String tagihan = txt_harga.getText().toString();

                sharedpreferences = getSharedPreferences(Login.my_shared_preferences, Context.MODE_PRIVATE);
                id_cus.setText(sharedpreferences.getString("id",""));
                etid_wall.setText(sharedpreferences.getString("id_wallet",""));

              //  String tagihan = txt_harga.getText().toString();


                if (conMgr.getActiveNetworkInfo() != null
                        && conMgr.getActiveNetworkInfo().isAvailable()
                        && conMgr.getActiveNetworkInfo().isConnected()) {
                    checkinput(id_pel, tagihan, id_wall, nomer_pel);
                } else {
                    Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void init(){

        spinner = findViewById(R.id.spNominal);
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, NominalPulsa);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                txt_harga.setText(spinner.getSelectedItem().toString());
                txt_harga.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }
    private void checkinput(final String id_pel, final String tagihan, final String id_wall, final String nomer_pel) {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Membayar ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Transaksi Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Check for error node in json
                    if (success == 1) {

                        Log.e("Successfully!", jObj.toString());

                        Toast.makeText(getApplicationContext(),
                                jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(InputTokenListrik.this, MainActivity.class);
                        finish();
                        startActivity(intent);
                      //  txt_harga.setText("");

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
                Log.e(TAG, "Transaksi Gagal: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();

                hideDialog();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_pel", id_pel);
                params.put("id_cus", nomer_pel);
                params.put("id_wallet", id_wall);
                params.put("tagihan", tagihan);


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
        intent = new Intent(InputTokenListrik.this, MainActivity.class);
        finish();
        startActivity(intent);
    }
}
