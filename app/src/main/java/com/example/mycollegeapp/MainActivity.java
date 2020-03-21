package com.example.mycollegeapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.mycollegeapp.Activity.AppController;
import com.example.mycollegeapp.Activity.History.List_transaction;
import com.example.mycollegeapp.Activity.Listrik.MenuListrik;
import com.example.mycollegeapp.Activity.PDAM.InputPdam;
import com.example.mycollegeapp.Activity.Profile_user;
import com.example.mycollegeapp.Activity.Pulsa.ChooseProvider;
import com.example.mycollegeapp.Activity.TopUp;
import com.example.mycollegeapp.Activity.adapter.DataModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private String url = Server.URL + "data_saldo.php";
    private static ProgressDialog mProgressDialog;
    ImageView btn_logout;
    TextView txt_id, txt_username;
    String id, username, nama;
    String saldo;
    int success;
    SharedPreferences sharedpreferences;
    public static final String TAG_ID = "id";
    public static final String TAG_NAMA = "nama";
    public static final String TAG_SALDO = "saldo";
    public static final String TAG_USERNAME = "username";
    ArrayList<DataModel> dataModelArrayList;

    private static final String TAG = Register.class.getSimpleName();
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    String tag_json_obj = "json_obj_req";


    ImageView llPulsa;
    ImageView llprofile;
    ImageView llListrik;
    ImageView llpdam;
    ImageView lltopup;
    ImageView llhistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt_id = (TextView) findViewById(R.id.user_name);
        txt_username = (TextView) findViewById(R.id.saldo);
        btn_logout = (ImageView) findViewById(R.id.btn_logout);

        sharedpreferences = getSharedPreferences(Login.my_shared_preferences, Context.MODE_PRIVATE);
        String id_wall = sharedpreferences.getString("id_wallet","");
        checkinput(id_wall);
        id = getIntent().getStringExtra(TAG_ID);
        nama = getusename();

        txt_id.setText(nama);



        initialaze();
        onClick();
    }
    void initialaze(){
        llprofile = findViewById(R.id.user_photo);
        llPulsa = findViewById(R.id.rlPulsa);
        llListrik = findViewById(R.id.rlPln);
        llpdam = findViewById(R.id.rlPDAM);
        llhistory = findViewById(R.id.rlhistory);
        lltopup = findViewById(R.id.send);
        ///spadaaaaaa
    }
    public String getusename() {
        String usename = sharedpreferences.getString("nama","");

        return usename;
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



                            double saldo = dataobj.getDouble(TAG_SALDO);

                            Locale localeID = new Locale("in", "ID");
                            NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
                            txt_username.setText(" " + formatRupiah.format((double)saldo));
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

    void onClick(){
        llPulsa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(MainActivity.this, ChooseProvider.class);
                intent.putExtra(TAG_ID, id);
                intent.putExtra(TAG_USERNAME, username);
                intent.putExtra(TAG_SALDO, saldo);
                intent.putExtra(TAG_NAMA, nama);
                startActivity(intent);

            }
        });

        llListrik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MenuListrik.class);
                startActivity(intent);
            }
        });

        llpdam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, InputPdam.class);
                startActivity(intent);

            }
        });
        llhistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, List_transaction.class);
                startActivity(intent);

            }
        });
        llprofile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(MainActivity.this, Profile_user.class);
                        startActivity(intent);

                    }
                });

        lltopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, TopUp.class);
                startActivity(intent);

            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // update login session ke FALSE dan mengosongkan nilai id dan username
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putBoolean(Login.session_status, false);
                editor.putString(TAG_ID, null);
                editor.putString(TAG_USERNAME, null);
                editor.commit();

                Intent intent = new Intent(MainActivity.this, Login.class);
                finish();
                startActivity(intent);
            }
        });

    }
}
