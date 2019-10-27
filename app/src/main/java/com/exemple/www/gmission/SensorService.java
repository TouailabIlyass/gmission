package com.exemple.www.gmission;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import models.Avion;
import models.Employe;
import models.Notification;
import models.Stock;

/**
 * Created by fabio on 30/01/2016.
 */
public class SensorService extends Service {
    public int counter=0;
    public SensorService(Context applicationContext) {
        super();
        Log.i("HERE", "here I am!");
    }

    public SensorService() {
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        startTimer();
        return START_STICKY;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("EXIT", "ondestroy!");
        Intent broadcastIntent = new Intent(this, SensorRestarterBroadcastReceiver.class);

        sendBroadcast(broadcastIntent);
        stoptimertask();
    }

    private Timer timer;
    private TimerTask timerTask;
    long oldTime=0;
    public void startTimer() {
        //set a new Timer
        timer = new Timer();

        //initialize the TimerTask's job
        initializeTimerTask();

        //schedule the timer, to wake up every 1 second
        timer.schedule(timerTask, 1000, 1000); //
    }

    /**
     * it sets the timer to print the counter every x seconds
     */
    public void initializeTimerTask() {
        timerTask = new TimerTask() {
            public void run() {
                Log.i("in timer", "in timer ++++  "+ (counter++));
                jsonArrayParse();
            }
        };
    }

    /**
     * not needed
     */
    public void stoptimertask() {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void jsonArrayParse() {

        RequestQueue mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        String url = Res.URL+"/notification.php?del";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject obj = response.getJSONObject(i);
                                Notfication(obj.getString("cin_emp"));
                            }
                            delNotification();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mRequestQueue.add(request);
    }
    public void Notfication(String content)
    {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Bitmap bmp = BitmapFactory.decodeResource(this.getResources(),R.drawable.bulletgreen);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setContentTitle(content)
                .setContentText(" a ajouter un transport")
                .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
                .setLargeIcon(bmp)
                .setAutoCancel(true)
                .setNumber(1);
        builder.setDefaults(android.app.Notification.DEFAULT_SOUND| android.app.Notification.DEFAULT_VIBRATE);
        builder.setVibrate(new long[]{500,1000,500});
        //builder.setSound(Uri.parse("android.resource://"+getPackageName()+"/"+R.raw...));
        notificationManager.notify(1,builder.build());
    }

    private  void delNotification()
    {
        RequestQueue mRequestQueue;
        mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        String url = Res.URL+"/notification.php/";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            //Toast.makeText(getApplicationContext(),"tt"+response.toString(),Toast.LENGTH_LONG).show();
                            //finish();
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