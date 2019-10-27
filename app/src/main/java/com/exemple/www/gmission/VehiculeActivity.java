package com.exemple.www.gmission;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import models.Avion;
import models.Employe;
import models.Vehicule;

public class VehiculeActivity extends AppCompatActivity {
    private TextView empcin,emp,conducteur;
    private ArrayList<Employe> list;
    private Spinner spEmploye,spConducteur;
    private EmployeSpinnerAdapter employeSpinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicule);

        final EditText marque,destination,distance;
        Button valider;

        spEmploye = findViewById(R.id.spinner_employe_vhc);
        spConducteur = findViewById(R.id.spinner_conducteur);

        marque = findViewById(R.id.txt_vehiculemarque);
        destination = findViewById(R.id.txt_vehiculedestination);
        distance = findViewById(R.id.txt_vehiculedistance);
        conducteur = findViewById(R.id.txt_vehiculeconducteur);
        empcin = findViewById(R.id.lbl_empscinaccaompagne_vhc);
        emp = findViewById(R.id.lbl_empsaccaompagne_vhc);
        valider = findViewById(R.id.btn_valider_vhc);

        list = new ArrayList<>();
        conducteur.setText("11");
        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //addTransport(new Vehicule(0,marque.getText().toString(),distance.getText().toString(),destination.getText().toString(),conducteur.getText().toString(),empcin.getText().toString()));
            addTransport(new Vehicule(0,marque.getText().toString(),distance.getText().toString(),destination.getText().toString(),conducteur.getText().toString(),null));
            }
        });
        jsonArrayParse();
    }
    public void addTransport(Vehicule vehicule){
        RequestQueue mRequestQueue;
        mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        JSONObject json = new JSONObject();
        try {
            json.put("id",0);
            json.put("marque",vehicule.getMarque());
            json.put("destination",vehicule.getDestination());
            json.put("distance",vehicule.getDistance());
            json.put("conducteur_cin",vehicule.getConducteur_cin());
            //json.put("accompagne",vehicule.getAccompagne());
            JSONArray jsonArray = new JSONArray();
            String []str = empcin.getText().toString().split(",");
            for(int i=0;i<str.length;i++)
            {
                jsonArray.put(i,str[i]);

            }
            json.put("accompagne",jsonArray);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = Res.URL+"/vehicule.php/";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            //Toast.makeText(getApplicationContext(),"tt"+response.toString(),Toast.LENGTH_LONG).show();
                            finish();
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

    public void jsonArrayParse() {
        RequestQueue mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        String url = Res.URL+"/employe.php/";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        list.clear();
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject obj = response.getJSONObject(i);
                                list.add(new Employe(obj.getString("cin"),obj.getString("nom"),obj.getString("prenom"),obj.getString("fonction"),obj.getString("grade"),obj.getInt("type")));
                            }
                            //employeAdapter = new EmployeAdapter(getApplicationContext(),list);
                            employeSpinnerAdapter = new EmployeSpinnerAdapter(getApplicationContext(),list);
                            spEmploye.setAdapter(employeSpinnerAdapter);
                            spEmploye.setOnItemSelectedListener(new MyOnItemSelectedListener());
                            /////
                            spConducteur.setAdapter(employeSpinnerAdapter);
                            spConducteur.setOnItemSelectedListener(new MyOnItemSelectedListener_conducteur());
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

    class MyOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent,
                                   View view, int pos, long id) {
            // Toast.makeText(parent.getContext(), "Item is " +parent.getItemAtPosition(pos)., Toast.LENGTH_LONG).show();
            Employe employe= (Employe) parent.getItemAtPosition(pos);
            Toast.makeText(parent.getContext(), "Item is " +employe.getCin(), Toast.LENGTH_LONG).show();
            if(!emp.getText().toString().contains(employe.getNom()+" "+employe.getPrenom()))
            {
                emp.setText(emp.getText().toString()+employe.getNom()+" "+employe.getPrenom()+", ");
                empcin.setText(empcin.getText().toString()+employe.getCin()+",");
            }
        }
        public void onNothingSelected(AdapterView parent) {
            // Do nothing.
        }
    }
    class MyOnItemSelectedListener_conducteur implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent,
                                   View view, int pos, long id) {
            // Toast.makeText(parent.getContext(), "Item is " +parent.getItemAtPosition(pos)., Toast.LENGTH_LONG).show();
            Employe employe= (Employe) parent.getItemAtPosition(pos);
            conducteur.setText(employe.getCin());
        }
        public void onNothingSelected(AdapterView parent) {
            // Do nothing.
        }
    }
}