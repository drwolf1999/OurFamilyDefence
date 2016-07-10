package com.example.kimdoyeop.ourfamilydefence.NoSave;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

/**
 * Created by KIMDOYEOP on 2016-07-09.
 */
public class CalculateLocation implements LocationListener {

    public CalculateLocation(Context context, Double latitude, Double longitude) {
    }

    private void CalculateLocation(Context context, Double lat, Double lon) {
        boolean isGPSEnabled = false;


        Location location = null;
        LocationManager manager = (LocationManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        isGPSEnabled = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (isGPSEnabled) {

            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, (long) 1000 * 60, (long) 10, this);
            if (manager != null) {
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
                location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }
        }

        Location target_Location = new Location("Target Location");
        target_Location.setLatitude(lat);
        target_Location.setLongitude(lon);

        double distance = location.distanceTo(target_Location);

        if (distance > 300) {
            //알림

        }
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
