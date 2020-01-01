package com.example.game2;




import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class fragment_bottom extends Fragment implements OnMapReadyCallback {
    View v;
    private GoogleMap map;
    MapView mapView;


    private static final String TAG = "MapsActivity";
    private GoogleMap mMap;
    private GoogleApiClient googleApiClient;
    private Location userLocation;
    private static double latitude;
    private static double longitude;
    private String titleMarker = "title";
    private Marker marker;
    private Marker number1marker;
    private Marker number2marker;
    private Marker number3marker;
    private LatLng mylocation;
    private LatLng userNumber1Location;
    private LatLng userNumber2Location;
    private LatLng userNumber3Location;


    LocationManager locationManager;
    LocationListener locationListener;


    public void setTitleMarker(String titleMarker) {
        this.titleMarker = titleMarker;
    }

    public String getTitleMarker() {
        return titleMarker;
    }

    private fragmentBottomListener listener;

    private UserRecord currentUserChoice;

    public interface fragmentBottomListener {
        void onInputUser(UserRecord userRecord);
    }


    public static void setLongitude(double longitude) {
        fragment_bottom.longitude = longitude;
    }

    public static double getLongitude() {
        return longitude;
    }

    public static double getLatitude() {
        return latitude;
    }

    public static void setLatitude(double latitude) {
        fragment_bottom.latitude = latitude;
    }

    //Current location
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 99;


    ///
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        v = inflater.inflate(R.layout.fragment_bottom, container, false);

        return v;


    }

    public void updateCurrentUser(UserRecord userRecord) {
        currentUserChoice = userRecord;
        setLatitude(currentUserChoice.getLatitude());
        setLongitude(currentUserChoice.getLongtitude());
        setTitleMarker(currentUserChoice.getText());
        double mLat = getLatitude();
        double mLon = getLongitude();
        LatLng userRecordLoc = new LatLng(mLat, mLon);

        Log.i("user","name"+userRecord.getText());

        if(userRecord.getText()==("First place")){
            Log.i("you","press first"+"");
            //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userNumber1Location, 15));
            ///
            //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userNumber1Location,16));

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(number1marker.getPosition(), 15));
                }
            }, 300);


            ///

        }

        if(userRecord.getText()==("Second place")){
            Log.i("you","press sec"+"");
           // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userNumber2Location, 15));
           // mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userNumber2Location,16));


        }

        if(userRecord.getText()==("third place")){
            Log.i("you","press third"+"");
           // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userNumber3Location, 15));
            //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userNumber3Location,16));




        }





    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof fragmentBottomListener) {
            listener = (fragmentBottomListener) context;
        } else {
            throw new RuntimeException(context.toString() + "Must implements fragmentBottomListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapView = view.findViewById(R.id.mapView);

        if (mapView != null) {
            mapView.onCreate(null);
            mapView.onResume();

            mapView.getMapAsync(this);
        }


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {



        Handler handler=new Handler();
        Runnable r=new Runnable() {
            public void run() {

                mMap = googleMap;
                double mLat = getLatitude();
                double mLon = getLongitude();
                mylocation = new LatLng(mLat, mLon);

                userNumber1Location=new LatLng(fragment_top.usernumber1.getLatitude()+0.00000003,fragment_top.usernumber2.getLongtitude());
                userNumber2Location=new LatLng(fragment_top.usernumber2.getLatitude()+0.000005,fragment_top.usernumber2.getLongtitude());
                userNumber3Location=new LatLng(fragment_top.usernumber3.getLatitude()+0.0000067,fragment_top.usernumber3.getLongtitude());


                marker = mMap.addMarker(new MarkerOptions().position(mylocation));
                number1marker = mMap.addMarker(new MarkerOptions().position(userNumber1Location).title(fragment_top.usernumber1.getText()));
                number2marker = mMap.addMarker(new MarkerOptions().position(userNumber2Location).title(fragment_top.usernumber2.getText()));
                number3marker = mMap.addMarker(new MarkerOptions().position(userNumber3Location).title(fragment_top.usernumber3.getText()));

                marker.showInfoWindow();
                number1marker.showInfoWindow();
                number2marker.showInfoWindow();
                number3marker.showInfoWindow();


                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mylocation, 15));
            }
        };
        handler.postDelayed(r, 1000);


    }
}