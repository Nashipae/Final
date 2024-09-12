package com.data_center_watchman.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.data_center_watchman.Constants;
import com.data_center_watchman.R;
import com.data_center_watchman.adapter.VisitorAdapter;
import com.data_center_watchman.model.OauthToken;
import com.data_center_watchman.model.Remedy;
import com.data_center_watchman.model.RemedyService;
import com.data_center_watchman.test.ApiUtils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ValidateCrq extends AppCompatActivity {
    TextInputLayout crqInput;
    VisitorAdapter adapter;
    TextView rFname, rLname, summary, approval, status, reason;
    TextInputEditText crqCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validate_crq);
        Button btn = findViewById(R.id.btnValidate);
        crqInput = findViewById(R.id.crqInput);
        crqCheck = findViewById(R.id.crqCheck);
        rFname = findViewById(R.id.rFname);
        rLname = findViewById(R.id.rLname);
        summary = findViewById(R.id.summary);
        approval = findViewById(R.id.approval);
        status = findViewById(R.id.status);
        reason = findViewById(R.id.crqReason);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if(!validateCrq()){
//
//                    AlertDialog.Builder builder = new AlertDialog.Builder(ValidateCrq.this);
//                    builder.setMessage("please enter your crq number as 1234...").setCancelable(false)
//                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int i) {
//                                    finish();
//                                }
//                            });
//                    AlertDialog alert = builder.create();
//                    alert.setTitle("ERROR INFO");
//                    alert.show();
//                }
                authenticate();

            }
        });

    }
    public static String token;
    public void authenticate(){
        ApiUtils.getAuthenticationAPI(Constants.RMEDEDY_API_KEY, Constants.REMEDY_SECRET, Constants.REMEDY_URL).getToken().enqueue(new Callback<OauthToken>() {
            @Override
            public void onResponse(@NonNull Call<OauthToken> call, @NonNull Response<OauthToken> response) {
                if (response.isSuccessful()) {
                    OauthToken accessToken = response.body();
                    if (accessToken != null) {

                        Log.i(ValidateCrq.class.getSimpleName(), response.body().getToken());
                        token=response.body().getToken();

//                        Crq crq = new Crq("CRQ000000369301");

                        getCrq();

                        return;
                    }
                }
                Log.e(ValidateCrq.class.getSimpleName(), "Authentication Failed: " + "Authentication Failed: "+response.raw());
            }
            @Override
            public void onFailure(@NonNull Call<OauthToken> call, @NonNull Throwable t) {
                Log.e(ValidateCrq.class.getSimpleName(), "Authentication Failed: " + t.getLocalizedMessage());
            }
        });
    }
    public void getCrq() {
       final String crq = crqInput.getEditText().getText().toString();
        adapter = RemedyService.getRemedyService().create(VisitorAdapter.class);
        Call<Remedy> call = adapter.getStatus("CRQ000000"+crq);
        call.enqueue(new Callback<Remedy>() {
            @Override
            public void onResponse(Call<Remedy> call, Response<Remedy> response) {
                if(response.isSuccessful()){
                Log.d("Test", response.body().getRequestStatus());
                rFname.setText(response.body().getFirstName());
                rLname.setText(response.body().getLastName());
                summary.setText(response.body().getSummary());
                status.setText(response.body().getRequestStatus());
                approval.setText(response.body().getApprovalPahse());
                reason.setText(response.body().getReasonForChange());
                Intent intent = new Intent(ValidateCrq.this, MainActivity.class);
                intent.putExtra("crqNumber",crq);
                startActivity(intent);
            }else{
                    final AlertDialog.Builder builder = new AlertDialog.Builder(ValidateCrq.this);
                    builder.setMessage("Kindly Enter a Correct CRQ Number").setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    builder.getContext();
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent = new Intent(ValidateCrq.this, Splash.class);
                                    startActivity(intent);
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.setTitle("ERR");
                    alert.show();
                }
            }
            @Override
            public void onFailure(Call<Remedy> call, Throwable t) {
                Toast.makeText(ValidateCrq.this, "Something went wrong......try again", Toast.LENGTH_SHORT).show();
                Log.d("Test", t.toString());
                Intent intent = new Intent(ValidateCrq.this, Splash.class);
                startActivity(intent);
            }
        });
    }
    private  boolean validateCrq(){
        String crqNumber = crqInput.getEditText().getText().toString().trim();

        if(crqNumber .isEmpty()){
            crqInput.setError("Field can't be empty");
            return false;
        }else if(crqNumber .length() > 8){
            crqInput.setError("The work order number is too long");
            return false;
        }else{
            crqInput.setError(null);
            crqInput.setErrorEnabled(false);
            return true;
        }
    }


}
