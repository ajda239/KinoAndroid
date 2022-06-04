package si.uni_lj.fe.tnuv.vaja6;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class Maps extends AppCompatActivity implements OnMapReadyCallback {

    // TODO shranjevat vsa besedila in podatke v te reference, kar smo delal na vajah?
    private GoogleMap mMap;

    ArrayList<LatLng> arrayListLokacije = new ArrayList<LatLng>();
    LatLng kinodvor = new LatLng(46.056746292443144, 14.509623019204405);
    LatLng komuna = new LatLng(46.05247459777552, 14.502844717489147);
    LatLng kolosej = new LatLng(46.06580322437144, 14.549952775089782);
    LatLng bezigrad = new LatLng(46.06439031335984, 14.510149353681658);
    LatLng kinoteka = new LatLng(46.05582267463545, 14.507940239926057);

    ArrayList<String> arrayListImena = new ArrayList<String>();
    String kinodvorIme = "Kinodvor";
    String komunaIme = "Komuna";
    String kolosejIme = "Kolosej";
    String bezigradIme = "Kino Gledališče Bežigrad";
    String kinotekaIme = "Kinoteka";


    List<Address> listGeoCoder;

    private static final int LOCATION_PERMISSION_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        if (isLocationPermissionGranted()) {
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.maps);
            mapFragment.getMapAsync(this);

            configureNazajButton();

            //DELUJE
            arrayListLokacije.add(kinodvor);
            arrayListLokacije.add(komuna);
            arrayListLokacije.add(kolosej);
            arrayListLokacije.add(bezigrad);
            arrayListLokacije.add(kinoteka);

            arrayListImena.add(kinodvorIme);
            arrayListImena.add(komunaIme);
            arrayListImena.add(kolosejIme);
            arrayListImena.add(bezigradIme);
            arrayListImena.add(kinotekaIme);



/*
            try {
                listGeoCoder = new Geocoder(this).getFromLocationName("Big Ben Clock, Bridge Street, London, Združeno kraljestvo", 1);
            } catch (Exception e) {
                e.printStackTrace();
            }

            double longitude = listGeoCoder.get(0).getLongitude();
            double latitude = listGeoCoder.get(0).getLatitude();
*/

            // Log.i("GOOGLE_MAP_TAG", "Address has longitude ::: " + String.valueOf(longitude) + " && latitude :::" + String.valueOf(latitude));
        } else requestLocationPermission();
    }

    private void configureNazajButton() {
        Button nazajButton = (Button) findViewById(R.id.nazajButton);
        nazajButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // SPREMINJANJE STILA
        /*googleMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(this, R.raw.custom_map_style)
        );*/

        /*
        LatLng kinodvor = new LatLng(listGeoCoder.get(0).getLatitude(), listGeoCoder.get(0).getLongitude());
        mMap.addMarker(new MarkerOptions().position(kinodvor).title(listGeoCoder.get(0).getFeatureName()));
         */

        // IZRIŠE VSE MARKERJE IN JIM DA IMENA
        for(int i = 0; i < arrayListLokacije.size(); i++) {
            mMap.addMarker(new MarkerOptions()
                    //.icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_ikona_kino)) //TUKEJ NEKI NE DELA
                    .position(arrayListLokacije.get(i))
                    .title(arrayListImena.get(i)))
                    .showInfoWindow();
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLng(arrayListLokacije.get(arrayListLokacije.size()-1)));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(18.0f));


        /*
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }
         */

        getData(mMap);
        // animateCamera();

    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    private boolean isLocationPermissionGranted() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            return true;
        } else return false;
    }

    private void requestLocationPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_CODE);
    }

    private void getData(GoogleMap mMap) {

        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {

                LatLng center = mMap.getCameraPosition().target;
                String sLatitude = String.format("%.6f", center.latitude);
                String sLongitude = String.format("%.6f", center.longitude);
                StringBuilder mLatLng = new StringBuilder();
                mLatLng.append(sLatitude);
                mLatLng.append("°");
                mLatLng.append(" ");
                mLatLng.append(sLongitude);
                mLatLng.append("°");
                //binding.latLng.setText(mLatLng);

            }
        });
    }

    private void animateCamera() {
        Location location = getLastKnownLocation();
        if (location != null) {

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
            mMap.getUiSettings().setAllGesturesEnabled(true);
            //delay is for after map loaded animation starts
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 15));

                }
            }, 2000);
        }
    }

    private Location getLastKnownLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = locationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            @SuppressLint("MissingPermission") Location l = locationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        return bestLocation;
    }
}