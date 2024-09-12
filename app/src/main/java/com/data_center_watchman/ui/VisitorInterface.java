package com.data_center_watchman.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.data_center_watchman.R;
import com.data_center_watchman.model.Visitor;

public class VisitorInterface extends AppCompatActivity {
    Button sendRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitor_interface);
        sendRequest = findViewById(R.id.sendRequest);
        sendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VisitorInterface.this, ValidateCrq.class);
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
