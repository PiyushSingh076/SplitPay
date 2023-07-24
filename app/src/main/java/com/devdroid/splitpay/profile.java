package com.devdroid.splitpay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class profile extends AppCompatActivity {
    private TextView textUsername,textEmail;

    Button btnTransaction, btnSplit, btnlogout;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        if(!SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this,Login.class));
        }
        textUsername=(TextView) findViewById(R.id.text1);
        textEmail=(TextView)findViewById(R.id.text2);
        btnSplit=findViewById(R.id.btn_split);
        btnlogout=findViewById(R.id.btn_logout);
        btnTransaction=findViewById(R.id.btn_transaction);

        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPrefManager.getInstance(profile.this).logout();
                finish();
                startActivity(new Intent(profile.this,MainActivity.class));
            }
        });

        btnTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(profile.this,transaction.class);
                startActivity(intent);
            }
        });
        textEmail.setText(SharedPrefManager.getInstance(this).getUserEmail());
        textUsername.setText(SharedPrefManager.getInstance(this).getUsername());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menun,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuLogout:
                SharedPrefManager.getInstance(this).logout();
                finish();
                startActivity(new Intent(this,MainActivity.class));
                break;
        }
        return true;
    }
}