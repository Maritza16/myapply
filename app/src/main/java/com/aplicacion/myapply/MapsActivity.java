package com.aplicacion.myapply;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    String  nombre,latitude,longitude;
    private GoogleMap mMap;

    private static Button btndriving;
    TextView lat,lgt;
    private LocationManager locationManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }




    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        longitude=(getIntent().getExtras().getString("longitud"));
        latitude=getIntent().getExtras().getString("latitud");
        nombre=getIntent().getExtras().getString("name");
        double loclat=Double.parseDouble(latitude);
        double loclong=Double.parseDouble(longitude);
        LatLng points=new LatLng(loclat,loclong);
        Log.i("nombre",latitude);
        lat=findViewById(R.id.latitud);
        lat.setText(latitude);
        lgt=findViewById(R.id.longitud);
        lgt.setText(longitude);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }





        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(points);
        mMap.addMarker(markerOptions).setTitle(nombre);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(points));
        mMap.moveCamera(CameraUpdateFactory.zoomIn());

        btndriving=findViewById(R.id.driving);

        btndriving.setOnClickListener((v)->{
            Uri uri=Uri.parse("geo:<" + latitude+ ">,<" + longitude+ ">?q=<" +latitude+ ">,<" + longitude+ ">(" + nombre + ")");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        });
    }


}