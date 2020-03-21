package com.example.mycollegeapp.Activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.mycollegeapp.Login;
import com.example.mycollegeapp.MainActivity;
import com.example.mycollegeapp.R;
import com.example.mycollegeapp.Register;
import com.example.mycollegeapp.Server;
import com.example.mycollegeapp.adapter.CustomArrayAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TopUp extends AppCompatActivity {

    private String url = Server.URL + "topup.php";
    private Button btn_choise;
    private Button buttonUpload;

    private ImageView imageView;

    private EditText editTextName;

    private Bitmap bitmap;

    private int PICK_IMAGE_REQUEST = 1;

    static final int REQUEST_SELECT_PHONE_NUMBER = 1;
    private String KEY_IMAGE = "foto_struk";
    EditText etPulsa;
    EditText etId,etIdwall;
    Spinner spinner;
    Button btnbayar, btncancel;
    TextView tvTotalPulsa, txt_harga;
    RelativeLayout rlTotalPulsa;
    TextView rlTotalsaldo;

    ConnectivityManager conMgr;

    String id, telp, total_harga;
    String saldo;
    SharedPreferences sharedpreferences;
    Intent intent;
    int success;

    ProgressDialog pDialog;
    String [] NominalPulsa = {
            "Bank Mandiri",
            "Bank BNI",
            "Alma Midi",
            "AlmaMart",
            "IndoMart"
    };

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
        setContentView(R.layout.activity_topup);
        Button btn_choise = (Button) findViewById(R.id.buttonChoose);
        imageView  = (ImageView) findViewById(R.id.imageView);

        init();
        onClick();

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

        Button btn_submit = (Button) findViewById(R.id.buttonUpload);
        Button btn_choise = (Button) findViewById(R.id.buttonChoose);
        etId = findViewById(R.id.etIdPel);
        etIdwall = findViewById(R.id.etIdwall);

        etPulsa = findViewById(R.id.etPulsa);

        tvTotalPulsa = findViewById(R.id.tvTotalPulsa);
        rlTotalPulsa = findViewById(R.id.rlTotalPulsa);
        txt_harga =  findViewById(R.id.tvTotalharga);

        sharedpreferences = getSharedPreferences(Login.my_shared_preferences, Context.MODE_PRIVATE);
        etId.setText(sharedpreferences.getString("id",""));
        etIdwall.setText(sharedpreferences.getString("id_wallet",""));


        etPulsa.setFocusableInTouchMode(false);
        etPulsa.setFocusable(false);


        btn_submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String id_pel = etId.getText().toString();
                String id_wall = etIdwall.getText().toString();
                String struk = getStringImage(bitmap);
                String total_harga = tvTotalPulsa.getText().toString();


                if (conMgr.getActiveNetworkInfo() != null
                        && conMgr.getActiveNetworkInfo().isAvailable()
                        && conMgr.getActiveNetworkInfo().isConnected()) {
                    checkinput(total_harga, id_pel, id_wall, struk);
                } else {
                    Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_choise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });


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

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }


    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //Setting the Bitmap to ImageView
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void checkinput(final String total_harga, final String id_pel, final String id_wall, final String struk) {
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

                        Intent intent = new Intent(TopUp.this, MainActivity.class);
                        finish();
                        startActivity(intent);

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
                params.put("id_pel", id_pel);
                params.put("id_wallet", id_wall);
                params.put("total_transaksi", total_harga);
                params.put("foto_struk", struk);
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
        Intent intent = new Intent(TopUp.this, Login.class);
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


    }

    private void showPulsa() {
        final String[] saldo = {"10.000", "25.000", "50.000", "100.000"};
        CustomArrayAdapter adapter = new CustomArrayAdapter(TopUp.this, R.layout.list_item_adapter, saldo);

        AlertDialog.Builder builder = new AlertDialog.Builder(TopUp.this);
        builder.setTitle("Pulsa")
                .setAdapter(adapter, new DialogInterface.OnClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (which == 0) {
                            rlTotalPulsa.setVisibility(View.VISIBLE);
                            tvTotalPulsa.setText("10000");
                        } else if (which == 1) {
                            rlTotalPulsa.setVisibility(View.VISIBLE);
                            tvTotalPulsa.setText("20000");
                        } else if (which == 2) {
                            rlTotalPulsa.setVisibility(View.VISIBLE);
                            tvTotalPulsa.setText("50000");
                        } else if (which == 3) {
                            rlTotalPulsa.setVisibility(View.VISIBLE);
                            tvTotalPulsa.setText("100000");
                        }

                        etPulsa.setText(saldo[which]);
                    }
                });


        builder.create().show();


    }
}