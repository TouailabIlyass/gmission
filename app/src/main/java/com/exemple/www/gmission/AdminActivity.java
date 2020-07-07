package com.exemple.www.gmission;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import models.Employe;

public class AdminActivity extends AppCompatActivity implements EmployeAdapter.OnItemClickListener{

    private ArrayList<Employe> list;
    private EmployeAdapter employeAdapter;
    private RecyclerView mRecyclerView;
    ///////////////////
    Intent mServiceIntent;
    private SensorService mSensorService;
    Context ctx;
    public Context getCtx() {
        return ctx;
    }
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i ("isMyServiceRunning?", true+"");
                return true;
            }
        }
        Log.i ("isMyServiceRunning?", false+"");
        return false;
    }
    @Override
    protected void onDestroy() {
        stopService(mServiceIntent);
        Log.i("MAINACT", "onDestroy!");
        super.onDestroy();
    }
    ////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        Button addemploye = findViewById(R.id.btn_addEmploye);
        Button gstock = findViewById(R.id.btn_gStock);
        Button state = findViewById(R.id.btn_state);
        final EditText search = findViewById(R.id.txt_search);

        mRecyclerView = findViewById(R.id.recycle_viewAllemployes);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        list = new ArrayList<>();

        jsonArrayParse(null);

        addemploye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(AdminActivity.this,AddEmployeActivity.class),1);
            }
        });

        gstock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(AdminActivity.this,StockActivity.class),2);
            }
        });

       search.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence s, int start, int count, int after) {

           }

           @Override
           public void onTextChanged(CharSequence s, int start, int before, int count) {
                jsonArrayParse(search.getText().toString());
           }

           @Override
           public void afterTextChanged(Editable s) {

           }
       });

        //////////////////////
        ctx = this;
       // setContentView(R.layout.activity_main);
        mSensorService = new SensorService(getCtx());
        mServiceIntent = new Intent(getCtx(), mSensorService.getClass());
        if (!isMyServiceRunning(mSensorService.getClass())) {
            startService(mServiceIntent);
        }
        ////////////////////////
        state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse(Res.URL+"/chart.php");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                //mapIntent.setPackage("com.google.android.apps.maps");
               // mapIntent.setPackage("com.android.chrome");
                startActivity(mapIntent);
            }
        });
        //////////////////
    }

    public void jsonArrayParse(String search) {

        RequestQueue mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        String url = Res.URL+"/employe.php";
        if(search != null && !search.trim().equals(""))
            url+="?search="+search;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        list.clear();
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject obj = response.getJSONObject(i);
                                list.add(new Employe(obj.getString("cin"),obj.getString("nom"),obj.getString("prenom"),obj.getString("fonction"),obj.getString("grade"),0));
                            }
                            employeAdapter = new EmployeAdapter(getApplicationContext(),list);
                            employeAdapter.setOnItemClickListener(AdminActivity.this);
                            mRecyclerView.setAdapter(employeAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                //jsonParse();

            }
        });

        mRequestQueue.add(request);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 1)
        {
            jsonArrayParse(null);
        }
    }

    @Override
    public void onItemClick(int position) {
        Intent detailIntent = new Intent(AdminActivity.this,EmployeDetailActivity.class);
       //detailIntent.putExtra("nom",list.get(position).getNom()+" "+list.get(position).getPrenom());
        detailIntent.putExtra("mission","-");
        detailIntent.putExtra("transport","-");
        detailIntent.putExtra("emplacement","-");
        detailIntent.putExtra("employe",list.get(position));
        startActivityForResult(detailIntent,2);
    }

}
