package code4share.forecastweatherapp.manager;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;

import code4share.forecastweatherapp.utils.Constants;

/**
 * Created by keyur.
 */

public class LocationManager implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private Context mContext;
    private LocationListener mLocationListener;

    public LocationManager (Context context, LocationListener locationListener) {
        mContext = context;
        mLocationListener = locationListener;
    }

    public void requestLocationSetting (ResultCallback<LocationSettingsResult> resultResultCallback) {
        if (!isGpsAvailable ()) {
            checkGoogleApiClient ();

            LocationServices.SettingsApi
                    .checkLocationSettings (mGoogleApiClient, getLocationSettingsRequest ())
                    .setResultCallback (resultResultCallback);
        }
    }

    private boolean isGpsAvailable () {
        return mGoogleApiClient != null && hasLocationProviders ();
    }

    public void disconnect () {
        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect ();
        }
    }

    public void connectLocation () {
        if (mGoogleApiClient != null && !mGoogleApiClient.isConnected ()) {
            mGoogleApiClient.connect ();
        }
    }

    private void checkGoogleApiClient () {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder (mContext)
                    .addApi (LocationServices.API)
                    .addConnectionCallbacks (this)
                    .addOnConnectionFailedListener (this)
                    .build ();
        }
        connectLocation ();

    }

    private LocationSettingsRequest getLocationSettingsRequest () {
        return new LocationSettingsRequest.Builder ()
                .addLocationRequest (getLocationRequest ())
                .setAlwaysShow (true)
                .build ();
    }

    private boolean hasLocationProviders () {
        android.location.LocationManager locationManager = (android.location.LocationManager) mContext.getSystemService (Context.LOCATION_SERVICE);
        boolean gpsEnabled;
        boolean networkEnabled;

        try {
            gpsEnabled = locationManager.isProviderEnabled (Constants.PROVIDER_GPS);
        } catch (Exception e) {
            gpsEnabled = false;
        }


        try {
            networkEnabled = locationManager.isProviderEnabled (Constants.PROVIDER_NETWORK);
        } catch (Exception e) {
            networkEnabled = false;
        }

        return gpsEnabled || networkEnabled;
    }

    private LocationRequest getLocationRequest () {
        if (mLocationRequest == null) {
            mLocationRequest = LocationRequest.create ();
            mLocationRequest.setPriority (LocationRequest.PRIORITY_HIGH_ACCURACY);
            mLocationRequest.setInterval (Constants.LOCATION_UPDATE_INTERVAL_MILLISECONDS);
            mLocationRequest.setFastestInterval (Constants.LOCATION_MAX_UPDATE_INTERVAL_MILLISECONDS);
        }

        return mLocationRequest;
    }

    public void onConnected (@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission (mContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission (mContext, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates (
                    mGoogleApiClient, getLocationRequest (), mLocationListener);
        }

    }

    @Override
    public void onConnectionSuspended (int i) {
    }

    @Override
    public void onConnectionFailed (@NonNull ConnectionResult connectionResult) {
    }
}

