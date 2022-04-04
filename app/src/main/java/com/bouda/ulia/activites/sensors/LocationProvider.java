package com.bouda.ulia.activites.sensors;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

/**
 * Created by benjakuben on 12/17/14.
 */

@SuppressLint("MissingPermission")
public class LocationProvider implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    public interface LocationCallback {
        void onNewLocation(Location location);
    }
    public final String TAG = this.getClass().getSimpleName();
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;


    private final LocationCallback locationCallback;
    private final Context context;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;

    private boolean checkLocationPermissions(){
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.w(TAG, "Missing location permissions");
            return false;
        }

        return true;
    }

    private void configureGoogleApiClient(){
        googleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    private void configureLocationRequest(){
        locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)
                .setFastestInterval(1 * 1000);
    }

    public LocationProvider(Context context, LocationCallback callback) {
        this.context = context;
        this.locationCallback = callback;

        configureGoogleApiClient();
        configureLocationRequest();
    }

    public void connect() {
        googleApiClient.connect();
    }

    public void disconnect() {
        if (googleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
            googleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        locationCallback.onNewLocation(
                LocationServices.FusedLocationApi.getLastLocation(googleApiClient)
        );
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (connectionResult.hasResolution() && context instanceof Activity) {
            try {
                Activity activity = (Activity) context;
                connectionResult.startResolutionForResult(activity, CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        } else {
            Log.w(TAG, "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        locationCallback.onNewLocation(location);
    }

    public LocationRequest getLocationRequest() {
        return locationRequest;
    }
}