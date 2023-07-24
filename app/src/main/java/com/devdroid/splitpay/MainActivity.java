package com.devdroid.splitpay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.devdroid.splitpay.Login;
import com.devdroid.splitpay.R;
import com.devdroid.splitpay.SignUp;

public class MainActivity extends AppCompatActivity {
    Button btnlogin,btnsignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this,profile.class));
            return;
        }

        btnlogin=(Button) findViewById(R.id.login_btn);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, Login.class);
                startActivity(intent);

            }

        });

        btnsignup=(Button) findViewById(R.id.signup_btn);
        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, SignUp.class);
                startActivity(intent);

            }

        });
    }
}