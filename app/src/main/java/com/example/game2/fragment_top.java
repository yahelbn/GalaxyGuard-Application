package com.example.game2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
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
    private static int best1, best2, best3;
    private static double lati1,lati2,lati3=0;
    private static double Long1,Long2,Long3=0;
    private Button playAgain;
    private TableLayout table;
    private Button tableButton;
    private TextView textViewFirstPlace;
    private TextView textViewSecondPlace;
    private TextView textViewThirdPlace;
    private TextView textCurrentScore;


    //Location
    private static double latitudeCurrent;
    private static double longtitudeCurrent;


    //users

    public static UserRecord usernumber1;
    public static UserRecord usernumber2;
    public static UserRecord usernumber3;
    private fragmentTopListener listener;

    public interface fragmentTopListener{
        void onInputUser(UserRecord userRecord);
    }



    public static void setLatitudeCurrent(double latitudeCurrent) {
        fragment_top.latitudeCurrent = latitudeCurrent;
    }

    public static void setLongtitudeCurrent(double longtitudeCurrent) {
        fragment_top.longtitudeCurrent = longtitudeCurrent;
    }

    public static double getLatitudeCurrent() {
        return latitudeCurrent;
    }

    public static double getLongtitudeCurrent() {
        return longtitudeCurrent;
    }



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

        Log.i("user1"," - "+usernumber1.getLatitude());
        Log.i("user2"," - "+usernumber2.getLatitude());
        Log.i("user3"," - "+usernumber3.getLatitude());

        textViewFirstPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onInputUser(usernumber1);

            }
        });

        textViewSecondPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onInputUser(usernumber2);

            }
        });

        textViewThirdPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onInputUser(usernumber3);

            }
        });


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

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof fragmentTopListener){
            listener=(fragmentTopListener)context;
        }
        else{
            throw new RuntimeException(context.toString()+"Must implements fragmentTop");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener=null;
    }

    public void updateScreenAfterCalc(){



        SharedPreferences pref =  getContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        int currentScore=pref.getInt("current_score", 0); // getting int num score in integer

        best1=pref.getInt("best1",0);
        best2=pref.getInt("best2",0);
        best3=pref.getInt("best3",0);


//        //Locations
        latitudeCurrent=getLatitudeCurrent(); //
         longtitudeCurrent=getLongtitudeCurrent(); //
//
//        //Latitude
//
// getting int num score in integer
        String checkLati1=pref.getString("latitude1", null);
        if(checkLati1!=null)
            lati1=Double.valueOf(checkLati1);

        String checkLati2=pref.getString("latitude2", null);
        if(checkLati2!=null)
            lati2=Double.valueOf(checkLati2);

        String checkLati3=pref.getString("latitude3", null);
        if(checkLati3!=null)
            lati3=Double.valueOf(checkLati3);

//
//        //Longtitude

        String checkLong1=pref.getString("long1", null);
        if(checkLong1!=null)
            Long1=Double.valueOf(checkLong1);

        String checkLong2=pref.getString("long2", null);
        if(checkLong2!=null)
            Long2=Double.valueOf(checkLong2);

        String checkLong3=pref.getString("long3", null);
        if(checkLong3!=null)
            Long3=Double.valueOf(checkLong3);


        usernumber1=new UserRecord(lati1,Long1,"name",best1);
        usernumber2=new UserRecord(lati2,Long2,"name",best2);
        usernumber3=new UserRecord(lati3,Long3,"name",best3);







        if(currentScore>usernumber3.getScore()){
            usernumber3.setScore(currentScore);
            usernumber3.setLatitude(latitudeCurrent);
            usernumber3.setLongtitude(longtitudeCurrent);
            editor.putInt("best3", usernumber3.getScore());
           editor.putString("latitude3", String.valueOf(usernumber3.getLatitude()));
            editor.putString("long3", String.valueOf(usernumber3.getLongtitude()));


            editor.apply();
        }

        if(currentScore>usernumber2.getScore()){
            int temp=usernumber2.getScore();
            double tempLati=usernumber2.getLatitude();
            double tempLong=usernumber2.getLongtitude();
            usernumber2.setScore(currentScore);
            usernumber2.setLatitude(latitudeCurrent);
            usernumber2.setLongtitude(longtitudeCurrent);
            usernumber3.setScore(temp);
            usernumber3.setLatitude(tempLati);
            usernumber3.setLongtitude(tempLong);
            editor.putInt("best2", usernumber2.getScore());
            editor.putInt("best3", usernumber3.getScore());
            editor.putString("latitude3", String.valueOf(usernumber3.getLatitude()));
            editor.putString("long3", String.valueOf(usernumber3.getLongtitude()));
            editor.putString("latitude2", String.valueOf(usernumber2.getLatitude()));
            editor.putString("long2", String.valueOf(usernumber2.getLongtitude()));
            editor.apply();
        }

        if(currentScore>usernumber1.getScore()){
            int temp=usernumber1.getScore();
            double tempLati=usernumber1.getLatitude();
            double tempLong=usernumber1.getLongtitude();
            usernumber1.setScore(currentScore);
            usernumber1.setLatitude(latitudeCurrent);
            usernumber1.setLongtitude(longtitudeCurrent);
            usernumber2.setScore(temp);
            usernumber2.setLatitude(tempLati);
            usernumber2.setLongtitude(tempLong);
            editor.putInt("best1", usernumber1.getScore());
            editor.putInt("best2", usernumber2.getScore());
            editor.putString("latitude1", String.valueOf(usernumber1.getLatitude()));
            editor.putString("long1", String.valueOf(usernumber1.getLongtitude()));
            editor.putString("latitude2", String.valueOf(usernumber2.getLatitude()));
            editor.putString("long2", String.valueOf(usernumber2.getLongtitude()));
            editor.apply();
        }

        textViewFirstPlace.setText(String.valueOf(usernumber1.getScore()));
        textViewSecondPlace.setText(String.valueOf(usernumber2.getScore()));
        textViewThirdPlace.setText(String.valueOf(usernumber3.getScore()));
        usernumber1.setText("First place");
        usernumber2.setText("Second place");
        usernumber3.setText("third place");


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
