package jp.ac.titech.itpro.sdl.locationnotifier;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

/**
 * Created by admin on 16/07/13.
 */
public class Notifier extends BroadcastReceiver implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{
    private final static String[] PERMISSIONS = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    private final static int REQCODE_PERMISSIONS = 1111;
    private GoogleApiClient googleApiClient;

    private Location loc;

    private Context context;

    public void onReceive (Context context, Intent intent) {

        this.context = context;

        googleApiClient = new GoogleApiClient.Builder(context).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API)
                .build();

        googleApiClient.connect();

        Uri.Builder builder = new Uri.Builder();
        AsyncHttpRequest task = new AsyncHttpRequest();
        task.setParams(1,1,(String)intent.getSerializableExtra("mail"));
        task.execute(builder);
        Log.d("Notifier",(String)intent.getSerializableExtra("mail"));

    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.d("notifier", "onConnected");
        showLastLocation(true);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    private void showLastLocation(boolean reqPermission) {
        for (String permission : PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                if (reqPermission)
                    ActivityCompat.requestPermissions((MainActivity)context, PERMISSIONS,REQCODE_PERMISSIONS);
                return;
                }
            }
        Location loc = LocationServices.FusedLocationApi .getLastLocation(googleApiClient);
        if (loc != null) displayLocation(loc);
    }

    private void displayLocation(Location loc) {
        this.loc = loc;
        Log.d("location",loc.getLatitude() + " : " + loc.getLongitude());
        googleApiClient.disconnect();
    }


}
