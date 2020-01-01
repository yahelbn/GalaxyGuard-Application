package com.example.game2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static com.example.game2.LastActivity.fragmentBottom;

public class screenStart extends AppCompatActivity {


    private Button startButton;



    private static double latitudeCurrent;
    //private  double altitudeCurrent;
    private static double longTitude;


            private Intent intent;
    public Location userLocation;
    private FusedLocationProviderClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_start);





        MediaPlayer ring= MediaPlayer.create(screenStart.this,R.raw.maple_song_game);
        ring.start();

        startButton=findViewById(R.id.startButton);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 intent = new Intent(screenStart.this,secondScreen.class);

                requestPermission();

                client= LocationServices.getFusedLocationProviderClient(screenStart.this);

                if(ActivityCompat.checkSelfPermission(screenStart.this, ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
                    return;
                }



                client.getLastLocation().addOnSuccessListener(screenStart.this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if(location!=null){

                           // altitudeCurrent=location.getAltitude();
                            latitudeCurrent=location.getLatitude();
                            longTitude=location.getLongitude();
                            userLocation=location;
                    Log.i("Print inside start","the location"+latitudeCurrent);
                    fragment_bottom.setLatitude(latitudeCurrent);
                            fragment_bottom.setLongitude(longTitude);
                            fragment_top.setLatitudeCurrent(latitudeCurrent);
                            fragment_top.setLongtitudeCurrent(longTitude);



                        }
                    }
                });



                if(Build.VERSION.SDK_INT >= 26) {
                    Vibrator brator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
// Vibrate for 500 milliseconds
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        brator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                    } else {
                        //deprecated in API 26
                        brator.vibrate(500);
                    }
                }

                MediaPlayer ring= MediaPlayer.create(screenStart.this,R.raw.button_sound_ms);
                ring.start();

                startActivity(intent);
            }
        });







    }





    private void requestPermission(){
        ActivityCompat.requestPermissions(this,new String[]{ACCESS_FINE_LOCATION},1);

    }



}
