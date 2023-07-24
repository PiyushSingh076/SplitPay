package com.devdroid.splitpay;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class transaction extends AppCompatActivity {

    private static final String PHP_URL = "http://192.168.29.52/Android/v1/bank.php";
    private List<BankData> bankDataList = new ArrayList<>();
    private RecyclerView recyclerViewt;

    Button btn_transaction;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        btn_transaction = findViewById(R.id.make_transaction);

        btn_transaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(transaction.this,perform_transaction.class);
                startActivity(intent);
            }
        });

        recyclerViewt = findViewById(R.id.recyclerView);
        recyclerViewt.setLayoutManager(new LinearLayoutManager(this));

        // Make the HTTP request to fetch the data
        new GetDataAsyncTask().execute();
    }

    private class GetDataAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(PHP_URL);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuilder buffer = new StringBuilder();

                if (inputStream == null) {
                    return null;
                }

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line).append("\n");
                }

                if (buffer.length() == 0) {
                    return null;
                }

                // Parse the JSON response
                String jsonData = buffer.toString();
                parseJsonData(jsonData);

            } catch (IOException | JSONException e) {
                Log.e("MainActivity", "Error: " + e.getMessage());
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        Log.e("MainActivity", "Error closing stream: " + e.getMessage());
                    }
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            // Initialize the RecyclerView Adapter with the retrieved data
            BankDataAdapter adapter = new BankDataAdapter(bankDataList);

            // Set the adapter for the RecyclerView
            recyclerViewt.setAdapter(adapter);
        }

        private void parseJsonData(String jsonData) throws JSONException {
            JSONObject responseObj = new JSONObject(jsonData);
            JSONArray dataArray = responseObj.getJSONArray("data");

            for (int i = 0; i < dataArray.length(); i++) {
                JSONObject jsonObject = dataArray.getJSONObject(i);
                String bankName = jsonObject.getString("bankname");
                String accountNumber = jsonObject.getString("accountno");
                double balance = Double.parseDouble(jsonObject.getString("balance"));

                BankData bankData = new BankData(bankName, accountNumber, balance);
                bankDataList.add(bankData);
            }
        }
    }
}
