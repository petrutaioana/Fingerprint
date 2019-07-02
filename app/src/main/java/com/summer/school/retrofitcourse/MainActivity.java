package com.summer.school.retrofitcourse;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.summer.school.retrofitcourse.model.EmployeeData;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private List<EmployeeData> employeeList;
    private EmployeeApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.my_list);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter
        employeeList = new ArrayList<>();
        mAdapter = new MyListAdapter(employeeList, new MyListAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClicked(int position) {
                // Add a delete action when long pressing an item in the list
                if (position < employeeList.size()) {
                    displayConfirmDialog(employeeList.get(position));
                }
                return false;
            }
        });
        recyclerView.setAdapter(mAdapter);


        // Build the API Service
        apiService = (new Retrofit.Builder())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://dummy.restapiexample.com/api/v1/")
                .build()
                .create(EmployeeApiService.class);

        // Load at start the current list of employees
        getAllEmployees();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void getAllEmployees() {
        Call<List<EmployeeData>> employeeCall = apiService.getUserDetails();
        employeeCall.enqueue(new Callback<List<EmployeeData>>() {
            @Override
            public void onResponse(Call<List<EmployeeData>> call, Response<List<EmployeeData>> response) {
                if (response != null && response.body() != null) {
                    employeeList.clear();
                    employeeList.addAll(response.body());
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<EmployeeData>> call, Throwable t) {
                //System.out.println("BUGSSSSSSSSSSSSSSSSS");
            }
        });
    }


    private void displayConfirmDialog(final EmployeeData employeeData) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.are_you_sure_label))
                .setPositiveButton(getString(R.string.confirm_label), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteEmployee(employeeData.getId());
                    }
                })
                .setNegativeButton(getString(R.string.cancel_label), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private void deleteEmployee(final int employeeId) {
        Call<ResponseBody> responseBodyCall = apiService.deleteEmployeeById(employeeId);
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                getAllEmployees();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
