package com.exemple.www.gmission;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

import models.Employe;

public class LoginActivity extends AppCompatActivity {

    Button log_btn,inscrire;
    EditText name,pass;
    ProgressDialog progressDialog;
    Employe tmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tmp = null;

        log_btn = findViewById(R.id.loginbtn);
        inscrire = findViewById(R.id.sinscrirebtn);
        name = findViewById(R.id.usernameLogintxt);
        pass = findViewById(R.id.passLogintxt);
        progressDialog = new ProgressDialog(this);
        log_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Dologin d = new Dologin();
                    d.execute("");
                }catch(Exception e)
                {

                }
            }
        });

        inscrire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(LoginActivity.this,UserActivity.class));
            }
        });
    }

    private class Dologin extends AsyncTask<String, String, String> implements Runnable{
        boolean isSuccess;
        String namestr,passstr,f11z;
        Thread t1;
        public void run()
        {
            while(tmp == null)
            {
                try {
                    t1.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        private Dologin() {
            this.namestr = LoginActivity.this.name.getText().toString();
            this.passstr = LoginActivity.this.pass.getText().toString();
            this.f11z = "";
            this.isSuccess = false;
            tmp = null;
        }
        protected void onPreExecute() {
            try {
                LoginActivity.this.progressDialog.setMessage("Loading...");
                LoginActivity.this.progressDialog.show();
                super.onPreExecute();
            }catch(Exception e)
            {

            }
        }

        protected String doInBackground(String... params) {
            if (this.namestr.trim().equals("") || this.passstr.trim().equals("")) {
                this.f11z = "Please enter all fields....";
            }
            try{
                t1 = new Thread(this);
                t1.start();
                jsonRequestGetEmploye(new Employe(passstr,namestr,"","","",0));
                while(t1.isAlive())
                    Thread.sleep(1000);
                if (!tmp.getCin().equals("0")) {
                    this.isSuccess = true;
                    this.f11z = "Login successfull";
                    //Res.employe=c;
                    Database database = new Database(getApplicationContext());
                    database.deleteClient(null);
                    database.addClient(tmp);
                } else {
                    this.isSuccess = false;
                }
            } catch (Exception ex) {
                this.isSuccess = false;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Exceptions");
                stringBuilder.append(ex);
                this.f11z = stringBuilder.toString();
            }
            //}
            return this.f11z;
        }
        protected void onPostExecute(String s) {
            if (this.isSuccess) {
                finish();
               // Toast.makeText(LoginActivity.this.getBaseContext(), "fin", Toast.LENGTH_SHORT).show();
            }
            LoginActivity.this.progressDialog.hide();
        }

        /*protected void onPostExecute(String s) {
            Context baseContext = LoginActivity.this.getBaseContext();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("");
            stringBuilder.append(this.f11z);
            Toast.makeText(baseContext, stringBuilder.toString(), Toast.LENGTH_SHORT).show();
            if (this.isSuccess) {
               // Intent intent = new Intent(LoginActivity.this, MainActivity.class);
               // intent.putExtra("name", this.namestr);
               // LoginActivity.this.startActivity(intent);
                finish();
            }
            LoginActivity.this.progressDialog.hide();
        }*/

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Toast.makeText(getApplicationContext(),"REsult", Toast.LENGTH_LONG).show();
        Database database = new Database(getApplicationContext());
        database.deleteClient(null);
        database.addClient(tmp);
        finish();
    }

    private   Employe jsonRequestGetEmploye(Employe c)
    {
        final Employe employe = new Employe();
        RequestQueue mRequestQueue;
        mRequestQueue = Volley.newRequestQueue(getApplicationContext());

        JSONObject json = new JSONObject();
        try {
            json.put("cin",c.getCin());
            json.put("nom",c.getNom());
            json.put("prenom","");
            json.put("fonction","");
            json.put("grade","");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String url = Res.URL+"/employe.php?action=login";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            //Toast.makeText(getApplicationContext(),"tt"+response.toString(),Toast.LENGTH_LONG).show();
                            employe.setCin(response.getString("cin"));
                            employe.setNom(response.getString("nom"));
                            employe.setPrenom(response.getString("prenom"));
                            employe.setFonction(response.getString("fonction"));
                            employe.setGrade(response.getString("grade"));
                            employe.setType(response.getInt("type"));
                            tmp=employe;
                            // Dologin d=  new Dologin();
                            //d.execute("");
                        }catch (Exception e)
                        {
                            employe.setCin(0+"");
                            Toast.makeText(getApplicationContext(),"exp", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //serverResp.setText("Error getting response");
                employe.setCin(0+"");
                tmp=employe;
                //Toast.makeText(getApplicationContext(),"errrrrrr"+error.getMessage(),Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(),"Username ou mot de passe incorrect",Toast.LENGTH_LONG).show();
            }
        });
        mRequestQueue.add(jsonObjectRequest);
        return  employe;
    }
    @Override
    public void onBackPressed() {

        //setResult(1,getIntent().putExtra("login","false"));
        //finish();
        System.exit(0);
    }

}
