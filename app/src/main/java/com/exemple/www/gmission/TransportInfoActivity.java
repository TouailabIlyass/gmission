package com.exemple.www.gmission;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import models.Avion;
import models.Ctm;
import models.Mission;
import models.Train;
import models.Transport;
import models.Vehicule;

public class TransportInfoActivity extends AppCompatActivity {

    private ArrayList<Transport> list;
    private TransportAdapter transportAdapter;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transport_info);

        mRecyclerView = findViewById(R.id.recycle_viewAlltransports);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        list = new ArrayList<>();

        jsonArrayParse();
    }

    public void jsonArrayParse() {

        RequestQueue mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        String url = Res.URL+"/transport.php?cin="+getIntent().getStringExtra("cin");
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        list.clear();
                        //Toast.makeText(getApplicationContext(),"start",Toast.LENGTH_SHORT).show();
                        try {
                            Transport transport = null;
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject obj = response.getJSONObject(i);
                                //list.add(new Mission(obj.getInt("id"),obj.getString("type"),obj.getString("date"),obj.getString("cin_emp")));
                                transport = new Transport(obj.getInt("id"),obj.getString("transport"),obj.getInt("type"),obj.getString("cin_emp"));
                                //Toast.makeText(getApplicationContext(),"start: "+transport.getType(),Toast.LENGTH_SHORT).show();

                                if(transport.getType()==1)
                                {   JSONObject jsavion =  obj.getJSONObject("avion");
                                    Avion avion = new Avion(0,jsavion.getString("numBon"),jsavion.getString("prix"),jsavion.getString("date"),jsavion.getString("destination"));
                                    transport.setAvion(avion);
                                }
                                else if(transport.getType()==2)
                                {
                                    JSONObject jstrain =  obj.getJSONObject("train");
                                    Train train = new Train(0,jstrain.getString("numBon"),jstrain.getString("prix"),null,jstrain.getString("destination"));
                                    transport.setTrain(train);
                                }
                                else if(transport.getType()==3)
                                {
                                    JSONObject jsctm =  obj.getJSONObject("ctm");
                                    Ctm ctm = new Ctm(0,jsctm.getString("numBon"),jsctm.getString("prix"),jsctm.getString("destination"));
                                    transport.setCtm(ctm);
                                }
                                else if(transport.getType()==4)
                                {
                                    JSONObject jsvehicule =  obj.getJSONObject("vehicule");
                                    Vehicule vehicule = new Vehicule(jsvehicule.getInt("id"),jsvehicule.getString("marque"),jsvehicule.getString("distance"),jsvehicule.getString("destination"),jsvehicule.getString("conducteur_cin"),null);
                                    transport.setVehicule(vehicule);
                                }
                                list.add(transport);
                            }
                            transportAdapter = new TransportAdapter(getApplicationContext(),list);
                            // missionAdapter.setOnItemClickListener(AdminActivity.this);
                            mRecyclerView.setAdapter(transportAdapter);
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
}
