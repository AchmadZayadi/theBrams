package com.example.mycollegeapp.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.mycollegeapp.Activity.adapter.DataModel;
import com.example.mycollegeapp.Login;
import com.example.mycollegeapp.MainActivity;
import com.example.mycollegeapp.R;
import com.example.mycollegeapp.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Profile_user extends AppCompatActivity {
    private String url = Server.URL + "data_saldo.php";
    private String url2 = Server.URL + "edit_profile.php";
    private static ProgressDialog mProgressDialog;

    TextView txt_id, txt_username, et_nama, et_alamat, et_pass, et_email, et_cfpass, et_notelp;
    String id, username, nama, alamat, email, no_telp;
    String saldo;
    Button btn_submit;
    int success;
    SharedPreferences sharedpreferences;
    public static final String TAG_ID = "id";
    public static final String TAG_NAMA = "nama";
    public static final String TAG_ALAMAT = "alamat";
    public static final String TAG_EMAIL = "email";
    public static final String TAG_SALDO = "saldo";
    public static final String TAG_USERNAME = "username";
    ArrayList<DataModel> dataModelArrayList;

    private static final String TAG = Register.class.getSimpleName();
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    String tag_json_obj = "json_obj_req";
    ProgressDialog pDialog;
    ConnectivityManager conMgr;

    ImageView llPulsa;
    ImageView llprofile;
    ImageView llListrik;
    ImageView llpdam;
    ImageView lltopup;
    ImageView llhistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        txt_id = (TextView) findViewById(R.id.name);
        txt_username = (TextView) findViewById(R.id.designation);



        btn_submit =  findViewById(R.id.btn_edit);
        et_nama = findViewById(R.id.et_nama);
        et_alamat = findViewById(R.id.et_alamat);
        et_email =  findViewById(R.id.et_email);
        et_notelp =  findViewById(R.id.et_phone);
        et_pass =  findViewById(R.id.etPassword);
        et_cfpass =  findViewById(R.id.etRePassword);

        sharedpreferences = getSharedPreferences(Login.my_shared_preferences, Context.MODE_PRIVATE);
        String nom = sharedpreferences.getString("username","");
        String id_wall = sharedpreferences.getString("id_wallet","");

        checkinput(id_wall);

        id = getIntent().getStringExtra(TAG_ID);
        nama = getusename();

        txt_id.setText(nama);
        txt_username.setText(nom);

        btn_submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String nama = et_nama.getText().toString();
                String alamat = et_alamat.getText().toString();
                String email = et_email.getText().toString();
                String no_telp = et_notelp.getText().toString();
                String pass = et_pass.getText().toString();
                String cfpass = et_pass.getText().toString();

                sharedpreferences = getSharedPreferences(Login.my_shared_preferences, Context.MODE_PRIVATE);
                String id_user = sharedpreferences.getString("id","");

                conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                if (conMgr.getActiveNetworkInfo() != null
                        && conMgr.getActiveNetworkInfo().isAvailable()
                        && conMgr.getActiveNetworkInfo().isConnected()) {
                    checkedit(id_user, nama, alamat, email, no_telp, pass, cfpass);
                } else {
                    Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public String getusename() {
        String nama = sharedpreferences.getString("nama","");

        return nama;
    }

    private void checkinput(final String id_wall) {

        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Transaction Response: " + response.toString());

                try {

                    JSONObject obj = new JSONObject(response);
                    if(obj.optString("status").equals("true")){

                        dataModelArrayList = new ArrayList<>();
                        JSONArray dataArray  = obj.getJSONArray("data");

                        for (int i = 0; i < dataArray.length(); i++) {

                            DataModel playerModel = new DataModel();
                            JSONObject dataobj = dataArray.getJSONObject(i);


                            String nama = dataobj.getString(TAG_NAMA);
                            String alamat = dataobj.getString(TAG_ALAMAT);
                            String email = dataobj.getString(TAG_EMAIL);
                            String nomertelp = dataobj.getString("username");

                            et_nama.setText(nama);
                            et_alamat.setText(alamat);
                            et_email.setText(email);
                            et_notelp.setText(nomertelp);
                            txt_username.setText(saldo);
                            dataModelArrayList.add(playerModel);

                        }


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_wallet", id_wall);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    private void checkedit(final String id_user ,final String nama, final String alamat, final String email, final String no_telp,final String password, final String confirm_password) {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Mohon Tunggu ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, url2, new Response.Listener<String>() {

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
                        Intent intent = new Intent(Profile_user.this, MainActivity.class);
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
                Log.e(TAG, "Gagal: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();

                hideDialog();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_user", id_user);
                params.put("nama", nama);
                params.put("alamat", alamat);
                params.put("email", email);
                params.put("telepon", no_telp);
                params.put("password", password);
                params.put("confirm_password", confirm_password);


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

    public static void removeSimpleProgressDialog() {
        try {
            if (mProgressDialog != null) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                    mProgressDialog = null;
                }
            }
        } catch (IllegalArgumentException ie) {
            ie.printStackTrace();

        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
