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

import models.Employe;
import models.Mission;

public class MissionInfoActivity extends AppCompatActivity {

    private ArrayList<Mission> list;
    private MissionAdapter missionAdapter;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission_info);

        mRecyclerView = findViewById(R.id.recycle_viewAllmission);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        list = new ArrayList<>();

        jsonArrayParse();
    }
    public void jsonArrayParse() {

        RequestQueue mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        String url = Res.URL+"/mission.php?cin="+getIntent().getStringExtra("cin");
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        list.clear();
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject obj = response.getJSONObject(i);
                                list.add(new Mission(obj.getInt("id"),obj.getString("type"),obj.getString("date"),obj.getString("cin_emp")));
                               //Toast.makeText(getApplicationContext(),"fff",Toast.LENGTH_LONG).show();
                            }
                            missionAdapter = new MissionAdapter(getApplicationContext(),list);
                           // missionAdapter.setOnItemClickListener(AdminActivity.this);
                            mRecyclerView.setAdapter(missionAdapter);
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
