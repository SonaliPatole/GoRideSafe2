package com.example.lenovo.goridesafe;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.common.api.GoogleApiClient;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class NearbyMapActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {
    private GoogleMap mMap;
    private LocationRequest locationRequest;
    private GoogleApiClient client;
    private  Location lastLocation;
    int PROXIMITY_RADIUS=1000;
    double latitude,longitude;
    private Marker currentLocationMarker;
    public static final int REQUEST_LOCATION_CODE=99;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_map);
        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.M)
        {
            checkLocationPermission();
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
    public void onClick(View v)
    {
        Object dataTransfer[]=new Object[2];
        String url=null;
        GetNearbyPlacesData getNearbyPlacesData=new GetNearbyPlacesData();

        switch (v.getId()) {
            case R.id.bSearch: {
                EditText search = (EditText) findViewById(R.id.txtSerach);
                String location = search.getText().toString();
                List<Address> addressList = null;
                MarkerOptions mo = new MarkerOptions();

                if (!location.equals("")) {
                    Geocoder geocoder = new Geocoder(this);
                    try {
                        addressList = geocoder.getFromLocationName(location, 5);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    for (int i = 0; i < addressList.size(); i++) {
                        Address myAdress = addressList.get(i);
                        LatLng latLng = new LatLng(myAdress.getLatitude(), myAdress.getLongitude());
                        mo.position(latLng);
                        mo.title("Your search result");
                        mMap.addMarker(mo);
                        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                    }
                }
            }
            break;
            case R.id.bhospital:
                mMap.clear();
                String hospital="Hospital";
                url=getUrl(latitude,longitude,hospital);

                dataTransfer[0]=mMap;
                dataTransfer[1]=url;
                getNearbyPlacesData.execute(dataTransfer);
                Toast.makeText(NearbyMapActivity.this,"Showing nearby hospitals",Toast.LENGTH_LONG).show();
                break;
            case R.id.bRestaurants:
                mMap.clear();
                String restarunt="Restaurant";
                url=getUrl(latitude,longitude,restarunt);

                dataTransfer[0]=mMap;
                dataTransfer[1]=url;
                getNearbyPlacesData.execute(dataTransfer);
                Toast.makeText(NearbyMapActivity.this,"Showing nearby restaurants",Toast.LENGTH_LONG).show();
                break;
            case R.id.bSchools:
                mMap.clear();
                String school="School";
                url=getUrl(latitude,longitude,school);

                dataTransfer[0]=mMap;
                dataTransfer[1]=url;
                getNearbyPlacesData.execute(dataTransfer);
                Toast.makeText(NearbyMapActivity.this,"Showing nearby schools",Toast.LENGTH_LONG).show();
                break;

        }
    }

     private String getUrl(Double latitude,Double longitude,String nearbyPlace)
     {
         StringBuilder googlePlaceUrl=new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
         googlePlaceUrl.append("location"+latitude+","+longitude);
         googlePlaceUrl.append("&radius="+PROXIMITY_RADIUS);
         googlePlaceUrl.append("&type="+nearbyPlace);
         googlePlaceUrl.append("&sensor=true");
         googlePlaceUrl.append("&key=AIzaSyCKnh_GofTvGSUoxqwg_-5AcaLCkdkaT-0");
         return googlePlaceUrl.toString();
     }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode)
        {
            case REQUEST_LOCATION_CODE:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {
                    //permission granted
                    if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED)
                    {
                        if(client==null)
                        {
                            builtGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                }
                else
                {
                    Toast.makeText(this,"permission denied",Toast.LENGTH_LONG).show();
                }
                return;
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED) {
            builtGoogleApiClient();
            mMap.setMyLocationEnabled(true);

        }


    }
    public boolean checkLocationPermission()
    {
        if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,android.Manifest.permission.ACCESS_FINE_LOCATION))
            {
                ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_LOCATION_CODE);
            }
            else
            {
                ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_LOCATION_CODE);
            }
            return false;
        }
        else
        {
            return true;
        }
    }
    protected synchronized void builtGoogleApiClient()
    {
        client=new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        client.connect();
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationRequest=new LocationRequest();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
        LocationServices.FusedLocationApi.requestLocationUpdates(client,locationRequest,this);}
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

        lastLocation=location;
        if(currentLocationMarker!=null)
        {
            currentLocationMarker.remove();

        }
        latitude=location.getLatitude();
        longitude=location.getLongitude();
        LatLng latLng=new LatLng(location.getLatitude(),location.getLongitude());
        MarkerOptions markerOptions=new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Location");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        currentLocationMarker =mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomBy(10));
        if(client!=null)
        {
            LocationServices.FusedLocationApi.removeLocationUpdates(client,this);
        }
    }
}
