package com.exemple.www.gmission;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import models.Employe;

public class EmployeInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employe_info);

        TextView lblcin,lblnom,lblfonction,lblgrade;

        lblcin = findViewById(R.id.lbl_cin);
        lblnom = findViewById(R.id.lbl_nom);
        //lblprenom = findViewById(R.id.lbl_prenom);
        lblfonction = findViewById(R.id.lbl_fonction);
        lblgrade = findViewById(R.id.lbl_grade);

        Intent data =  getIntent();
        Employe employe = (Employe)data.getSerializableExtra("employe");
        lblcin.setText(employe.getCin());
        lblnom.setText(employe.getNom()+" "+employe.getPrenom());
        //lblprenom.setText(employe.getPrenom());
        lblfonction.setText(employe.getFonction());
        lblgrade.setText(employe.getGrade());

        /*lblcin.setText(Res.getEmploye().getCin());
        lblnom.setText(Res.getEmploye().getNom());
        lblprenom.setText(Res.getEmploye().getPrenom());
        lblfonction.setText(Res.getEmploye().getFonction());
        lblgrade.setText(Res.getEmploye().getGrade());*/
    }
}
