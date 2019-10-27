package com.exemple.www.gmission;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.BoringLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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

import models.Stock;

public class GSTockActivity extends AppCompatActivity {

    private static Stock stock;
    private static String status;
    private static final String ADD = "add";
    private static final String UPDATE = "update";
    private LinearLayout linearLayout;
    private EditText stock_value;
    private TextView solde;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gstock);

        final TextView title,action;

        stock_value = findViewById(R.id.txt_stock_value);

        title = findViewById(R.id.lbl_stock_title);
        solde = findViewById(R.id.lbl_stock_solde);
        action = findViewById(R.id.lbl_stock_action);
        Intent data = getIntent();

        title.setText("Stock "+data.getStringExtra("type"));

        getStock(data.getStringExtra("type"));


        final Button addstock,modifierstock,valider;

        addstock = findViewById(R.id.btn_addStock);
        modifierstock = findViewById(R.id.btn_modifierStock);
        valider = findViewById(R.id.btn_valider_stock);

        linearLayout = findViewById(R.id.linear_addStock);

        addstock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status = ADD;
                makeVisible(true);
                action.setText("Ajouter solde");
            }
        });

        modifierstock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status = UPDATE;
                makeVisible(true);
                action.setText("Modifier la somme");
            }
        });

        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(status==ADD)
                {
                    addStock(new Stock(stock.getSomme()+Double.parseDouble(stock_value.getText().toString()),stock.getType()));
                }
                else if(status==UPDATE)
                {
                    addStock(new Stock(Double.parseDouble(stock_value.getText().toString()),stock.getType()));
                }
                makeVisible(false);
            }
        });
    }

    private void getStock(final String type)
    {
        RequestQueue mRequestQueue;
        mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        String url = Res.URL+"/stock.php?type="+type;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            //Toast.makeText(getApplicationContext(),"tt"+response.toString(),Toast.LENGTH_LONG).show();
                            stock = new Stock(response.getDouble("somme"),response.getString("type"));
                            solde.setText("Le Stock "+type+"a un somme de "+stock.getSomme()+" MAD");
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


    private void makeVisible(Boolean aBoolean)
    {
        if(aBoolean) {
            linearLayout.setVisibility(View.VISIBLE);
            stock_value.setVisibility(View.VISIBLE);
        }
        else
        {
            linearLayout.setVisibility(View.INVISIBLE);
            stock_value.setVisibility(View.INVISIBLE);
        }
    }
}
