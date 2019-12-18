package com.example.game2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.net.Uri;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Timer;
import java.util.TimerTask;

import tyrantgit.explosionfield.ExplosionField;

import static android.os.Build.*;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener, SensorEventListener {


    //activity
    private MainActivity intentMain;

    //Video
    private VideoView videoView;
    ///

    ////Sensor mode
    private SensorManager sensorManager;
    Sensor accelo;

    ///Relative layout////
    private RelativeLayout all_screen_layout;
    ////
    private float gridHeight;

    //Wich mode you choose 0=Easy 1=hard 2=sensor
    private int kindOfBTN;

    //screen size
    private int screenWidth;
    private int screenHeight;

    //Rocket 1
    private ImageView rocketUp;
    private boolean rocketupFinishscreen = true;

    //Rocket 2
    private ImageView rocketUp2;
    private boolean rocketup2Finishscreen = true;

    //Rocket 3
    private ImageView rocketUp3;
    private boolean rocketup3Finishscreen = true;

    //Rocket TEMP
    private ImageView rocketemp;


    //Coin
    private ImageView coin;
    private boolean coinFinishscreen = true;

    //Bullet
    private ImageView bullet;
    private boolean bulletFinishscreen = true;
    private boolean bullet_start_move=false;
    private boolean bulletAttackTherocket=false;


    //Buttons left and right
    private ImageButton leftBtn;
    private ImageButton rightBtn;

    //Main timer
    private Handler handler = new Handler();
    private Timer timer = new Timer();

    //Bullet timer

    private Handler bullet_handler = new Handler();
    private Timer bullet_timer = new Timer();

    //Layouts/////
    private FrameLayout frame;
    private RelativeLayout relLayout;

    //Movements////////////////

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


    //Position coin

    private float coinX;
    private float coinY;


    //Position bullet

    private float bulletX;
    private float bulletY;


    ///Speed on y of the objects////
    private float speedrocket1=10;
    private float speedrocket2=10;
    private float speedrocket3=10;
    private float speedcoin=10;

    ///

    private boolean action_left, action_right;

///////////////////////////////////////////////////////////////////////////////////////////////////////////////


    ////Image of heart and stack/////
    private ImageView heart1, heart2, heart3;
    private Deque<ImageView> stack = new ArrayDeque<ImageView>();
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////

    /////stack of spaceships and current one////////////////////////////////////////////////////////////////////
    private Deque<ImageView> stackOfSpaceShips = new ArrayDeque<ImageView>();
    private float spaceShipX, spaceShipY;
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
    private int hits = 0;
    private int counterOfPoints = 0;

    //text Of points
    private TextView textPoint;
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    ///Effectes////////////////////////////////////////////////////////////////////////////////////////////////////
    ExplosionField explosionField;
    ExplosionField explosionField2;
    ExplosionField explosionField3;

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////


    ///////Time Counter to new spaceShip/////////
    private int timeCount = 0;

    private boolean playNow = false;

    ///////

    private float way1;
    private float way2;
    private float way3;
    private float way4;
    private float way5;

    private float[] floatArray;


    private float spaceShipCurrentHeight;

    ///To increase the speed
    int dynamicSpeed = 10;

    ///



    /////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        playNow = true;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        accelo = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(MainActivity.this, accelo, SensorManager.SENSOR_DELAY_FASTEST);


        //Get the kind of mode (easy ,hard,sensor)////
        Intent thisintent = getIntent();
        kindOfBTN = thisintent.getExtras().getInt("kind_of_btn");


        /////


        ///Video On BackGround

        videoView = findViewById(R.id.mVideoView);
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.falling_stars3);
        videoView.setVideoURI(uri);

        videoView.start();


        /////

        //Rockets

        rocketUp = (ImageView) findViewById(R.id.rocket);
        rocketUp2 = (ImageView) findViewById(R.id.rocket2);
        rocketUp3 = (ImageView) findViewById(R.id.rocket3);
        coin = findViewById(R.id.coin);
        bullet=findViewById(R.id.bulllet);
        rocketUp3.setVisibility(View.GONE);


        //SpaceShips/////////////////

        spaceShip = findViewById(R.id.spaceShip);
        frame = findViewById(R.id.frame);
        relLayout = findViewById(R.id.relLayout);
        findViewById(R.id.leftBtn).setOnTouchListener(this);
        findViewById(R.id.rightBtn).setOnTouchListener(this);
        spaceShipImg = getResources().getDrawable(R.drawable.spaceshipimproved);

        spaceShipCurrentHeight = spaceShip.getHeight();

        all_screen_layout = findViewById(R.id.all_screen_layout);
        gridHeight = all_screen_layout.getHeight();


        ////////////////////////////

        //////Buttons recognize////////

        leftBtn = findViewById(R.id.leftBtn);
        rightBtn = findViewById(R.id.rightBtn);

        ////////


        ////Heart///
        heart1 = findViewById(R.id.heart1);
        heart2 = findViewById(R.id.heart2);
        heart3 = findViewById(R.id.heart3);
        textPoint = findViewById(R.id.pointText);
        stack.push(heart1);
        stack.push(heart2);
        stack.push(heart3);

        ////

        ///spaceships stack//////////////////////////////////////////////////////////

        spaceShip2 = findViewById(R.id.spaceShip2);
        spaceShip3 = findViewById(R.id.spaceShip3);
        spaceShip4 = findViewById(R.id.spaceShip4);

        spaceShipImg2 = getResources().getDrawable(R.drawable.spaceshipimproved);
        spaceShipImg3 = getResources().getDrawable(R.drawable.spaceshipimproved);
        spaceShipImg4 = getResources().getDrawable(R.drawable.spaceshipimproved);

        stackOfSpaceShips.push(spaceShip2);
        stackOfSpaceShips.push(spaceShip3);
        stackOfSpaceShips.push(spaceShip4);

        spaceShip4.setVisibility(View.GONE);
        spaceShip3.setVisibility(View.GONE);
        spaceShip2.setVisibility(View.GONE);

        spaceShipCurrent = spaceShip;
        spaceShipCurrent.setX(433);
        spaceShipCurrent.setY(0);


        //get the size of the screen.
        WindowManager wm = getWindowManager();
        Display ds = wm.getDefaultDisplay();
        Point size = new Point();
        ds.getSize(size);
        screenHeight = size.y;
        screenWidth = size.x;

        //Set positions for the beginning
        rocketUp.setX(-80.0f);
        rocketUp.setY(screenHeight + 80.0f);

        rocketUp2.setX(-80.0f);
        rocketUp2.setY(screenHeight + 80.0f);

        rocketUp3.setX(-80.0f);
        rocketUp3.setY(screenHeight + 80.0f);

        coin.setX(-80.0f);
        coin.setY(screenHeight + 80.0f);

        bullet.setX(-80.0f);
        bullet.setY(-80.0f);
       bullet.setVisibility(View.GONE);


        timer.schedule(timerTask, 0, dynamicSpeed);
    }

    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            if (playNow == true) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        changePos();
                    }
                });
            }
        }
    };

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        if (kindOfBTN == 2) {
            rightBtn.setVisibility(View.GONE);
            leftBtn.setVisibility(View.GONE);
            if (event.values[0] > 0.5) {
                action_left = true;


            } else if (event.values[0] < -0.5) {
                action_right = true;


            } else if (-0.5 < event.values[0] || event.values[0] < 0.5) {
                action_right = false;
                action_left = false;

            }

            if (event.values[1] < 7 && event.values[1] > 5.5) {
                speedcoin=4;
                speedrocket1=4;
                speedrocket2=4;
                speedrocket3=4;


            } else if (event.values[1] < 5.5 && event.values[1] > 4) {
                speedcoin=6;
                speedrocket1=6;
                speedrocket2=6;
                speedrocket3=6;



            } else if (event.values[1] < 4 && event.values[1] > 2) {

                speedcoin=8;
                speedrocket1=8;
                speedrocket2=8;
                speedrocket3=8;

            } else if (event.values[1]>0&&event.values[1] < 2) {
                speedcoin=10;
                speedrocket1=10;
                speedrocket2=10;
                speedrocket3=10;



            }

            else if (event.values[1]>-2&&event.values[1] < 0) {
                speedcoin=14;
                speedrocket1=14;
                speedrocket2=14;
                speedrocket3=14;



            }



            else if(event.values[1] >7){
                speedcoin=2;
                speedrocket1=2;
                speedrocket2=2;
                speedrocket3=2;

            }


        } else {
            rightBtn.setVisibility(View.VISIBLE);
            leftBtn.setVisibility(View.VISIBLE);
        }


    }


    @Override
    protected void onPause() {
        playNow = false;

        super.onPause();
    }

    @Override
    protected void onResume() {
        playNow = true;
        super.onResume();
    }

    public void changePos() {
        timeCount += 20;




        ///Spaceship////////Get the Location of the spaceship.////////////
        spaceShipX = spaceShipCurrent.getX();
        spaceShipY = spaceShipCurrent.getY();


        if (action_left) spaceShipX -= 20;
        if (action_right) spaceShipX += 20;

        //Delimite Horizintal move.
        if (spaceShipX < 0) {
            spaceShipX = 0;
            spaceShipCurrent.setImageDrawable(spaceShipImg);

        }

        if (spaceShipX > frame.getWidth() - spaceShip.getWidth()) {
            spaceShipX = frame.getWidth() - spaceShip.getWidth();
            spaceShipCurrent.setImageDrawable(spaceShipImg);

        }




        //////////////////////////////////////
        ///Rocket 1 attack the spaceShip////////

        moveRocket(rocketUp2, rocketup2Finishscreen);

        ///////////////////////////////////////////////////////////

        ///rocket 2 attack

        moveRocket(rocketUp, rocketupFinishscreen);

        /////

        ///rocket 3 attack
        moveRocket(rocketUp3, rocketup3Finishscreen);


        //// get the coin
        moveCoin(coin, coinFinishscreen);
        ///

        spaceShipCurrent.setX(spaceShipX);
        spaceShipCurrent.setY(spaceShipY);

        ////
        //Down rocket


        Log.i("myTag", "" + all_screen_layout.getWidth());
        Log.i("shlish", "" + all_screen_layout.getWidth() / 3);
        Log.i("way1", "" + way1);
        Log.i("way2", "" + way2);
        Log.i("way3", "" + way3);

        if (kindOfBTN == 0) {

            //shlish
            float shlish = all_screen_layout.getWidth() / 3;

            //way one
            way1 = (shlish / 2) - 65;

            //way two
            way2 = way1 + shlish;

            //way three
            way3 = way2 + shlish;
            floatArray = new float[]{way1, way2, way3};

        } else if (kindOfBTN == 1 || kindOfBTN == 2) {

            //shlish
            float hamitishit = all_screen_layout.getWidth() / 5;

            //way one
            way1 = (hamitishit / 2) - 80;

            //way two
            way2 = way1 + hamitishit;

            //way three
            way3 = way2 + hamitishit;

            //way four
            way4 = way3 + hamitishit;

            //way five
            way5 = way4 + hamitishit;

            floatArray = new float[]{way1, way2, way3, way4, way5};


            Log.i("way1", "" + way1);
            Log.i("way2", "" + way2);
            Log.i("way3", "" + way3);
            Log.i("way1", "" + way4);
            Log.i("way2", "" + way5);

        }

        //Position Rocket Number1

        int indexPos;
        positionY += speedrocket1;
        if (rocketUp.getY() > screenHeight) {

            if (kindOfBTN == 0) {
                indexPos = (int) Math.floor(Math.random() * 3);
            } else {
                indexPos = (int) Math.floor(Math.random() * 5);

            }

            positionX = floatArray[indexPos];
            positionY = -100.0f;
            if (rocketupFinishscreen) {
                counterOfPoints += 1;
                textPoint.setText("Score:" + counterOfPoints);

            }

        }
        rocketUp.setX(positionX);
        rocketUp.setY(positionY);

        //Position rocket number2
        positionY2 += speedrocket2;
        if (rocketUp2.getY() > screenHeight && timeCount % 900 == 0) {

            if (kindOfBTN == 0) {
                indexPos = (int) Math.floor(Math.random() * 3);
            } else {
                indexPos = (int) Math.floor(Math.random() * 5);

            }
            positionX2 = floatArray[indexPos];
            positionY2 = -100.0f;

            if (rocketup2Finishscreen) {
                counterOfPoints += 1;
                textPoint.setText("Score:" + counterOfPoints);

            }

        }
        rocketUp2.setX(positionX2);
        rocketUp2.setY(positionY2);


        //Bullet ///
        if(bullet_start_move=true) {
            bulletY -= 5;
            if (bullet.getY() + bullet.getHeight() < 0||bulletAttackTherocket&&bullet.getVisibility()==View.VISIBLE) {

                bulletAttackTherocket=false;
                bullet_start_move=false;
                rocketUp.setVisibility(View.VISIBLE);
                rocketUp2.setVisibility(View.VISIBLE);
                bullet.setVisibility(View.GONE);
                bulletX = spaceShipX+spaceShipCurrent.getWidth()/2;
               //bulletY=spaceShipCurrent.getY()+spaceShipCurrent.getHeight();
                 bulletY = screenHeight + 100.0f;

            }


            bullet.setX(bulletX);
            bullet.setY(bulletY);
        }

        ///////


        //Position coin
        coinY += speedcoin;
        if (coin.getY() > screenHeight) {
            coin.setVisibility(View.VISIBLE);
            if (kindOfBTN == 0) {
                indexPos = (int) Math.floor(Math.random() * 3);
            } else {
                indexPos = (int) Math.floor(Math.random() * 5);

            }
            coinX = floatArray[indexPos];
            coinY = -100.0f;

            if (coinFinishscreen) {
                counterOfPoints += 0;
                textPoint.setText("Score:" + counterOfPoints);

            }

        }
        coin.setX(coinX);
        coin.setY(coinY);

//rocket number 3

        if (kindOfBTN == 1) {
            rocketUp3.setVisibility(View.VISIBLE);
            positionY3 += speedrocket3;
            if (rocketUp3.getY() > screenHeight) {

                if (kindOfBTN == 0) {
                    indexPos = (int) Math.floor(Math.random() * 3);
                } else {
                    indexPos = (int) Math.floor(Math.random() * 5);

                }
                positionX3 = floatArray[indexPos];
                positionY3 = -100.0f;
                if (rocketup3Finishscreen) {
                    counterOfPoints += 1;
                    textPoint.setText("Score:" + counterOfPoints);

                }

            }
            rocketUp3.setX(positionX3);
            rocketUp3.setY(positionY3);
        }


    }

    public void createNewSpaceShipAfterExplosion() {
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


    public void gameOver() {
        if (VERSION.SDK_INT >= 26) {
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

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("current_score", counterOfPoints);
        editor.commit();
        timer.cancel();
        timer = null;
        startActivity(lastIntent);
    }


    public void checkIfBulletAttacktheRocket(final ImageView rocketCu ){

        float centerOfBullet1y = bullet.getY() + bullet.getHeight() / 2;
        float centerOfBullet1x = bullet.getX() + bullet.getWidth() / 2;

        if (rocketCu.getX() <= centerOfBullet1x && centerOfBullet1x <= rocketCu.getX() + rocketCu.getWidth()) {
            if (rocketCu.getY() <= centerOfBullet1y && centerOfBullet1y <= rocketCu.getY() + 5) {
                if(bullet.getVisibility()==View.VISIBLE) {

                    //if bullet attack the rocket
                    //explode


                    rocketCu.setVisibility(View.GONE);


                    //hide this rocket after explosion
                    //hide bullet
                    bullet.setVisibility(View.GONE);


                    //and make new one.
                    bulletAttackTherocket = true;
                }

            }
            }


    }


    public void moveRocket(ImageView rocketCu, boolean locate) {
        float centerOfRocket1y = rocketCu.getY() + rocketCu.getHeight() / 2;
        float centerOfRocket1x = rocketCu.getX() + rocketCu.getWidth() / 2;

        float centerOfspaceShip = spaceShipCurrent.getY() + spaceShipCurrent.getHeight() / 2;

        checkIfBulletAttacktheRocket(rocketCu);


        if (spaceShipCurrent.getX() <= centerOfRocket1x && centerOfRocket1x <= spaceShipCurrent.getX() + spaceShipCurrent.getWidth()) {
            if (spaceShipCurrent.getY() <= centerOfRocket1y && centerOfRocket1y <= spaceShipCurrent.getY() + 5&&rocketCu.getVisibility()==View.VISIBLE) {


                Log.i("GOVA SHEL HA HALALIT", "" + centerOfRocket1y);
                Log.i("GOVA SHEL HA HALALIT", "" + spaceShipCurrent.getY());
                Log.i("GOVA SHEL HA HALALIT", "" + all_screen_layout.getHeight());


                hits += 1;
                if (hits == 3) {
                    finish();
                    gameOver();
                }


                ///////The Explosion// Of the Heart////////
                explosionField = ExplosionField.attach2Window(this);
                explosionField.explode(stack.pop());
                MediaPlayer ring= MediaPlayer.create(MainActivity.this,R.raw.bomb_sound);
                ring.start();


                if (VERSION.SDK_INT >= 26) {
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


                /////////////////////////////////////////////////////////////////////////////////////////
                locate = false;
            }


            locate = true;

        }




    }




    public void ScreenTaped(View view){
        bullet.setVisibility(View.VISIBLE);
        bulletX = spaceShipX+spaceShipCurrent.getWidth()/2;
        bullet_start_move=true;
        MediaPlayer ring= MediaPlayer.create(MainActivity.this,R.raw.sf_laser);
        ring.start();
    }





    public void moveCoin(ImageView coin, boolean locate) {
        float centerOfcoin1y = coin.getY() + coin.getHeight() / 2;
        float centerOfcoin1x = coin.getX() + coin.getWidth() / 2;

        if (spaceShipCurrent.getX() <= centerOfcoin1x && centerOfcoin1x <= spaceShipCurrent.getX() + spaceShipCurrent.getWidth()) {
            if (spaceShipCurrent.getY() <= centerOfcoin1y && centerOfcoin1y <= spaceShipCurrent.getY() + 5) {

                //after take coins////////////////////////////////////////////////////
                counterOfPoints += 20;
                textPoint.setText("Score:" + counterOfPoints);
                coin.setVisibility(View.GONE);

                ///////////////////////////////////////////////////////////////////////////////
                if (VERSION.SDK_INT >= 26) {
                    Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    // Vibrate for 500 milliseconds
                    if (VERSION.SDK_INT >= VERSION_CODES.O) {
                        v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                    } else {
                        //deprecated in API 26
                        v.vibrate(500);
                    }
                }
                ///////The sound of coin////////

                MediaPlayer ring= MediaPlayer.create(MainActivity.this,R.raw.mvm_money_pickup);
                ring.start();


                ////
                locate = false;
            }

            locate = true;

        }


    }


    @Override
    public void onBackPressed() {

        Intent mainIntent = new Intent(MainActivity.this, screenStart.class);
        startActivity(mainIntent);
        finish();


    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            switch (v.getId()) {
                case R.id.leftBtn:
                    action_left = true;
                    break;
                case R.id.rightBtn:
                    action_right = true;
                    break;

            }

        } else {
            action_right = false;
            action_left = false;
        }
        return true;
    }


}


