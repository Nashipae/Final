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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CheckoutView extends AppCompatActivity {
    Button btnCheckout;
    Visitor checkoutVisitor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkoutview);
        btnCheckout = findViewById(R.id.SignOutVisitor);
        checkoutVisitor = getIncomingIntent();
        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checkoutVisitor(checkoutVisitor);
            }
        });
    }
    private Visitor getIncomingIntent(){
        if(getIntent().hasExtra("fullName") && getIntent().hasExtra("idNumber") && getIntent().hasExtra("company")
        && getIntent().hasExtra("crqNumber")  && getIntent().hasExtra("reason") && getIntent().hasExtra("checkedIn")&& getIntent().hasExtra("crqNumber")
        && getIntent().hasExtra("location")&& getIntent().hasExtra("timeOut") && getIntent().hasExtra("id")){
           String fullName = getIntent().getStringExtra("fullName");
           Integer id = getIntent().getIntExtra("id", 0);
           String idNumber = getIntent().getStringExtra("idNumber");
           String company = getIntent().getStringExtra("company");
           String phoneNumber = getIntent().getStringExtra("phoneNumber");
           String reason = getIntent().getStringExtra("reason");
           String crqNumber = getIntent().getStringExtra("crqNumber");
           String checkedIn = getIntent().getStringExtra("checkedIn");
           String location = getIntent().getStringExtra("location");
           String timeOut = getIntent().getStringExtra("timeOut");
           setVistorDetails(fullName,idNumber,company,reason,checkedIn,crqNumber,location);
           Visitor visitor = new Visitor(fullName, idNumber,crqNumber,company,phoneNumber,location,reason);
           visitor.setId(id);
           return visitor;
        } else {
            return null;
        }
    }
    private void setVistorDetails(String fullName, String idNumber, String company, String reason, String checkedIn, String crqNumber, String location){
        TextView tv1 = findViewById(R.id.tv1);
        TextView tv2 = findViewById(R.id.tv2);
        TextView tv3 = findViewById(R.id.tv3);
        TextView tv4 = findViewById(R.id.tv5);
        TextView tv5 = findViewById(R.id.tv7);
        TextView tv6 = findViewById(R.id.tv4);
        TextView tv7 = findViewById(R.id.tv6);
        tv1.setText(fullName);tv2.setText(idNumber);tv3.setText(company);tv4.setText(reason);tv5.setText(checkedIn);
        tv6.setText(crqNumber);tv7.setText(location);
    }
    public void checkoutVisitor(Visitor visitor){
        Log.d("VISITOR DETAIL","UPDATE: " + visitor.getId());
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();

        VisitorAdapter adapter = retrofit.create(VisitorAdapter.class);
        Call<Visitor> call = adapter.checkout(visitor);
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(CheckoutView.this);
        progressDialog.setMessage("Updating Time out.Please Wait.......");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        call.enqueue(new Callback<Visitor>() {
            @Override
            public void onResponse(Call<Visitor> call, Response<Visitor> response) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CheckoutView.this);
                builder.setMessage("Visitor Checked out, Successfully\n\nDo you want to checkout another visitor?").setCancelable(false)
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                               Intent intent = new Intent(CheckoutView.this, CheckedInActivity.class);
                               startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(CheckoutView.this, Splash.class);
                                startActivity(intent);
                            }
                        });
                AlertDialog alert = builder.create();
                alert.setTitle("CHECK OUT");
                alert.show();
            }
            @Override
            public void onFailure(Call<Visitor> call, Throwable t) {
                Toast.makeText(CheckoutView.this, "creation failed!", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
