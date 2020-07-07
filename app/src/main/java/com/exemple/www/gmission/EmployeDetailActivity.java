package com.exemple.www.gmission;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import models.Employe;
import models.Location;

public class EmployeDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employe_detail);

        //TextView nom,mission,transport,emplacement;

        Button info,mission,transport,emplacement;

       /* nom = findViewById(R.id.lbl_empnom);
        mission = findViewById(R.id.lbl_empmission);
        transport = findViewById(R.id.lbl_emptransport);
        emplacement = findViewById(R.id.lbl_empemplacement);
        Intent data = getIntent();
        nom.setText(data.getStringExtra("nom"));
        mission.setText(data.getStringExtra("mission"));
        transport.setText(data.getStringExtra("transport"));
        emplacement.setText(data.getStringExtra("emplacement"));
        */
        //Intent data = getIntent();
        //Employe employe = (Employe)data.getSerializableExtra("employe");

        info = findViewById(R.id.btn_empinfo);
        mission = findViewById(R.id.btn_empmission);
        transport = findViewById(R.id.btn_emptransport);
        emplacement = findViewById(R.id.btn_empemplacement);

       info.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                Intent data = new Intent(EmployeDetailActivity.this,EmployeInfoActivity.class);
                data.putExtra("employe",getIntent().getSerializableExtra("employe"));
                startActivity(data);
           }
       });
        mission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Employe employe = (Employe)getIntent().getSerializableExtra("employe");
                Intent data = new Intent(EmployeDetailActivity.this,MissionInfoActivity.class);
                data.putExtra("cin",employe.getCin());
                startActivity(data);
            }
        });

        transport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Employe employe = (Employe)getIntent().getSerializableExtra("employe");
                Intent data = new Intent(EmployeDetailActivity.this,TransportInfoActivity.class);
                data.putExtra("cin",employe.getCin());
                startActivity(data);
            }
        });

        emplacement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation(Res.getEmploye().getCin());
            }
        });
    }

    public void getLocation(String cin){

        RequestQueue mRequestQueue;
        mRequestQueue = Volley.newRequestQueue(getApplicationContext());

        String url = Res.URL+"/location.php?cin="+cin;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            //Toast.makeText(getApplicationContext(),"tt"+response.toString(),Toast.LENGTH_LONG).show();
                            //finish()
                            String []location=response.getString("location").split(";");
                            //Uri gmmIntentUri = Uri.parse("geo:37.7749,-122.4194");
                              Uri gmmIntentUri = Uri.parse("https://www.google.com/maps/@"+location[0]+","+location[1]);
                              Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                             //mapIntent.setPackage("com.google.android.apps.maps");
                            //mapIntent.setPackage("com.android.chrome");
                            startActivity(mapIntent);

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
}
