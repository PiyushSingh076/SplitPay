package com.devdroid.splitpay;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class bank extends AppCompatActivity implements View.OnClickListener {
    private EditText bankname, accountno, balance;
    private ProgressDialog progressDialog;
    private Button btnSignUp2;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank);

        bankname = findViewById(R.id.Bankname);
        btnSignUp2 = findViewById(R.id.btn_signup2);
        accountno = findViewById(R.id.accountno);
        balance = findViewById(R.id.balance);
        progressDialog = new ProgressDialog(this);

        btnSignUp2.setOnClickListener(this);
    }

        private void registerAcc(){

            final String bankName=bankname.getText().toString().trim();
            final String AccountNo=accountno.getText().toString().trim();
            final String Balance= balance.getText().toString().trim();

            progressDialog.setMessage("Registering User....");
            progressDialog.show();

            StringRequest stringRequest=new StringRequest(Request.Method.POST, Constants.URL_REGISTER, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();

                    try{
                        JSONObject obj = new JSONObject(response);
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                        // Print the response string for debugging purposes
                        Log.d("Response", response);

                        Toast.makeText(bank.this, "User registered successfully...", Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Intent intent=new Intent(bank.this,Login.class);
                    startActivity(intent);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.hide();
                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }){
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params= new HashMap<>();
                    params.put("bankname",bankName);
                    params.put("accountno", AccountNo);
                    params.put("balance", Balance);
                    return params;
                }
            };

            RequestHandler.getInstance(this).addToRequestQueue(stringRequest);


        }



    @Override
    public void onClick(View view) {
        if(view==btnSignUp2) {
            registerAcc();
            String accountNo = accountno.getText().toString().trim();
            fetchBankData(accountNo);
        }
    }

    private void fetchBankData(String accountNo) {
        progressDialog.setMessage("Fetching Bank Data...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_ACC, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

                try {
                    JSONObject obj = new JSONObject(response);
                    if (!obj.getBoolean("error")) {
                        JSONObject user = obj.getJSONObject("user");
                        String bankName = user.getString("bankname");
                        String accountNo = user.getString("accountno");
                        String Balance = user.getString("balance");

                        // Display the fetched data in your UI or do whatever you want with it
                        // For example, you can set the data in EditText fields like this:
                        bankname.setText(bankName);
                        accountno.setText(accountNo);
                        balance.setText(Balance);

                        Toast.makeText(bank.this, "Bank data fetched successfully...", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(bank.this, obj.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(bank.this, "Error parsing JSON response", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.hide();
                Toast.makeText(bank.this, "Error fetching bank data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("accountno", accountNo);
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }


}