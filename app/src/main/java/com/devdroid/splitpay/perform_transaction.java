package com.devdroid.splitpay;


import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class perform_transaction extends AppCompatActivity {

    private EditText senderAccountEditText, receiverAccountEditText, amountEditText;
    private Button btnTransaction;
    private ProgressDialog progressDialog;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perform_transaction);

        senderAccountEditText = findViewById(R.id.sender_account_edittext);
        receiverAccountEditText = findViewById(R.id.receiver_account_edittext);
        amountEditText = findViewById(R.id.amount_edittext);
        btnTransaction = findViewById(R.id.btn_perform_transaction);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait....");

        btnTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performTransaction();
            }
        });
    }

    private void performTransaction() {
        final String senderAccount = senderAccountEditText.getText().toString().trim();
        final String receiverAccount = receiverAccountEditText.getText().toString().trim();
        final String amount = amountEditText.getText().toString().trim();

        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_TRANSACTION, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

                try {
                    JSONObject obj = new JSONObject(response);
                    if (!obj.getBoolean("error")) {
                        // Transaction successful
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(perform_transaction.this,profile.class);
                        startActivity(intent);
                    } else {
                        // Transaction failed
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("sender_account", senderAccount);
                params.put("receiver_account", receiverAccount);
                params.put("amount", amount);
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }
}
