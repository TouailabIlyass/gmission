package com.exemple.www.gmission;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import models.Avion;
import models.Employe;

public class AddEmployeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employe);

        Button save = findViewById(R.id.btn_saveEmploye);

        final EditText txtcin,txtnom,txtprenom,txtfct,txtgrade;
        txtcin = findViewById(R.id.txt_cin);
        txtnom = findViewById(R.id.txt_nom);
        txtprenom = findViewById(R.id.txt_prenom);
        txtfct = findViewById(R.id.txt_fonction);
        txtgrade = findViewById(R.id.txt_grade);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addEmploye(new Employe(txtcin.getText().toString(),txtnom.getText().toString(),txtprenom.getText().toString(),txtfct.getText().toString(),txtgrade.getText().toString(),0));
            }
        });
    }

    public void addEmploye(Employe employe){

        RequestQueue mRequestQueue;
        mRequestQueue = Volley.newRequestQueue(getApplicationContext());

        JSONObject json = new JSONObject();
        try {
            json.put("cin",employe.getCin());
            json.put("nom",employe.getNom());
            json.put("prenom",employe.getPrenom());
            json.put("fonction",employe.getFonction());
            json.put("grade",employe.getGrade());
            json.put("type",employe.getType());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String url = Res.URL+"/employe.php/";
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

}
