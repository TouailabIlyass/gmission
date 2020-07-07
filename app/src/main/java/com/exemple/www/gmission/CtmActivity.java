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

import models.Ctm;
import models.Employe;
import models.Stock;
import models.Train;

public class CtmActivity extends AppCompatActivity {

    private Button valider;
    private ArrayList<Employe> list;
    private Spinner spEmploye;
    private EmployeSpinnerAdapter employeSpinnerAdapter;
    private TextView listempcin,listemp,lblerror;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ctm);
        valider = findViewById(R.id.btn_valide);
        spEmploye = findViewById(R.id.spinner_employe);
        listemp = findViewById(R.id.lbl_empsaccaompagne);
        listempcin = findViewById(R.id.lbl_empscinaccaompagne);
        lblerror = findViewById(R.id.lbl_error);
        final EditText numbon,prix,destination;
        numbon =  findViewById(R.id.txt_numbon);
        prix =  findViewById(R.id.txt_prix);
        destination =  findViewById(R.id.txt_destination);

        list = new ArrayList<>();

        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getStock(Double.valueOf(prix.getText().toString()),new Ctm(0,numbon.getText().toString(),prix.getText().toString(),destination.getText().toString(),listempcin.getText().toString()));
            }
        });
        jsonArrayParse();
    }

    public void addTransport(Ctm ctm){
        RequestQueue mRequestQueue;
        mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        JSONObject json = new JSONObject();
        try {
            json.put("id",0);
            json.put("numBon",ctm.getNumBon());
            json.put("prix",ctm.getPrix());
            json.put("destination",ctm.getDestination());
            json.put("accompagne",ctm.getAccompagne());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = Res.URL+"/ctm.php?cin="+Res.getEmploye().getCin();
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
                            employeSpinnerAdapter = new EmployeSpinnerAdapter(getApplicationContext(),list);
                            spEmploye.setAdapter(employeSpinnerAdapter);
                            spEmploye.setOnItemSelectedListener(new MyOnItemSelectedListener());
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
            Employe emp= (Employe) parent.getItemAtPosition(pos);
            Toast.makeText(parent.getContext(), "Item is " +emp.getCin(), Toast.LENGTH_LONG).show();
            if(!listemp.getText().toString().contains(emp.getNom()+" "+emp.getPrenom()))
            {
                if(listemp.getText().toString().isEmpty())
                    listemp.setText(listemp.getText().toString()+emp.getNom()+" "+emp.getPrenom());
                else
                    listemp.setText(listemp.getText().toString()+", "+emp.getNom()+" "+emp.getPrenom());
                listempcin.setText(listempcin.getText().toString()+emp.getCin()+",");
            }
        }
        public void onNothingSelected(AdapterView parent) {
            // Do nothing.
        }
    }

    private void getStock(final double prix,final Ctm ctm)
    {
        RequestQueue mRequestQueue;
        mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        String url = Res.URL+"/stock.php?type=VTT";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Stock stock;
                        try{
                            stock = new Stock(response.getDouble("somme"),response.getString("type"));
                            if(stock.getSomme()>=prix)
                            {
                                addStock(new Stock(stock.getSomme()-prix,stock.getType()));
                                addTransport(ctm);
                                lblerror.setVisibility(View.GONE);
                            }
                            else
                                lblerror.setVisibility(View.VISIBLE);

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

    private  void addStock(Stock stock)
    {
        RequestQueue mRequestQueue;
        mRequestQueue = Volley.newRequestQueue(getApplicationContext());

        JSONObject json = new JSONObject();
        try {
            json.put("somme",stock.getSomme());
            json.put("type",stock.getType());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String url = Res.URL+"/stock.php/";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, json,
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
}
