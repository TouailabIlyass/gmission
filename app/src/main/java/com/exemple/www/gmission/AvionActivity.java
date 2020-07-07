package com.exemple.www.gmission;

import android.app.DatePickerDialog;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import models.Avion;
import models.Employe;
import models.Notification;
import models.Stock;

public class AvionActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    private Button valider;
    private ArrayList<Employe> list;
    private Spinner spEmploye;
    private EmployeSpinnerAdapter employeSpinnerAdapter;
    private TextView listempcin,listemp,lbl_error, lbldate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avion);

        valider = findViewById(R.id.btn_valide);
        spEmploye = findViewById(R.id.spinner_employe);
        listemp = findViewById(R.id.lbl_empsaccaompagne);
        listempcin = findViewById(R.id.lbl_empscinaccaompagne);
        lbl_error = findViewById(R.id.lbl_error);
        final EditText numbon,prix,destination;

        numbon =  findViewById(R.id.txt_numbon);
        prix =  findViewById(R.id.txt_prix);
        lbldate =  findViewById(R.id.lbl_date);
        destination =  findViewById(R.id.txt_destination);

        setDate(lbldate);

        list = new ArrayList<>();

        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getStock(Double.valueOf(prix.getText().toString()),new Avion(0,numbon.getText().toString(),prix.getText().toString(),lbldate.getText().toString(),destination.getText().toString(),listempcin.getText().toString()));
            }
        });

        Button bdate = findViewById(R.id.btn_date);

        bdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date avion");
            }
        });
        jsonArrayParse();

    }
    public void addTransport(Avion avion){
        RequestQueue mRequestQueue;
        mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        JSONObject json = new JSONObject();
        try {
            json.put("id",0);
            json.put("numBon",avion.getNumBon());
            json.put("prix",avion.getPrix());
            json.put("date",avion.getDate());
            json.put("destination",avion.getDestination());
            json.put("accompagne",avion.getAccompagne());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = Res.URL+"/avion.php?cin="+Res.getEmploye().getCin();
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
                            //Toast.makeText(getApplicationContext(),"exp11 "+list.size(), Toast.LENGTH_LONG).show();
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
            Toast.makeText(parent.getContext(), "Item is "+id +emp.getCin(), Toast.LENGTH_LONG).show();
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


    private void getStock(final double prix,final Avion avion)
    {
        RequestQueue mRequestQueue;
        mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        String url = Res.URL+"/stock.php?type=VTA";
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
                                addTransport(avion);
                                addNotification(new Notification(0,Res.getEmploye().getCin(),prix+"","VTA"));
                                lbl_error.setVisibility(View.GONE);
                            }
                            else
                                lbl_error.setVisibility(View.VISIBLE);

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

    private  void addNotification(Notification  notification)
    {
        RequestQueue mRequestQueue;
        mRequestQueue = Volley.newRequestQueue(getApplicationContext());

        JSONObject json = new JSONObject();
        try {
            json.put("id",notification.getId());
            json.put("cin_emp",notification.getCin_emp());
            json.put("prix",notification.getPrix());
            json.put("type",notification.getType());
            json.put("deleted",0);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String url = Res.URL+"/notification.php/";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, json,
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
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        lbldate.setText(dayOfMonth+"/"+(month+1)+"/"+year);
    }

    public void setDate (TextView view){

        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String date = formatter.format(today);
        view.setText(date);
    }
}


