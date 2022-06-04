package si.uni_lj.fe.tnuv.vaja6;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
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
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
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

import si.uni_lj.fe.tnuv.vaja6.databinding.ActivityMaps2Binding;

public class MapsActivity2 extends FragmentActivity implements OnMapReadyCallback {

    // TODO shranjevat vsa besedila in podatke v te reference, kar smo delal na vajah?
    private GoogleMap mMap;
    private ActivityMaps2Binding binding;

    ArrayList<LatLng> arrayListLokacije = new ArrayList<LatLng>();
    LatLng kinodvor = new LatLng(46.056746292443144, 14.509623019204405);
    LatLng komuna = new LatLng(46.05247459777552, 14.502844717489147);
    LatLng kolosej = new LatLng(46.06580322437144, 14.549952775089782);
    LatLng bezigrad = new LatLng(46.06439031335984, 14.510149353681658);
    LatLng kinoteka = new LatLng(46.05582267463545, 14.507940239926057);

    ArrayList<String> arrayListImena = new ArrayList<String>();
    String kinodvorIme = "Kinodvor";
    String komunaIme = "Kino Komuna";
    String kolosejIme = "Kolosej";
    String bezigradIme = "Kino Gledališče Bežigrad";
    String kinotekaIme = "Kinoteka";

    private static final int LOCATION_PERMISSION_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMaps2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (isLocationPermissionGranted()) {
            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);

            //configureNazajButton();

            //DELUJE
            arrayListLokacije.add(bezigrad);
            arrayListLokacije.add(komuna);
            arrayListLokacije.add(kinoteka);
            arrayListLokacije.add(kolosej);
            arrayListLokacije.add(kinodvor);

            arrayListImena.add(bezigradIme);
            arrayListImena.add(komunaIme);
            arrayListImena.add(kinotekaIme);
            arrayListImena.add(kolosejIme);
            arrayListImena.add(kinodvorIme);

        } else requestLocationPermission();
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

    private int indeks = Global.indeksiranje;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // SPREMINJANJE STILA
        googleMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(this, R.raw.custom_map_style)
        );

        /*for(int i = 0; i < arrayListLokacije.size(); i++) {
            mMap.addMarker(new MarkerOptions()
                    .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_ikona))
                    .position(arrayListLokacije.get(i))
                    .title(arrayListImena.get(i)))
                    .showInfoWindow();
        }*/

        mMap.addMarker(new MarkerOptions()
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_ikona))
                .position(arrayListLokacije.get(indeks))
                .title(arrayListImena.get(indeks)))
                .showInfoWindow();

        //mMap.moveCamera(CameraUpdateFactory.newLatLng(arrayListLokacije.get(arrayListLokacije.size()-1)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(arrayListLokacije.get(indeks)));

        CameraPosition cameraPosition = new CameraPosition.Builder()
                //.target(arrayListLokacije.get(arrayListLokacije.size()-1))      // Sets the center of the map to Mountain View
                .target(arrayListLokacije.get(indeks))      // Sets the center of the map to Mountain View
                .zoom(18)                   // Sets the zoom
                .bearing(0)                // Sets the orientation of the camera to east
                .tilt(0)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        //getData(mMap);
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

    private void configureNazajButton() {
        Button nazajButton = (Button) findViewById(R.id.nazajButton);
        nazajButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}