package com.data_center_watchman.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.data_center_watchman.R;

public class Splash extends AppCompatActivity {
    Button sendRequest;
    ImageView imgReq, imgSout, imgSin, imgLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        imgReq = findViewById(R.id.imgRequest);
        imgSin = findViewById(R.id.imgSignin);
        imgSout = findViewById(R.id.imgSignout);
        imgLog = findViewById(R.id.imgLogs);

        sendRequest = findViewById(R.id.sendRequest);

        sendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Splash.this, ValidateCrq.class);
                startActivity(intent);

            }
        });

        imgReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Splash.this, RequestsActivity.class);

                startActivity(intent);

            }
        });
        imgSin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Splash.this, CheckedInActivity.class);

                startActivity(intent);

            }
        });
        imgSout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Splash.this, CheckedOutActivity.class);

                startActivity(intent);

            }
        });
        imgLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Splash.this, AllLogsActivity.class);

                startActivity(intent);

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

    }
}
