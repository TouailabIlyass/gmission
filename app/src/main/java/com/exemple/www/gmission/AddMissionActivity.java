package com.exemple.www.gmission;

import android.app.DatePickerDialog;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import models.Employe;
import models.Mission;

public class AddMissionActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

   private TextView lbldate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mission);

        Button add;
        final EditText type,date;
        type = findViewById(R.id.txt_typemission);
        //date = findViewById(R.id.txt_datemission);
        add = findViewById(R.id.btn_saveMission);
        lbldate =  findViewById(R.id.lbl_date);
        setDate(lbldate);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jsonRequestAddMission(new Mission(0,type.getText().toString(),lbldate.getText().toString(),Res.getEmploye().getCin()));
            }
        });

        Button bdate = findViewById(R.id.btn_date);

        bdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date mission");
            }
        });

    }

    private void jsonRequestAddMission(Mission mission)
    {
        RequestQueue mRequestQueue;
        mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        JSONObject json = new JSONObject();
        try {
            json.put("id",mission.getId());
            json.put("type",mission.getType());
            json.put("date",mission.getDate());
            json.put("cin_emp",mission.getCin_emp());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = Res.URL+"/mission.php";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
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
