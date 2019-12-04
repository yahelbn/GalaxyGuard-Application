package com.example.game2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Timer;
import java.util.TimerTask;

import tyrantgit.explosionfield.ExplosionField;

import static android.os.Build.*;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {



    //activity

    private MainActivity intentMain;


    //screen size
    private int screenWidth;
    private int screenHeight;


    //Rocket 1
    private ImageView rocketUp;
    private boolean rocketupFinishscreen=true;


    //Rocket 2
    private ImageView rocketUp2;
    private boolean rocketup2Finishscreen=true;


    //Rocket 3
    private ImageView rocketUp3;
    private boolean rocketup3Finishscreen=true;



    private Handler handler=new Handler();
    private Timer timer=new Timer();
///////////////////////////////////////


    //Layouts/////
    private FrameLayout frame;
    private RelativeLayout relLayout;

    //Movements/////////////////////////////////////////////////////////////////////////////////////////////////

    //Positions

    //Position rocket 1
    private float positionX;
    private float positionY;

    //Position rocket 2
    private float positionX2;
    private float positionY2;

    //Position rocket 3
    private float positionX3;
    private float positionY3;

    private boolean action_left,action_right;
    private float currentAttackLocation;

///////////////////////////////////////////////////////////////////////////////////////////////////////////////



    ////Image of heart and stack/////
    private ImageView heart1,heart2,heart3;
    private Deque<ImageView> stack = new ArrayDeque<ImageView>();
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////

    /////stack of spaceships and current one////////////////////////////////////////////////////////////////////
    private Deque<ImageView> stackOfSpaceShips = new ArrayDeque<ImageView>();
    private float spaceShipX,spaceShipY;
    private Drawable spaceShipImg;
    private Drawable spaceShipImg2;
    private Drawable spaceShipImg3;
    private Drawable spaceShipImg4;

    private ImageView spaceShipCurrent;

    //SpaceShip

    private ImageView spaceShip;
    private ImageView spaceShip2;
    private ImageView spaceShip3;
    private ImageView spaceShip4;
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////


    ////Number of hits and points////////////////////////////////////////////////////////////////////////////////////
    private int hits=0;
    private int counterOfPoints=0;

    //text Of points
    private TextView textPoint;
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    ///Effectes////////////////////////////////////////////////////////////////////////////////////////////////////
    ExplosionField explosionField;
    ExplosionField explosionField2;
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////


    ///////Time Counter to new spaceShip/////////
    private int timeCount=0;

    private boolean playNow=false;


    ///////




    /////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        playNow=true;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Rockets

        rocketUp=(ImageView)findViewById(R.id.rocket);
        rocketUp2=(ImageView)findViewById(R.id.rocket2);
//        rocketUp3=(ImageView)findViewById(R.id.rocket3);



        //SpaceShips/////////////////

        spaceShip=findViewById(R.id.spaceShip);
        frame=findViewById(R.id.frame);
        relLayout=findViewById(R.id.relLayout);
        findViewById(R.id.leftBtn).setOnTouchListener(this);
        findViewById(R.id.rightBtn).setOnTouchListener(this);
        spaceShipImg=getResources().getDrawable(R.drawable.spaceship);


       ////////////////////////////


        ////Heart///
        heart1=findViewById(R.id.heart1);
        heart2=findViewById(R.id.heart2);
        heart3=findViewById(R.id.heart3);
        textPoint=findViewById(R.id.pointText);
        stack.push(heart1);
        stack.push(heart2);
        stack.push(heart3);

        ////

        ///spaceships stack//////////////////////////////////////////////////////////

        spaceShip2=findViewById(R.id.spaceShip2);
        spaceShip3=findViewById(R.id.spaceShip3);
        spaceShip4=findViewById(R.id.spaceShip4);

        spaceShipImg2=getResources().getDrawable(R.drawable.spaceship);
        spaceShipImg3=getResources().getDrawable(R.drawable.spaceship);
        spaceShipImg4=getResources().getDrawable(R.drawable.spaceship);

        stackOfSpaceShips.push(spaceShip2);
        stackOfSpaceShips.push(spaceShip3);
        stackOfSpaceShips.push(spaceShip4);

        spaceShip4.setVisibility(View.GONE);
        spaceShip3.setVisibility(View.GONE);
        spaceShip2.setVisibility(View.GONE);

        spaceShipCurrent=spaceShip;
        spaceShipCurrent.setX(433);
        spaceShipCurrent.setY(0);




        //get the size of the screen.
        WindowManager wm=getWindowManager();
        Display ds=wm.getDefaultDisplay();
        Point size=new Point();
        ds.getSize(size);
        screenHeight=size.y;
        screenWidth=size.x;


        rocketUp.setX(-80.0f);
        rocketUp.setY(screenHeight+80.0f);

        rocketUp2.setX(-80.0f);
        rocketUp2.setY(screenHeight+80.0f);




    timer.schedule(new TimerTask() {
        @Override
        public void run() {
            if(playNow==true) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        changePos();
                    }
                });
            }
        }
    }, 0, 10);





    }


    @Override
    protected void onPause() {
        playNow=false;

        super.onPause();
    }

    @Override
    protected void onResume() {
        playNow=true;
        super.onResume();
    }

    public void changePos(){
        timeCount+=20;

        ///Spaceship////////Get the Location of the spaceship.////////////
        spaceShipX=spaceShipCurrent.getX();
        spaceShipY=spaceShipCurrent.getY();


        if(action_left)spaceShipX-=20;
        if (action_right)spaceShipX+=20;

        //Delimite Horizintal move.
        if(spaceShipX<0){
            spaceShipX=0;
            spaceShipCurrent.setImageDrawable(spaceShipImg);

        }

        if(spaceShipX>frame.getWidth()-spaceShip.getWidth()){
            spaceShipX=frame.getWidth()-spaceShip.getWidth();
            spaceShipCurrent.setImageDrawable(spaceShipImg);

        }



        /////Attack///in 3 ways On easy Game////

        //Way one
        if(spaceShipX<220 &&spaceShipX>-2){
            currentAttackLocation=100;
        }

       //way two
       if(spaceShipX<600 &&spaceShipX>110){
            currentAttackLocation=410;
      }

       //way three

       if(spaceShipX<900 &&spaceShipX>660){
           currentAttackLocation=770;
       }


        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        //////////////////////////////////////
        ///Rocket 1 attack the spaceShip////////

          moveRocket(rocketUp2,rocketup2Finishscreen);

       ///////////////////////////////////////////////////////////

        ///rocket 2 attack

         moveRocket(rocketUp,rocketupFinishscreen);

        /////
        spaceShipCurrent.setX(spaceShipX);
        spaceShipCurrent.setY(spaceShipY);

        ////
        //Down rocket
        float[] floatArray ;
        floatArray = new float[]{100,410, 770};



        //Position Rocket Number1


        positionY+=10;
        if(rocketUp.getY()>screenHeight){

            int indexPos=(int)Math.floor(Math.random()*3);
            positionX=floatArray[indexPos];
            positionY=-100.0f;
            if(rocketupFinishscreen){
                counterOfPoints+=1;
                textPoint.setText("Score:"+counterOfPoints);

            }

        }
        rocketUp.setX(positionX);
        rocketUp.setY(positionY);

        //Position rocket number2
        positionY2+=10;
        if(rocketUp2.getY()>screenHeight&&timeCount%900==0){
            Log.i("myTag", ""+rocketUp2.getY());

            int indexPos=(int)Math.floor(Math.random()*3);
            positionX2=floatArray[indexPos];
            positionY2=-100.0f;

            if(rocketup2Finishscreen){
                counterOfPoints+=1;
                textPoint.setText("Score:"+counterOfPoints);

            }

        }
        rocketUp2.setX(positionX2);
        rocketUp2.setY(positionY2);






    }

    public void createNewSpaceShipAfterExplosion(){
        new CountDownTimer(1050, 1000) {

            public void onTick(long millisUntilFinished) {


                relLayout.removeView(spaceShipCurrent);

                spaceShipCurrent.setImageResource(0);
            }

            public void onFinish() {

                spaceShipCurrent = stackOfSpaceShips.pop();


                spaceShipCurrent.setVisibility(View.VISIBLE);
                spaceShipCurrent.setX(433);
                spaceShipCurrent.setY(0);
            }
        }.start();
    }


    public void changePositionRocket(ImageView rocketCu,boolean RocketFinish,float positionXCu,float positionYcu){
        //Down rocket
        float[] floatArray ;
        floatArray = new float[]{100,410, 770};


        //Position Rocket Number1


        positionY+=10;
        if(rocketUp.getY()>screenHeight){

            int indexPos=(int)Math.floor(Math.random()*3);
            positionX=floatArray[indexPos];
            positionY=-100.0f;
            if(rocketupFinishscreen){
                counterOfPoints+=1;
                textPoint.setText("Score:"+counterOfPoints);

            }

        }
        rocketUp.setX(positionX);
        rocketUp.setY(positionY);
    }

    public void gameOver(){
        if(VERSION.SDK_INT >= 26) {
            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            // Vibrate for 500 milliseconds
            if (VERSION.SDK_INT >= VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(800, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                //deprecated in API 26
                v.vibrate(800);
            }
        }
        Intent lastIntent = new Intent(MainActivity.this, LastActivity.class);
        String points = Integer.toString(counterOfPoints);
        lastIntent.putExtra("points_i_need", points);
        timer.cancel();
        timer = null;
        startActivity(lastIntent);
    }




    public void moveRocket(ImageView rocketCu,boolean locate){
        if (rocketCu.getX() == currentAttackLocation) {
            if ((rocketCu.getY() == 1300)) {
                hits += 1;
                if (hits == 3) {
                    finish();
                gameOver();
                }
                ///////The Explosion// Of the Heart////////
                explosionField = ExplosionField.attach2Window(this);
                explosionField.explode(stack.pop());

if(VERSION.SDK_INT >= 26) {
    Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
// Vibrate for 500 milliseconds
    if (VERSION.SDK_INT >= VERSION_CODES.O) {
        v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
    } else {
        //deprecated in API 26
        v.vibrate(500);
    }
}
                ///////The Explosion// Of the Spaceship////////
                explosionField2 = ExplosionField.attach2Window(this);
                explosionField2.explode(spaceShipCurrent);

                spaceShipCurrent.setVisibility(View.GONE);

                createNewSpaceShipAfterExplosion();


                ////
                locate = false;
            }
            currentAttackLocation = 1000;

            locate = true;

        }


    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_DOWN){
            switch (v.getId()){
                case R.id.leftBtn:
                    action_left=true;
                    break;
                case R.id.rightBtn:
                    action_right=true;
                    break;

            }

        }
        else{
            action_right=false;
            action_left=false;
        }
        return true;
    }



}
