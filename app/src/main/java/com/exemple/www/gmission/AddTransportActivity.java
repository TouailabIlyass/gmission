package com.exemple.www.gmission;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AddTransportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transport);

        Button avion,train,vehicule,ctm;

        avion = findViewById(R.id.btn_typeavion);
        train = findViewById(R.id.btn_typetrain);
        vehicule = findViewById(R.id.btn_typevehicule);
        ctm = findViewById(R.id.btn_typectm);

        avion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddTransportActivity.this,AvionActivity.class));
            }
        });
        ctm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddTransportActivity.this,CtmActivity.class));
            }
        });

        train.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddTransportActivity.this,TrainActivity.class));
            }
        });

        vehicule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddTransportActivity.this,VehiculeActivity.class));
            }
        });
    }

    public void Notfication()
    {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Bitmap bmp = BitmapFactory.decodeResource(this.getResources(),R.drawable.bulletgreen);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setContentTitle("title")
                .setContentText("text")
                .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
                .setLargeIcon(bmp)
                .setAutoCancel(true)
                .setNumber(1);
        builder.setDefaults(Notification.DEFAULT_SOUND|Notification.DEFAULT_VIBRATE);
        builder.setVibrate(new long[]{500,1000,500});
        //builder.setSound(Uri.parse("android.resource://"+getPackageName()+"/"+R.raw...));
        notificationManager.notify(1,builder.build());
    }
}
