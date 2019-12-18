package com.example.game2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;



import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.zip.Inflater;

public class fragment_top extends Fragment {




    private boolean table_flag=false;
    private int best1, best2, best3;
    private Button playAgain;
    private TableLayout table;
    private Button tableButton;
    private TextView textViewFirstPlace;
    private TextView textViewSecondPlace;
    private TextView textViewThirdPlace;
    private TextView textCurrentScore;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_top,container,false);




        table=v.findViewById(R.id.tableScore);
        tableButton=v.findViewById(R.id.tableButton);
        playAgain=v.findViewById(R.id.playAgain);
        textViewFirstPlace=v.findViewById(R.id.textViewFirstPlace);
        textViewSecondPlace=v.findViewById(R.id.textViewSecondPlace);
        textViewThirdPlace=v.findViewById(R.id.textViewThirdPlace);
        textCurrentScore=v.findViewById(R.id.currentScore);

        updateScreenAfterCalc();




        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(getContext(),secondScreen.class);
                MediaPlayer ring= MediaPlayer.create(getContext(),R.raw.button_sound_ms);
                ring.start();

                if(Build.VERSION.SDK_INT >= 26) {
                    Vibrator brator = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
                    // Vibrate for 500 milliseconds
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        brator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                    } else {
                        //deprecated in API 26
                        brator.vibrate(500);
                    }
                }


                startActivity(newIntent);

            }
        });


        Button collapseTablebutton = (Button) v.findViewById(R.id.tableButton);
        collapseTablebutton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
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
        });



        return v;
    }


    public void updateScreenAfterCalc(){



        SharedPreferences pref =  getContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
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
