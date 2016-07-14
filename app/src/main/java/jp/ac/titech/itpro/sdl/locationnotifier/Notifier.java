package jp.ac.titech.itpro.sdl.locationnotifier;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

/**
 * Created by admin on 16/07/13.
 */
public class Notifier extends BroadcastReceiver {

    private LocationManager locationManager;
    private Location loc = null;

    public void onReceive(Context context, Intent intent) {

        Log.d("Notifier","recieve");

        locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);

        Log.d("Notifier",locationManager.toString());

        getLocation(context,this);

        int count = 0;
        while(loc == null) {
        }

        Uri.Builder builder = new Uri.Builder();
        AsyncHttpRequest task = new AsyncHttpRequest();
        task.setParams(loc.getLatitude(),loc.getLongitude(),(String) intent.getSerializableExtra("mail"));
        task.execute(builder);

        loc = null;

    }

    private void getLocation(final Context context, final Notifier not) {

        Log.d("getLocation","1");

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            return;
        }

        Log.d("getLocation","2");
        loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                1000, // 通知のための最小時間間隔（ミリ秒）
                0, // 通知のための最小距離間隔（メートル）
                new LocationListener() {

                    @Override
                    public void onLocationChanged(Location location) {

                        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            Log.d("LocationListener","permission denied");
                            return;
                        }
                        Log.d("LocationListener","listen");
                        loc = location;
                        locationManager.removeUpdates(this);
                    }

                    @Override
                    public void onProviderDisabled(String provider) {
                        Log.d("LocationListener","pdlisten");
                    }

                    @Override
                    public void onProviderEnabled(String provider) {
                        Log.d("LocationListener","pelisten");
                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {
                        Log.d("LocationListener","sclisten");
                    }
                }
        );
    }
}
