package com.exemple.www.gmission;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import models.Employe;

public class MainActivity extends AppCompatActivity {

    private static Context appContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn_avion;
        btn_avion = findViewById(R.id.btn_avion);

        appContext = getApplicationContext();

        Database database = new Database(getApplicationContext());
        SQLiteDatabase db = database.getWritableDatabase();
        //database.deleteClient(null);
        //database.Del();
        database.onUpgrade(db,0,1);
        Employe employe = database.getEmploye();
        Res.setClient(employe);

        if (employe == null)
        {
            Intent i = new Intent(MainActivity.this,LoginActivity.class);
            startActivityForResult(i,1);
        }
        isAdmin(employe);

        btn_avion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,AvionActivity.class));
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            if(requestCode == 1)
            {
                Database database = new Database(getApplicationContext());
                Employe c=database.getEmploye();
                Res.setClient(c);
                //Toast.makeText(getApplicationContext(),c.getUsername()+"|"+c.getId()+"ccc",Toast.LENGTH_SHORT).show();
                this.onRestart();
                isAdmin(c);
            }
            else if(requestCode ==2)
                finish();
    }


    public static Context getAppContext()
    {
        return  appContext;
    }

    public void isAdmin(Employe employe)
    {
        if(employe!=null && employe.getType()==1)
        {
            startActivityForResult(new Intent(MainActivity.this,AdminActivity.class),2);
        }
        if(employe!=null && employe.getType()==0)
        {
            startActivityForResult(new Intent(MainActivity.this,EmployeActivity.class),2);
        }
    }
}
