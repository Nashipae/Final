package com.data_center_watchman.ui;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.data_center_watchman.R;
import com.data_center_watchman.adapter.VisitorAdapter;
import com.data_center_watchman.service.VisitorAdapterFilterable;
import com.data_center_watchman.service.VisitorListAdapter;
import com.data_center_watchman.model.Visitor;
import com.data_center_watchman.model.VisitorService;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllLogsActivity extends AppCompatActivity {
    private List<Visitor> visitors;
    Toolbar toolbar;
    VisitorAdapter adapter;
    private SearchView searchView;
    VisitorAdapterFilterable mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visitorlist);
        toolbar = findViewById(R.id.contentToolbar);
        toolbar.setTitle("Visitors List");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        adapter = VisitorService.getRetrofitInstance().create(VisitorAdapter.class);
        Call<List<Visitor>> call = adapter.getAll();
        isOnline();
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(AllLogsActivity.this);
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
                Toast.makeText(AllLogsActivity.this, "Something went wrong......try again", Toast.LENGTH_SHORT).show();
                Log.d("Test", t.toString());
                progressDialog.dismiss();
            }
        });
    }
    private void generateVisitorsList(List<Visitor> visitorList){
        RecyclerView recyclerView = findViewById(R.id.resultsView);
        VisitorListAdapter adapter = new VisitorListAdapter(this,visitorList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(AllLogsActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == android.R.id.home){
            Intent intent = new Intent(AllLogsActivity.this, Splash.class);
            startActivity(intent);
        }
        return  super.onOptionsItemSelected(item);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.visitor_menu, menu);
//
//        // Associate searchable configuration with the SearchView
//        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        searchView = (SearchView) menu.findItem(R.id.action_search)
//                .getActionView();
//        searchView.setSearchableInfo(searchManager
//                .getSearchableInfo(getComponentName()));
//        searchView.setMaxWidth(Integer.MAX_VALUE);
//
//        // listening to search query text change
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                // filter recycler view when query submitted
//                mAdapter.getFilter().filter(query);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String query) {
//                // filter recycler view when text is changed
//                mAdapter.getFilter().filter(query);
//                return false;
//            }
//        });
//        return true;
//    }

    public boolean isOnline() {
        ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if(netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()){
            Toast.makeText(AllLogsActivity.this, "No Internet connection!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

}
