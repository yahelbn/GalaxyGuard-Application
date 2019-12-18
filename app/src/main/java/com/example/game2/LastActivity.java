package com.example.game2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.VideoView;

public class LastActivity extends AppCompatActivity {

    private boolean table_flag=false;
    private int best1, best2, best3;
    private Button playAgain;
    private  TableLayout table;
    private Button tableButton;
    private TextView textViewFirstPlace;
    private TextView textViewSecondPlace;
    private TextView textViewThirdPlace;
    private TextView textCurrentScore;

    ///Fragments


    private fragment_top fragmentTop;
    private fragment_bottom fragmentBottom;

    ////


    @Override
    public void onBackPressed() {

        Intent mainIntent = new Intent(LastActivity.this, screenStart.class);
        startActivity(mainIntent);
        finish();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last);

            //////Fragments/////

        fragmentTop=new fragment_top();
        fragmentBottom=new fragment_bottom();


        getSupportFragmentManager().beginTransaction().replace(R.id.top_frag,fragmentTop).replace(R.id.bottom_frag,fragmentBottom).commit();





        ////



    }


    public void updateScreenAfterCalc(){

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        int currentScore=pref.getInt("current_score", 0); // getting int num score in integer
        best1=pref.getInt("best1",0);
        best2=pref.getInt("best2",0);
        best3=pref.getInt("best3",0);

        if(currentScore>best3){
            best3=currentScore;
            editor.putInt("best3", best3);
            editor.apply();
        }

        if(currentScore>best2){
            int temp=best2;
            best2=currentScore;
            best3=temp;
            editor.putInt("best2", best2);
            editor.putInt("best3", best3);
            editor.apply();
        }

        if(currentScore>best1){
            int temp=best1;
            best1=currentScore;
            best2=temp;
            editor.putInt("best1", best1);
            editor.putInt("best2", best2);
            editor.apply();
        }

        textViewFirstPlace.setText(String.valueOf(best1));
        textViewSecondPlace.setText(String.valueOf(best2));
        textViewThirdPlace.setText(String.valueOf(best3));

        textCurrentScore.setText("Your score is:"+currentScore);
    }



    public void collapseTable(View view){


        table.setColumnCollapsed(1,table_flag);
        table.setColumnCollapsed(2,table_flag);



        if(table_flag){
            table_flag=false;
            tableButton.setText("Show Details");
        }

        else{
            table_flag=true;
            tableButton.setText("Hide Details");
        }
    }
}
