package com.example.game2;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import com.google.android.gms.location.FusedLocationProviderClient;
public class LastActivity extends AppCompatActivity implements fragment_top.fragmentTopListener,fragment_bottom.fragmentBottomListener{



    ///Fragments

    public static fragment_top fragmentTop;
    public static fragment_bottom fragmentBottom;

    ////
    public Location userLocation;
    private FusedLocationProviderClient client;






    @Override
    public void onBackPressed() {

        Intent mainIntent = new Intent(LastActivity.this, screenStart.class);
        startActivity(mainIntent);
        finish();

    }
//




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last);

            //////Fragments/////

        fragmentTop=new fragment_top();
        fragmentBottom=new fragment_bottom();
        ////


        getSupportFragmentManager().beginTransaction().replace(R.id.top_frag,fragmentTop).replace(R.id.bottom_frag,fragmentBottom).commit();


    }


    @Override
    public void onInputUser(UserRecord userRecord) {
        fragmentBottom.updateCurrentUser(userRecord);
    }



}
