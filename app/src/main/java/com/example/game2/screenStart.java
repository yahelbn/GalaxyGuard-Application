package com.example.game2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;

public class screenStart extends AppCompatActivity {


    private Button startButton;

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
                Intent intent = new Intent(screenStart.this,secondScreen.class);

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
}
