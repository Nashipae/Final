package com.data_center_watchman.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.data_center_watchman.Constants;
import com.data_center_watchman.R;
import com.data_center_watchman.adapter.VisitorAdapter;
import com.data_center_watchman.model.Visitor;
import com.data_center_watchman.service.VisitorListAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CheckinView extends AppCompatActivity {

    Button btnCheckin;
    Visitor checkinVisitor;
    VisitorListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkinview);
        btnCheckin = findViewById(R.id.SignInVisitor);
        checkinVisitor = getIncomingIntent();
        btnCheckin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkinVisitor(checkinVisitor);


            }
        });
    }
    private Visitor getIncomingIntent(){
        if(getIntent().hasExtra("fullName") && getIntent().hasExtra("idNumber") && getIntent().hasExtra("company")
                && getIntent().hasExtra("reason")&& getIntent().hasExtra("crqNumber") && getIntent().hasExtra("location")&&getIntent().hasExtra("id")){
            String fullName = getIntent().getStringExtra("fullName");
            Integer id = getIntent().getIntExtra("id", 0);
            String idNumber = getIntent().getStringExtra("idNumber");
            String company = getIntent().getStringExtra("company");
            String phoneNumber = getIntent().getStringExtra("phoneNumber");
            String reason = getIntent().getStringExtra("reason");
            String crqNumber = getIntent().getStringExtra("crqNumber");
            String location = getIntent().getStringExtra("location");
            setVistorDetails(fullName,idNumber,company,reason,crqNumber,location);
            Visitor visitor = new Visitor(fullName, idNumber,crqNumber,company,phoneNumber,location,reason);
            visitor.setId(id);
            return visitor;
        } else {
            return null;
        }
    }
    private void setVistorDetails(String fullName, String idNumber, String company, String reason,String crqNumber, String location){

        TextView tv1 = findViewById(R.id.tv1);
        TextView tv2 = findViewById(R.id.tv2);
        TextView tv3 = findViewById(R.id.tv3);
        TextView tv4 = findViewById(R.id.tv5);
        TextView tv6 = findViewById(R.id.tv4);
        TextView tv7 = findViewById(R.id.tv6);
        tv1.setText(fullName);tv2.setText(idNumber);tv3.setText(company);tv4.setText(reason);
        tv6.setText(crqNumber);tv7.setText(location);

    }
    public void checkinVisitor(Visitor visitor){
        Log.d("VISITOR DETAIL","UPDATE: " + visitor.getId());
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        VisitorAdapter adapter = retrofit.create(VisitorAdapter.class);

        Call<Visitor> call = adapter.checkin(visitor);
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(CheckinView.this);
        progressDialog.setMessage("Updating Time in.Please Wait.......");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        call.enqueue(new Callback<Visitor>() {
            @Override
            public void onResponse(Call<Visitor> call, Response<Visitor> response) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CheckinView.this);
                builder.setMessage("Visitor Checked in, Successfully\n\nDo you want to checkin another visitor?").setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(CheckinView.this, RequestsActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(CheckinView.this, Splash.class);
                                startActivity(intent);
                            }
                        });
                AlertDialog alert = builder.create();
                alert.setTitle("CHECK IN");
                alert.show();
            }

            @Override
            public void onFailure(Call<Visitor> call, Throwable t) {
                Toast.makeText(CheckinView.this, "creation failed!", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
