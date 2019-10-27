package com.exemple.www.gmission;

import android.content.Intent;
import android.location.Location;

import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;


public class EmployeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employe);

        Button profile,mission,transport;
        FloatingActionButton fab = findViewById(R.id.fab_1);

        profile = findViewById(R.id.btn_profile);
        mission = findViewById(R.id.btn_addmission);
        transport = findViewById(R.id.btn_addtransport);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent(EmployeActivity.this,EmployeInfoActivity.class);
                data.putExtra("employe",Res.getEmploye());
                startActivity(data);
            }
        });

        mission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EmployeActivity.this,AddMissionActivity.class));
            }
        });

        transport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EmployeActivity.this,AddTransportActivity.class));
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Database database = new Database(getApplicationContext());
                database.deleteClient(null);
                finish();
            }
        });
        String locationProviders = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        if (locationProviders == null || locationProviders.equals("")) {

            startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS),1);
        }
        else
            getLocation();
    }
    public void addLocation(models.Location location){

        RequestQueue mRequestQueue;
        mRequestQueue = Volley.newRequestQueue(getApplicationContext());

        JSONObject json = new JSONObject();
        try {
            json.put("cin_emp",location.getCin_emp());
            json.put("location",location.getLocation());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String url = Res.URL+"/location.php/";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            //Toast.makeText(getApplicationContext(),"tt"+response.toString(),Toast.LENGTH_LONG).show();
                            //finish();

                        }catch (Exception e)
                        {
                            Toast.makeText(getApplicationContext(),"exp", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //serverResp.setText("Error getting response");
                Toast.makeText(getApplicationContext(),"errrrrrr"+error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
        mRequestQueue.add(jsonObjectRequest);
    }

    private void getLocation()
    {
        SmartLocation.with(getApplicationContext()).location()
                .start(new OnLocationUpdatedListener() {
                    @Override
                    public void onLocationUpdated(Location location) {
                        Toast.makeText(getApplicationContext(),location.getLatitude()+","+location.getLongitude(),Toast.LENGTH_SHORT).show();
                        addLocation(new models.Location(Res.getEmploye().getCin(),location.getLatitude()+";"+location.getLongitude()));
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 1)
        {
            getLocation();
        }
    }
}
