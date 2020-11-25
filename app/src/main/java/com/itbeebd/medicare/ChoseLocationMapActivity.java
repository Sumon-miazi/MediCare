package com.itbeebd.medicare;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.itbeebd.medicare.api.allInterfaces.CustomLocation;

public class ChoseLocationMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private final int LOCATION_PERMISSION_ID = 1976;
    private GoogleMap mMap;
    private LatLng userLocation;
    private Boolean isGpsLocationEnableChecked = false;
    private TextView latitudeTxt;
    private TextView longitudeTxt;
    private Button submit;
    private String title = "";
    private Dialog gpsEnableDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chose_location_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        latitudeTxt = findViewById(R.id.latitudeTxt);
        longitudeTxt = findViewById(R.id.longitudeTxt);
        submit = findViewById(R.id.locationSubmitId);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        initializeDialog();

        submit.setOnClickListener(view -> {
            if(userLocation != null){
                Intent returnIntent = new Intent();
                returnIntent.putExtra("latitude", this.userLocation.latitude);
                returnIntent.putExtra("longitude", this.userLocation.longitude);
                setResult(Activity.RESULT_OK,returnIntent);
            }
            else {
                Toast.makeText(this, "You didn't pick up the location", Toast.LENGTH_LONG).show();
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_CANCELED, returnIntent);
            }
            finish();
        });
    }

    private void initializeDialog() {
        gpsEnableDialog = new Dialog(ChoseLocationMapActivity.this);
        gpsEnableDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        gpsEnableDialog.setCancelable(true);
        gpsEnableDialog.setContentView(R.layout.gps_enable_dialog);

        Button dialogButton = gpsEnableDialog.findViewById(R.id.btn_dialog);
        dialogButton.setOnClickListener(v -> gpsEnableDialog.dismiss());
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
        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.uber_style));

        getLastLocation(this::setMarker);

        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                userLocation = marker.getPosition();
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 12));
                latitudeTxt.setText(("Latitude:" + userLocation.latitude).substring(0, 18));
                longitudeTxt.setText(("Longitude:" + userLocation.longitude).substring(0, 19));
            }
        });
    }

    private void setMarker(double latitude, double longitude) {
        mMap.clear();

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .title(title)
                .draggable(true)
                .icon(bitmapDescriptorFromVector(this, R.drawable.ic_user_marker)));

         mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 12));
    }

    @SuppressLint("MissingPermission")
    private void getLastLocation(final CustomLocation customLocation) {

        if (isLocationPermissionsGiven()) {
            if (isLocationEnabled()) {
                isGpsLocationEnableChecked = false;
                requestNewLocationData(customLocation);
                if (gpsEnableDialog.isShowing())
                    gpsEnableDialog.dismiss();

            } else {
                if (isGpsLocationEnableChecked) {
                    if (!gpsEnableDialog.isShowing())
                        gpsEnableDialog.show();
                    // Toast.makeText(this, "To use this app you have to enable gps service on", Toast.LENGTH_LONG).show();
                    return;
                }
                isGpsLocationEnableChecked = true;

                //Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            requestLocationPermissions();
        }
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData(final CustomLocation customLocation) {

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationProviderClient.requestLocationUpdates(
                mLocationRequest,
                new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        // Log.i("request2", "getLastLocation from mLocationCallBacks");
                        Location mLastLocation = locationResult.getLastLocation();
                        userLocation = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
                        latitudeTxt.setText(("Latitude:" + userLocation.latitude).substring(0, 18));
                        longitudeTxt.setText(("Longitude:" + userLocation.longitude).substring(0, 19));
                        //setMarker(mLastLocation.getLatitude(), mLastLocation.getLongitude());
                        customLocation.customLocation(mLastLocation.getLatitude(), mLastLocation.getLongitude());

                        //Log.i("controlBottomSheet", "location2 >>> " + mLastLocation.getLatitude() + " " + mLastLocation.getLongitude());

                    }
                },
                Looper.myLooper()
        );

    }

    private boolean isLocationPermissionsGiven() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestLocationPermissions() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                LOCATION_PERMISSION_ID
        );
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation(this::setMarker);
            }
        }
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    @Override
    public void onResume() {
        super.onResume();

        if(userLocation != null){
            setMarker(userLocation.latitude, userLocation.longitude);
        }
        else if (isLocationPermissionsGiven()) {
            getLastLocation(this::setMarker);
            // updateUserLocationAndBusCurrentLocationFromAPI();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
    }
}