package com.data_center_watchman.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.data_center_watchman.R;
import com.data_center_watchman.adapter.VisitorAdapter;
import com.data_center_watchman.service.CheckInAdapter;
import com.data_center_watchman.service.CheckOutAdapter;
import com.data_center_watchman.service.VisitorListAdapter;
import com.data_center_watchman.model.Visitor;
import com.data_center_watchman.model.VisitorService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckedInActivity extends AppCompatActivity {
        private List<Visitor> visitors;
        Toolbar toolbar;
        VisitorAdapter adapter;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_checked_in);
            toolbar = findViewById(R.id.contentToolbar);
            toolbar.setTitle("CheckedIn List");
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            adapter = VisitorService.getRetrofitInstance().create(VisitorAdapter.class);
            Call<List<Visitor>> call = adapter.getAllCheckedIn();
            isOnline();
            final ProgressDialog progressDialog;
            progressDialog = new ProgressDialog(CheckedInActivity.this);
            progressDialog.setMessage("Loading.Please Wait.......");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();

            call.enqueue(new Callback<List<Visitor>>() {
                @Override
                public void onResponse(Call<List<Visitor>> call, Response<List<Visitor>> response) {

                    Log.d("Test", response.body().toString());

                    generateVisitorsList(response.body());
                    progressDialog.dismiss();
                }
                @Override
                public void onFailure(Call<List<Visitor>> call, Throwable t) {
                    Toast.makeText(CheckedInActivity.this, "Something went wrong......try again", Toast.LENGTH_SHORT).show();
                    Log.d("Test", t.toString());
                    progressDialog.dismiss();
                }
            });
        }
        private void generateVisitorsList(List<Visitor> visitorList){
            RecyclerView recyclerView = findViewById(R.id.resultsView);
            CheckOutAdapter adapter = new CheckOutAdapter(this,visitorList);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(CheckedInActivity.this);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
        }
        @Override
        public boolean onOptionsItemSelected(MenuItem item){
            if (item.getItemId() == android.R.id.home){
                Intent intent = new Intent(CheckedInActivity.this, Splash.class);
                startActivity(intent);
            }
            return  super.onOptionsItemSelected(item);
        }

    public boolean isOnline() {
        ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if(netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()){
            Toast.makeText(CheckedInActivity.this, "No Internet connection!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
