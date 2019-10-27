package com.exemple.www.gmission;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StockActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);

        Button vtt,vta;

        vtt = findViewById(R.id.btn_stock_vtt);
        vta = findViewById(R.id.btn_stock_vta);

        vtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StockActivity.this,GSTockActivity.class);
                i.putExtra("type","VTT");
                startActivityForResult(i,1);
            }
        });


        vta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StockActivity.this,GSTockActivity.class);
                i.putExtra("type","VTA");
                startActivityForResult(i,2);
            }
        });
    }
}
