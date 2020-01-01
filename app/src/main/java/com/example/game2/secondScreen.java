package com.example.game2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class secondScreen extends AppCompatActivity {


    private Button easyBtn;
    private Button hardBtn;
    private Button sensorGameBtn;


    @Override
    public void onBackPressed() {

        Intent mainIntent = new Intent(secondScreen.this, screenStart.class);
        startActivity(mainIntent);
        finish();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_screen);





        easyBtn=findViewById(R.id.easyGameBtn);
        hardBtn=findViewById(R.id.HardGameBtn);
        sensorGameBtn=findViewById(R.id.sensorGameBtn);


        easyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(secondScreen.this,MainActivity.class);
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
                MediaPlayer ring= MediaPlayer.create(secondScreen.this,R.raw.button_sound_ms);
                ring.start();
                intent.putExtra("kind_of_btn",0);
                startActivity(intent);
            }
        });


        hardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(secondScreen.this,MainActivity.class);
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


                MediaPlayer ring= MediaPlayer.create(secondScreen.this,R.raw.button_sound_ms);
                ring.start();
                intent.putExtra("kind_of_btn",1);
                startActivity(intent);
            }
        });

        sensorGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(secondScreen.this,MainActivity.class);
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

                MediaPlayer ring= MediaPlayer.create(secondScreen.this,R.raw.button_sound_ms);
                ring.start();
                intent.putExtra("kind_of_btn",2);
                startActivity(intent);

            }
        });
    }
}
